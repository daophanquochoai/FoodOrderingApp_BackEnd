package doctorhoai.learn.orderservice.service.shipfeeconfig;

import doctorhoai.learn.orderservice.dto.ShippingFeeConfigDto;
import doctorhoai.learn.orderservice.exception.exception.ShippingFeeConfigNotFoundException;
import doctorhoai.learn.orderservice.model.ShippingFeeConfig;
import doctorhoai.learn.orderservice.repository.ShippingFeeConfigRepository;
import doctorhoai.learn.orderservice.utils.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingFeeConfigServiceImpl implements ShippingFeeConfigService{

    private final ShippingFeeConfigRepository shippingFeeConfigRepository;
    private final Mapper mapper;

    @Override
    public ShippingFeeConfigDto createShippingFeeConfig(ShippingFeeConfigDto shippingFeeConfigDto) {
        ShippingFeeConfig shippingFeeConfig = mapper.convertToShippingFeeConfig(shippingFeeConfigDto);
        List<ShippingFeeConfig> shipUpdated = shippingFeeConfigRepository.findShippingFeeConfigActive();

        shipUpdated = shipUpdated.stream().peek(s -> s.setIsActive(false)).toList();
        shippingFeeConfigRepository.saveAll(shipUpdated);

        shippingFeeConfig = shippingFeeConfigRepository.save(shippingFeeConfig);
        return mapper.convertToShippingFeeConfigDto(shippingFeeConfig);
    }

    @Override
    public ShippingFeeConfigDto getShippingFeeConfig() {
        ShippingFeeConfig shippingFeeConfig = shippingFeeConfigRepository.findShippingFeeConfigByNew().orElseThrow(ShippingFeeConfigNotFoundException::new);
        return mapper.convertToShippingFeeConfigDto(shippingFeeConfig);
    }
}
