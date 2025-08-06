package doctorhoai.learn.userservice.service.address;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.userservice.dto.AddressDto;
import doctorhoai.learn.userservice.dto.Filter.Filter;

public interface AddressService {
    AddressDto createNewAddress(AddressDto addressDto);
    AddressDto updateAddress(AddressDto addressDto, Integer id);
    PageObject getAddress(Filter filter);
    void setDefaultAddress(Integer addId, Integer userId);
}
