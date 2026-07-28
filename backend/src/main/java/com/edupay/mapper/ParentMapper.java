package com.edupay.mapper;

import com.edupay.dto.response.ParentResponse;
import com.edupay.entity.Parent;
import org.springframework.stereotype.Component;

@Component
public class ParentMapper {

    public ParentResponse toResponse(Parent parent) {
        ParentResponse response = new ParentResponse();
        response.setId(parent.getId());
        response.setFirstName(parent.getFirstName());
        response.setLastName(parent.getLastName());
        response.setEmail(parent.getEmail());
        response.setPhone(parent.getPhone());
        response.setCreatedAt(parent.getCreatedAt());
        if (parent.getInstitution() != null) {
            response.setInstitutionId(parent.getInstitution().getId());
            response.setInstitutionName(parent.getInstitution().getName());
        }
        return response;
    }
}

