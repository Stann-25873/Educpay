package com.edupay.mapper;

import com.edupay.dto.response.UserResponse;
import com.edupay.entity.Role;
import com.edupay.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        if (user == null) return null;

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setIsActive(user.getIsActive());
        response.setLastLoginAt(user.getLastLoginAt());
        response.setCreatedAt(user.getCreatedAt());

        if (user.getInstitution() != null) {
            response.setInstitutionId(user.getInstitution().getId());
            response.setInstitutionName(user.getInstitution().getName());
        }

        if (user.getRole() != null) {
            response.setRoleId(user.getRole().getId());
            response.setRoleCode(user.getRole().getCode());
            response.setRoleName(user.getRole().getName());
        }

        return response;
    }
}
