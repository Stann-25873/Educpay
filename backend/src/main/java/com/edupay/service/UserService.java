package com.edupay.service;

import com.edupay.dto.request.CreateUserRequest;
import com.edupay.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(UUID id);

    UserResponse getCurrentUser();

    List<UserResponse> getAllUsersByTenant();

    UserResponse updateUser(UUID id, CreateUserRequest request);

    void deleteUser(UUID id);

    void toggleUserStatus(UUID id, boolean active);
}
