package doctorhoai.learn.userservice.service.address;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.userservice.dto.AddressDto;
import doctorhoai.learn.userservice.dto.Filter.Filter;
import doctorhoai.learn.userservice.dto.UserDto;
import doctorhoai.learn.userservice.exception.exception.UserNotFound;
import doctorhoai.learn.userservice.model.Address;
import doctorhoai.learn.userservice.model.User;
import doctorhoai.learn.userservice.repository.AddressRepository;
import doctorhoai.learn.userservice.repository.UserRepository;
import doctorhoai.learn.userservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;


    @Override
    public AddressDto createNewAddress(AddressDto addressDto) {

        if( addressDto.getUserId() == null || addressDto.getUserId().getId() == null){
            throw new UserNotFound("id", null);
        }
        User user = userRepository.findByIdAndIsActive(addressDto.getUserId().getId(), true).orElseThrow(() -> new UserNotFound("id", addressDto.getUserId().getId().toString()));
        Address address = mapper.convertToAddress(addressDto);

        address.setUserId(user);
        Address addressSaved = addressRepository.save(address);
        AddressDto addressReturn = mapper.convertToAddressDto(addressSaved);
        addressReturn.setUserId(mapper.convertToUserDto(user));
        return addressReturn;
    }

    @Override
    public AddressDto updateAddress(AddressDto addressDto, Integer id) {
        addressRepository.getAddressByIdAndIsActive(id, true).orElseThrow(() -> new UserNotFound("id", addressDto.getUserId().getId().toString()));

        addressDto.setProvince(addressDto.getProvince());
        addressDto.setCommune(addressDto.getCommune());
        addressDto.setAddress(addressDto.getAddress());
        addressDto.setIsDefault(addressDto.getIsDefault());
        addressDto.setAddress(addressDto.getAddress());
        Address address = mapper.convertToAddress(addressDto);
        AddressDto addressReturn = mapper.convertToAddressDto(addressRepository.save(address));
        addressReturn.setUserId(mapper.convertToUserDto(address.getUserId()));
        return addressReturn;
    }

    @Override
    public PageObject getAddress(Filter filter) {
        Pageable pageable;
        if( filter.getSort().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()).descending());
        } else {
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()));
        }

        Page<Address> addresses = addressRepository.getAddressByFilter(
                filter.getSearch(),
                filter.getIsActive(),
                filter.getUserId(),
                pageable
        );
        return PageObject.builder()
                .page(filter.getPageNo())
                .totalPage(addresses.getTotalPages())
                .data(
                        addresses.getContent() == null ? null :
                                addresses.getContent().stream().map( mapper::convertToAddressDto).toList()
                )
                .build();
    }
}

