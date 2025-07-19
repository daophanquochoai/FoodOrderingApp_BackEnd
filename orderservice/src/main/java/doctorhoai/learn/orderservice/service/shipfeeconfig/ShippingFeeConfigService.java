package doctorhoai.learn.orderservice.service.shipfeeconfig;

import doctorhoai.learn.orderservice.dto.ShippingFeeConfigDto;

public interface ShippingFeeConfigService {

    ShippingFeeConfigDto createShippingFeeConfig(ShippingFeeConfigDto shippingFeeConfigDto);
    ShippingFeeConfigDto getShippingFeeConfig();
}
