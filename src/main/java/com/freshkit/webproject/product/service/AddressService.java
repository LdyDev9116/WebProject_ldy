package com.freshkit.webproject.product.service;

import com.freshkit.webproject.product.dto.AddressDTO;
import com.freshkit.webproject.product.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;

    public List<AddressDTO> getAddressesByMemberId(String memberId) {
        return addressMapper.getAddressesByMemberId(memberId);
    }

    public void addAddress(AddressDTO addressDTO) {
        addressMapper.insertAddress(addressDTO);
    }

    public void updateAddress(AddressDTO addressDTO) {
        addressMapper.updateAddress(addressDTO);
    }

    public void deleteAddress(int addressId) {
        addressMapper.deleteAddress(addressId);
    }
}
