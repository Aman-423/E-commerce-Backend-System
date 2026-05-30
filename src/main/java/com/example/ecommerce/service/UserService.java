package com.example.ecommerce.service;

import com.example.ecommerce.dto.user.AddressRequest;
import com.example.ecommerce.dto.user.AddressResponse;
import com.example.ecommerce.dto.user.UpdateProfileRequest;
import com.example.ecommerce.dto.user.UserProfileResponse;
import java.util.List;

public interface UserService {
    UserProfileResponse getProfile(Long userId);
    UserProfileResponse updateProfile(Long userId, UpdateProfileRequest request);
    List<AddressResponse> getAddresses(Long userId);
    AddressResponse addAddress(Long userId, AddressRequest request);
    AddressResponse updateAddress(Long userId, Long addressId, AddressRequest request);
    void deleteAddress(Long userId, Long addressId);
}
