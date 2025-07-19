package doctorhoai.learn.orderservice.service.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.dto.OrderDto;
import doctorhoai.learn.orderservice.dto.OrderItemDto;
import doctorhoai.learn.orderservice.dto.filter.Filter;
import doctorhoai.learn.orderservice.dto.foodservice.FoodSizeDto;
import doctorhoai.learn.orderservice.dto.userservice.UserDto;
import doctorhoai.learn.orderservice.dto.voucherservice.CategoryDto;
import doctorhoai.learn.orderservice.dto.voucherservice.EDiscountType;
import doctorhoai.learn.orderservice.dto.voucherservice.FoodDto;
import doctorhoai.learn.orderservice.dto.voucherservice.VoucherDto;
import doctorhoai.learn.orderservice.exception.exception.*;
import doctorhoai.learn.orderservice.feign.foodservice.FoodSizeFeign;
import doctorhoai.learn.orderservice.feign.foodservice.VoucherFeign;
import doctorhoai.learn.orderservice.feign.userservice.UserFeign;
import doctorhoai.learn.orderservice.model.*;
import doctorhoai.learn.orderservice.model.enums.EStatusOrder;
import doctorhoai.learn.orderservice.repository.*;
import doctorhoai.learn.orderservice.service.cartservice.CartServiceImpl;
import doctorhoai.learn.orderservice.utils.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ShippingFeeConfigRepository shippingFeeConfigRepository;
    private final PaymentRepository paymentRepository;
    private final UserFeign userFeign;
    private final VoucherFeign voucherFeign;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FoodSizeFeign foodSizeFeign;
    private final CartServiceImpl cartService;
    private final Mapper mapper;
    private List<Integer> rushHourFee = new ArrayList<>(Arrays.asList(5, 6, 11, 12, 17,18));

    @Override
    public OrderDto save(OrderDto orderDto, List<OrderItemDto> orderItemDtoList) {
        float totalCost = 0;
        // check user
        if( orderDto.getUserId() == null || orderDto.getUserId().getId() == null){
            throw new UserNotFoundException();
        }
        userFeign.getUserById(orderDto.getUserId().getId());
        //check payment
        if( orderDto.getPaymentId() == null || orderDto.getPaymentId().getId() == null){
            throw new PaymentNotFoundException();
        }
        Payment payment = paymentRepository.getPaymentByIdAndIsActive(orderDto.getPaymentId().getId(), true).orElseThrow(PaymentNotFoundException::new);

        // check ship
        ShippingFeeConfig shippingFeeConfig = shippingFeeConfigRepository.findShippingFeeConfigByNew().orElseThrow(ShippingFeeConfigNotFoundException::new);
        float calculateCostShip =shippingFeeConfig.getBaseFee() + (orderDto.getShipFee() * shippingFeeConfig.getFeePerKm());
        int currentHour = LocalDateTime.now().getHour();
        if( rushHourFee.contains(currentHour)){
            calculateCostShip += shippingFeeConfig.getRushHourFee();
        }

        // check cartitem
        List<Integer> idsFoodSize = orderItemDtoList.stream().map( i -> i.getFoodId().getId()).toList();
        List<FoodSizeDto> foodSizeDtos = cartService.getFoodSizeDto(idsFoodSize);

        List<Integer> idsCartItem = orderItemDtoList.stream().map(OrderItemDto::getId).toList();
        List<CartItem> cartItems = cartItemRepository.getCartItemByIds(idsCartItem);

        EDiscountType type = null;
        Double priceDown = null;
        List<Integer> category = new ArrayList<>();
        List<Integer> food = new ArrayList<>();
        VoucherDto voucherDto = new VoucherDto();
        // check voucher
        if( orderDto.getDiscountApplied() != null && orderDto.getDiscountApplied().getId() != null){
            ResponseEntity<ResponseObject> responseVoucher = voucherFeign.getVoucherById(orderDto.getDiscountApplied().getId());

            if( responseVoucher.getStatusCode().is2xxSuccessful() ){
                 voucherDto = objectMapper.convertValue(responseVoucher.getBody().getData(), VoucherDto.class);
                // check voucher cant apply
                if( voucherDto.getUsers() == null || orderDto.getUserId() == null || orderDto.getUserId().getId() == null){
                    throw new UserNotFoundException();
                }else{
                    List<Integer> userIds = voucherDto.getUsers().stream().map(UserDto::getId).toList();
                    if( !userIds.contains(orderDto.getUserId().getId())){
                        throw new VoucherCantApplyException(EMessageException.USER_NOT_HAVE_VOUCHER.getMessage());
                    }
                }
                if( voucherDto.getMaxUse() == voucherDto.getUsedCount()){
                    throw new VoucherCantApplyException(EMessageException.VOUCHER_CANT_USE.getMessage());
                }
                if( voucherDto.getCategories() != null ){
                    category = voucherDto.getCategories().stream().map(CategoryDto::getId).toList();
                }
                if( voucherDto.getFoods() != null ){
                    food = voucherDto.getFoods().stream().map(FoodDto::getId).toList();
                }
                switch (orderDto.getDiscountApplied().getDiscountType()){
                    case CASH -> {
                        type = EDiscountType.CASH;
                        priceDown = voucherDto.getMaxDiscount();
                    }
                    case PERCENT -> {
                        priceDown = voucherDto.getMaxDiscount();
                        type = EDiscountType.PERCENT;
                    }
                    case FREESHIP -> {
                        type = EDiscountType.FREESHIP;
                        calculateCostShip -= voucherDto.getMaxDiscount();
                    }
                }
            }
        }

        // calculate total
        for( FoodSizeDto foodSizeDto :foodSizeDtos){
            if( foodSizeDto.getFoodId() != null && foodSizeDto.getFoodId().getCategory() != null && foodSizeDto.getFoodId().getCategory().getId() != null){
                if( category.contains(foodSizeDto.getFoodId().getCategory().getId())){
                    switch (type){
                        case CASH -> {
                            totalCost += (float) (foodSizeDto.getPrice()*(100 -foodSizeDto.getDiscount()) - priceDown);
                        }
                        case PERCENT -> {
                            totalCost += (float) ((foodSizeDto.getPrice()*(100 -foodSizeDto.getDiscount()))*(100-priceDown));
                        }
                    }
                    continue;
                }
            }

            if( foodSizeDto.getFoodId() != null && foodSizeDto.getFoodId().getId() != null){
                if( food.contains(foodSizeDto.getFoodId().getId())){
                    switch (type){
                        case CASH -> {
                            totalCost += (float) (foodSizeDto.getPrice()*(100 -foodSizeDto.getDiscount()) - priceDown);
                        }
                        case PERCENT -> {
                            totalCost += (float) ((foodSizeDto.getPrice()*(100 -foodSizeDto.getDiscount()))*(100-priceDown));
                        }
                    }
                    continue;
                }
            }
            throw new FoodNotFoundException();
        }

        if( totalCost != orderDto.getTotalPrice() ){
            throw new ErrorException(EMessageException.TOTAL_COST_NOT_CORRECT.getMessage());
        }

        // create order
        Order orderSave = Order.builder()
                .userId(orderDto.getUserId().getId())
                .paymentId(payment)
                .transactionCode(null)
                .discountApplied(orderDto.getDiscountApplied().getId())
                .address(orderDto.getAddress())
                .totalPrice(totalCost)
                .shipFee( totalCost >= shippingFeeConfig.getMinOrderForFeeShipping() ? 0 : Math.max(0, calculateCostShip))
                .tableNumber( orderDto.getTableNumber())
                .status(EStatusOrder.PENDING)
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        for( CartItem cartItem : cartItems){
            FoodSizeDto foodSizeDto = getFoodSizeInList(foodSizeDtos, cartItem.getFoodId());
            OrderItem orderItem = OrderItem.builder()
                    .orderId(orderSave)
                    .foodId(cartItem.getFoodId())
                    .quantity(cartItem.getQuantity())
                    .priceAtTime( foodSizeDto.getPrice() * (100 - foodSizeDto.getDiscount()) )
                    .isActive(true)
                    .build();
            orderItems.add(orderItem);
        }
        orderSave.setOrderItems(orderItems);

        orderSave = orderRepository.save(orderSave);
        return convertToOrder(orderSave, voucherDto); //TODO : chua check
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
