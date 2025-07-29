package doctorhoai.learn.orderservice.service.orderservice;

import doctorhoai.learn.basedomain.kafka.order.EventOrder;
import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.orderservice.dto.OrderDto;
import doctorhoai.learn.orderservice.dto.OrderItemDto;
import doctorhoai.learn.orderservice.dto.filter.Filter;
import doctorhoai.learn.orderservice.dto.foodservice.FoodSizeDto;
import doctorhoai.learn.orderservice.dto.userservice.UserDto;
import doctorhoai.learn.orderservice.dto.voucherservice.VoucherDto;
import doctorhoai.learn.orderservice.exception.exception.*;
import doctorhoai.learn.orderservice.feign.foodservice.VoucherFeign;
import doctorhoai.learn.orderservice.feign.userservice.UserFeign;
import doctorhoai.learn.orderservice.kafka.sender.KafkaMessageSender;
import doctorhoai.learn.orderservice.model.*;
import doctorhoai.learn.orderservice.model.enums.EStatusOrder;
import doctorhoai.learn.orderservice.repository.*;
import doctorhoai.learn.orderservice.service.cartservice.CartServiceImpl;
import doctorhoai.learn.orderservice.utils.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final PaymentRepository paymentRepository;
    private final UserFeign userFeign;
    private final VoucherFeign voucherFeign;
    private final CartServiceImpl cartService;
    private final Mapper mapper;
    private final KafkaMessageSender sender;
    private final PointRepository pointRepository;
    private final HistoryPointRepository historyPointRepository;
    @Value("${spring.kafka.topic.food}")
    private String foodTopic;
    @Value("${spring.kafka.topic.inventory}")
    private String inventoryTopic;

    @Override
    @Transactional
    public OrderDto save(OrderDto orderDto) {

        Order order = new Order();

        // check user
        if( orderDto.getUserId() == null || orderDto.getUserId().getId() == null){
            throw new UserNotFoundException();
        }
        userFeign.getUserById(orderDto.getUserId().getId());
        order.setUserId(orderDto.getUserId().getId());

        //check payment
        if( orderDto.getPaymentId() == null || orderDto.getPaymentId().getCode() == null){
            throw new PaymentNotFoundException();
        }
        Payment payment = paymentRepository.getPaymentByCodeAndIsActive(orderDto.getPaymentId().getCode(), true).orElseThrow(PaymentNotFoundException::new);
        order.setPaymentId(payment);

        //set total price
        order.setTotalPrice(orderDto.getTotalPrice());

        // set transaction code
        order.setTransactionCode(orderDto.getTransactionCode());

        // check voucher
        if( orderDto.getDiscountApplied() != null && orderDto.getDiscountApplied().getId() != null ){
            voucherFeign.getVoucherById(orderDto.getDiscountApplied().getId());
            order.setDiscountApplied(orderDto.getDiscountApplied().getId());
        }

        // check address
        order.setAddress(orderDto.getAddress());

        // ship fee
        order.setShipFee(orderDto.getShipFee());

        // status
        order.setStatus(order.getStatus());

        // table number
        order.setTableNumber(orderDto.getTableNumber());

        // set name
        order.setName(orderDto.getName());

        // phone number
        order.setPhoneNumber(orderDto.getPhoneNumber());

        // order item
        if( orderDto.getOrderItems() == null){
            throw new OrderNotFoundException();
        }

        // check cartitem
        List<Integer> idsFoodSize = orderDto.getOrderItems().stream().map( i -> i.getFoodId().getId()).toList();
        List<FoodSizeDto> foodSizeDtos = cartService.getFoodSizeDto(idsFoodSize);

        List<OrderItem> orderItems = new ArrayList<>();
        for( OrderItemDto orderItemDto : orderDto.getOrderItems()){
            FoodSizeDto foodSizeDto = getFoodSizeInList(foodSizeDtos, orderItemDto.getFoodId().getId());
            OrderItem orderItem = OrderItem.builder()
                    .orderId(order)
                    .foodId(orderItemDto.getFoodId().getId())
                    .quantity(orderItemDto.getQuantity())
                    .priceAtTime( foodSizeDto.getPrice() * (100 - foodSizeDto.getDiscount()) )
                    .isActive(true)
                    .build();
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setStatus(EStatusOrder.CREATING);
        order = orderRepository.save(order);

//        Optional<Point> point = pointRepository.findByUserIdAndIsActive(orderDto.getUserId().getId(), true);
//        if( point.isEmpty()){
//            throw new PointNotFound();
//        }
//
//        List<HistoryPoint> historyPoints = new ArrayList<>();
//        historyPoints.add(HistoryPoint.builder()
//                        .userId(orderDto.getUserId().getId())
//                        .isActive(true)
//                        .usedPoint( -1 * orderDto.getPoint())
//                        .orderId(order)
//                .build());
//        historyPoints.add(HistoryPoint.builder()
//                .userId(orderDto.getUserId().getId())
//                .isActive(true)
//                .usedPoint((int) (orderDto.getTableNumber() * 0.01))
//                .orderId(order)
//                .build());
//        historyPointRepository.saveAll(historyPoints);
//
//        point.get().setPoint((int) (point.get().getPoint() + (-1 * orderDto.getPoint() + orderDto.getTableNumber() * 0.01 )));
//        pointRepository.save(point.get());


        // update cart
        List<CartItem> cartItems = cartItemRepository.getCartItemByCartToUpdate(orderDto.getUserId().getId());
        cartItems.forEach(cartItem -> cartItem.setIsActive(false));
        cartItemRepository.saveAll(cartItems);


        log.info("Send to food service...");
        OrderDto kafkaOrder = mapper.convertToOrderDto(order);
        kafkaOrder.setOrderItems(orderDto.getOrderItems());
        if( order.getDiscountApplied() != null){
            sender.sendTo(
                    EventOrder.<OrderDto>builder()
                            .order(kafkaOrder)
                            .cart(cartItems.stream().map(i -> i.getId()).toList())
                            .voucher(order.getDiscountApplied())
                            .status(doctorhoai.learn.basedomain.kafka.order.EStatusOrder.CREATE)
                            .build(),
                    foodTopic
            );
        }else{
            sender.sendTo(
                    EventOrder.<OrderDto>builder()
                            .order(kafkaOrder)
                            .cart(cartItems.stream().map(i -> i.getId()).toList())
                            .voucher(order.getDiscountApplied())
                            .status(doctorhoai.learn.basedomain.kafka.order.EStatusOrder.CREATE)
                            .build(),
                    inventoryTopic
            );
        }

        return kafkaOrder;





//        EDiscountType type = null;
//        Double priceDown = null;
//        List<Integer> category = new ArrayList<>();
//        List<Integer> food = new ArrayList<>();
//        VoucherDto voucherDto = new VoucherDto();
//        // check voucher
//        if( orderDto.getDiscountApplied() != null && orderDto.getDiscountApplied().getId() != null){
//            ResponseEntity<ResponseObject> responseVoucher = voucherFeign.getVoucherById(orderDto.getDiscountApplied().getId());
//
//            if( responseVoucher.getStatusCode().is2xxSuccessful() ){
//                 voucherDto = objectMapper.convertValue(responseVoucher.getBody().getData(), VoucherDto.class);
//                // check voucher cant apply
//                if( voucherDto.getUsers() == null || orderDto.getUserId() == null || orderDto.getUserId().getId() == null){
//                    throw new UserNotFoundException();
//                }else{
//                    List<Integer> userIds = voucherDto.getUsers().stream().map(UserDto::getId).toList();
//                    if( !userIds.contains(orderDto.getUserId().getId())){
//                        throw new VoucherCantApplyException(EMessageException.USER_NOT_HAVE_VOUCHER.getMessage());
//                    }
//                }
//                if( voucherDto.getMaxUse() == voucherDto.getUsedCount()){
//                    throw new VoucherCantApplyException(EMessageException.VOUCHER_CANT_USE.getMessage());
//                }
//                if( voucherDto.getCategories() != null ){
//                    category = voucherDto.getCategories().stream().map(CategoryDto::getId).toList();
//                }
//                if( voucherDto.getFoods() != null ){
//                    food = voucherDto.getFoods().stream().map(FoodDto::getId).toList();
//                }
//                switch (orderDto.getDiscountApplied().getDiscountType()){
//                    case CASH -> {
//                        type = EDiscountType.CASH;
//                        priceDown = voucherDto.getMaxDiscount();
//                    }
//                    case PERCENT -> {
//                        priceDown = voucherDto.getMaxDiscount();
//                        type = EDiscountType.PERCENT;
//                    }
//                    case FREESHIP -> {
//                        type = EDiscountType.FREESHIP;
//                        calculateCostShip -= voucherDto.getMaxDiscount();
//                    }
//                }
//            }
//        }
//
//        // calculate total
//        for( FoodSizeDto foodSizeDto :foodSizeDtos){
//            if( foodSizeDto.getFoodId() != null && foodSizeDto.getFoodId().getCategory() != null && foodSizeDto.getFoodId().getCategory().getId() != null){
//                if( category.contains(foodSizeDto.getFoodId().getCategory().getId())){
//                    switch (type){
//                        case CASH -> {
//                            totalCost += (float) (foodSizeDto.getPrice()*(100 -foodSizeDto.getDiscount()) - priceDown);
//                        }
//                        case PERCENT -> {
//                            totalCost += (float) ((foodSizeDto.getPrice()*(100 -foodSizeDto.getDiscount()))*(100-priceDown));
//                        }
//                    }
//                    continue;
//                }
//            }
//
//            if( foodSizeDto.getFoodId() != null && foodSizeDto.getFoodId().getId() != null){
//                if( food.contains(foodSizeDto.getFoodId().getId())){
//                    switch (type){
//                        case CASH -> {
//                            totalCost += (float) (foodSizeDto.getPrice()*(100 -foodSizeDto.getDiscount()) - priceDown);
//                        }
//                        case PERCENT -> {
//                            totalCost += (float) ((foodSizeDto.getPrice()*(100 -foodSizeDto.getDiscount()))*(100-priceDown));
//                        }
//                    }
//                    continue;
//                }
//            }
//            throw new FoodNotFoundException();
//        }
//
//        if( totalCost != orderDto.getTotalPrice() ){
//            throw new ErrorException(EMessageException.TOTAL_COST_NOT_CORRECT.getMessage());
//        }
//
//        // create order
//        Order orderSave = Order.builder()
//                .userId(orderDto.getUserId().getId())
//                .paymentId(payment)
//                .transactionCode(null)
//                .discountApplied(orderDto.getDiscountApplied().getId())
//                .address(orderDto.getAddress())
//                .totalPrice(totalCost)
//                .shipFee( totalCost >= shippingFeeConfig.getMinOrderForFeeShipping() ? 0 : Math.max(0, calculateCostShip))
//                .tableNumber( orderDto.getTableNumber())
//                .status(EStatusOrder.PENDING)
//                .build();

    }

    private OrderDto convertToOrder( Order order , VoucherDto voucherDto){
        return OrderDto.builder()
                .userId(UserDto.builder()
                        .id(order.getUserId())
                        .build())
                .totalPrice(order.getTotalPrice())
                .paymentId(mapper.convertToPaymentDto(order.getPaymentId()))
                .transactionCode(order.getTransactionCode())
                .discountApplied(voucherDto)
                .address(order.getAddress())
                .shipFee(order.getShipFee())
                .tableNumber(order.getTableNumber())
                .status(order.getStatus())
                .build();
    }


    private FoodSizeDto getFoodSizeInList( List<FoodSizeDto> foodSizeDtoList, Integer id){
        for( FoodSizeDto foodSizeDto : foodSizeDtoList){
            if( foodSizeDto.getId().equals(id)){
                return foodSizeDto;
            }
        }
        throw new FoodNotFoundException();
    }

    @Override
    public OrderDto update(OrderDto orderDto, Integer id) {
        EStatusOrder statusOrder = null;
        Order order = orderRepository.findOrderByIdAndStatus(id, orderDto.getStatus()).orElseThrow(OrderNotFoundException::new);
        switch (orderDto.getStatus()){
            case PENDING -> {
                // TODO : chua thanh toan visa / crash
                order.setTransactionCode(order.getTransactionCode());
                statusOrder = EStatusOrder.PROCESSING;
            }
            case PROCESSING -> {
                statusOrder = EStatusOrder.COMPLETE;
            }
            case COMPLETE ->  {
                statusOrder = EStatusOrder.SHIPPING;
            }
            case SHIPPING -> {
                statusOrder = EStatusOrder.RECEIVE;
            }
        }
        order.setStatus(statusOrder);

        order = orderRepository.save(order);
        return convertToOrder(order, null);
    }

    @Override
    public PageObject getVoucherAll(Filter filter) {
        return null;
    }
}
