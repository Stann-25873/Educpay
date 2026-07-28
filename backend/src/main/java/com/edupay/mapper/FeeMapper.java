package com.edupay.mapper;

import com.edupay.dto.response.FeeResponse;
import com.edupay.entity.Fee;
import org.springframework.stereotype.Component;

@Component
public class FeeMapper {

    public FeeResponse toResponse(Fee fee) {
        FeeResponse response = new FeeResponse();
        response.setId(fee.getId());
        response.setCode(fee.getCode());
        response.setTitle(fee.getTitle());
        response.setDescription(fee.getDescription());
        response.setAmount(fee.getAmount());
        response.setCurrency(fee.getCurrency());
        response.setBillingPeriod(fee.getBillingPeriod());
        response.setLevel(fee.getLevel());
        response.setCreatedAt(fee.getCreatedAt());
        if (fee.getInstitution() != null) {
            response.setInstitutionId(fee.getInstitution().getId());
            response.setInstitutionName(fee.getInstitution().getName());
        }
        return response;
    }
}