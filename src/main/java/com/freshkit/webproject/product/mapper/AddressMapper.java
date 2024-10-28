package com.freshkit.webproject.product.mapper;

import com.freshkit.webproject.product.dto.AddressDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {
    List<AddressDTO> getAddressesByMemberId(String memberId);
    void insertAddress(AddressDTO addressDTO);
    void updateAddress(AddressDTO addressDTO);
    void deleteAddress(int addressId);
}
