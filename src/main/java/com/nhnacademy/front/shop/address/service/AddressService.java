package com.nhnacademy.front.shop.address.service;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.address.client.AddressClient;
import com.nhnacademy.front.shop.address.client.dto.AddressCreateRequest;
import com.nhnacademy.front.shop.address.client.dto.AddressResponse;
import com.nhnacademy.front.shop.address.client.dto.AddressUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressService {
    private final AddressClient addressClient;

    public AddressResponse getAddress(long addressId) {
        return addressClient.getAddress(addressId).data();
    }

    public PageResponse<AddressResponse> getAddresses(int page, int size) {
        return addressClient.getAddresses(page, size).data();
    }

    public AddressResponse createAddress(AddressCreateRequest request) {
        return addressClient.createAddress(request).data();
    }

    public AddressResponse updateAddress(long addressId, AddressUpdateRequest request) {
        return addressClient.updateAddress(addressId, request).data();
    }

    public void setDefaultAddress(long addressId) {
        addressClient.setDefaultAddress(addressId);
    }

    public void deleteAddress(long addressId) {
        addressClient.deleteAddress(addressId);
    }
}
