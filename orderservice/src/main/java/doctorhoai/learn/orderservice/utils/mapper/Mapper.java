package doctorhoai.learn.orderservice.utils.mapper;

import doctorhoai.learn.orderservice.dto.*;
import doctorhoai.learn.orderservice.model.*;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public PaymentDto convertToPaymentDto(Payment payment ){
        return PaymentDto.builder()
                .id(payment.getId())
                .methodName(payment.getMethodName())
                .isActive(payment.getIsActive())
                .code(payment.getCode())
                .build();
    }

    public Payment convertToPayment(PaymentDto paymentDto){
        return Payment.builder()
                .methodName(paymentDto.getMethodName())
                .code(paymentDto.getCode())
                .isActive(paymentDto.getIsActive())
                .build();
    }

    public CartDto convertToCartDto(Cart cart){
        return CartDto.builder()
                .id(cart.getId())
                .isActive(cart.getIsActive())
                .build();
    }

    public Cart convertToCart(CartDto cartDto){
        return Cart.builder()
                .isActive(cartDto.getIsActive())
                .build();
    }

    public CartItem convertToCartItem(CartItemDto cartItemDto){
        return CartItem.builder()
                .quantity(cartItemDto.getQuantity())
                .priceAtTime(cartItemDto.getPriceAtTime())
                .isActive(cartItemDto.getIsActive())
                .build();
    }

    public CartItemDto convertToCartItemDto(CartItem cartItem){
        return CartItemDto.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .priceAtTime(cartItem.getPriceAtTime())
                .isActive(cartItem.getIsActive())
                .build();
    }

    public HistoryPoint convertToHistoryPoint( HistoryPointDto historyPointDto){
        return HistoryPoint.builder()
                .isActive(historyPointDto.getIsActive())
                .usedPoint(historyPointDto.getUsedPoint())
                .build();
    }

    public HistoryPointDto convertToHistoryPointDto(HistoryPoint historyPoint){
        return HistoryPointDto.builder()
                .id(historyPoint.getId())
                .usedPoint(historyPoint.getUsedPoint())
                .isActive(historyPoint.getIsActive())
                .build();
    }

//    public Order convertToOrder(OrderDto orderDto){
//        return Order.builder()
//                .userId(orderDto.getUserId().getId())
//                .totalPrice(orderDto.getTotalPrice())
//                .transactionCode(orderDto.getTransactionCode())
//                .status(orderDto.getStatus())
//                .address(orderDto.getAddress())
//                .shipFee(orderDto.getShipFee())
//                .tableNumber(orderDto.getTableNumber())
//                .build();
//    }

    public OrderDto convertToOrderDto(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .address(order.getAddress())
                .totalPrice(order.getTotalPrice())
                .transactionCode(order.getTransactionCode())
                .status(order.getStatus())
                .shipFee(order.getShipFee())
                .tableNumber(order.getTableNumber())
                .createTime(order.getCreatedAt())
                .updateTime(order.getLateUpdateTime())
                .message(order.getMessage())
                .name(order.getName())
                .phoneNumber(order.getPhoneNumber())
                .createTime(order.getCreatedAt())
                .cogs(order.getCogs())
                .build();
    }

    public OrderItem convertToOrderItem(OrderItemDto orderItemDto){
        return OrderItem.builder()
                .quantity(orderItemDto.getQuantity())
                .priceAtTime(orderItemDto.getPriceAtTime())
                .isActive(orderItemDto.getIsActive())
                .build();
    }

    public OrderItemDto convertToOrderItemDto(OrderItem orderItem){
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .priceAtTime(orderItem.getPriceAtTime())
                .isActive(orderItem.getIsActive())
                .build();
    }

    public PointDto convertToPointDto(Point point){
        return PointDto.builder()
                .id(point.getId())
                .isActive(point.getIsActive())
                .point(point.getPoint())
                .build();
    }

    public Point convertToPoint(PointDto pointDto){
        return Point.builder()
                .isActive(pointDto.getIsActive())
                .point(pointDto.getPoint())
                .build();
    }

    public ShippingFeeConfig convertToShippingFeeConfig(ShippingFeeConfigDto shippingFeeConfigDto){
        return ShippingFeeConfig.builder()
                .baseFee(shippingFeeConfigDto.getBaseFee())
                .feePerKm(shippingFeeConfigDto.getFeePerKm())
                .rushHourFee(shippingFeeConfigDto.getRushHourFee())
                .minOrderForFeeShipping(shippingFeeConfigDto.getMinOrderForFeeShipping())
                .build();
    }

    public ShippingFeeConfigDto convertToShippingFeeConfigDto(ShippingFeeConfig shippingFeeConfig){
        return ShippingFeeConfigDto.builder()
                .id(shippingFeeConfig.getId())
                .baseFee(shippingFeeConfig.getBaseFee())
                .feePerKm(shippingFeeConfig.getFeePerKm())
                .rushHourFee(shippingFeeConfig.getRushHourFee())
                .minOrderForFeeShipping(shippingFeeConfig.getMinOrderForFeeShipping())
                .build();
    }
}
