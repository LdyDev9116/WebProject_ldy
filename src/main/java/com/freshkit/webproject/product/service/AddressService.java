package com.freshkit.webproject.product.service;

import com.freshkit.webproject.product.dto.AddressDTO;
import com.freshkit.webproject.product.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AddressService는 주소와 관련된 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * - 사용자 주소 조회, 추가, 수정, 삭제와 같은 작업을 제공합니다.
 */
@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 특정 회원의 주소 목록을 조회합니다.
     * @param memberId 조회할 회원의 ID
     * @return List<AddressDTO> 형태의 주소 목록
     */
    public List<AddressDTO> getAddressesByMemberId(String memberId) {
        return addressMapper.getAddressesByMemberId(memberId);
    }

    /**
     * 새로운 주소를 추가합니다.
     * @param addressDTO 추가할 주소 정보를 담은 객체
     */
    public void addAddress(AddressDTO addressDTO) {
        addressMapper.insertAddress(addressDTO);
    }

    /**
     * 기존 주소를 수정합니다.
     * @param addressDTO 수정할 주소 정보를 담은 객체
     */
    public void updateAddress(AddressDTO addressDTO) {
        addressMapper.updateAddress(addressDTO);
    }

    /**
     * 특정 주소를 삭제합니다.
     * @param addressId 삭제할 주소의 ID
     */
    public void deleteAddress(int addressId) {
        addressMapper.deleteAddress(addressId);
    }
}
