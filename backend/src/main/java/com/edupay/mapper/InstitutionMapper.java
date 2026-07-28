package com.edupay.mapper;

import com.edupay.dto.response.InstitutionResponse;
import com.edupay.entity.Institution;
import org.springframework.stereotype.Component;

@Component
public class InstitutionMapper {

    public InstitutionResponse toResponse(Institution institution) {
        InstitutionResponse response = new InstitutionResponse();
        response.setId(institution.getId());
        response.setName(institution.getName());
        response.setType(institution.getType());
        response.setCreatedAt(institution.getCreatedAt());
        return response;
    }
}

