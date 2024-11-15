package com.freshkit.webproject.product.mapper;

import com.freshkit.webproject.product.dto.AddressDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AddressMapper는 사용자 주소와 관련된 데이터베이스 작업을 수행하는 MyBatis 매퍼 인터페이스입니다.
 * - 사용자별 주소 조회, 추가, 수정, 삭제 작업을 제공합니다.
 */
@Mapper
public interface AddressMapper {

    /**
     * 특정 회원의 모든 주소를 데이터베이스에서 조회합니다.
     *
     * @param memberId 조회할 회원의 ID
     * @return List<AddressDTO> 형태의 주소 목록
     */
    List<AddressDTO> getAddressesByMemberId(String memberId);

    /**
     * 새 주소를 데이터베이스에 추가합니다.
     *
     * @param addressDTO 추가할 주소 데이터를 담은 객체
     */
    void insertAddress(AddressDTO addressDTO);

    /**
     * 기존 주소를 데이터베이스에서 수정합니다.
     *
     * @param addressDTO 수정할 주소 데이터를 담은 객체
     */
    void updateAddress(AddressDTO addressDTO);

    /**
     * 특정 주소를 데이터베이스에서 삭제합니다.
     *
     * @param addressId 삭제할 주소의 ID
     */
    void deleteAddress(int addressId);
}
