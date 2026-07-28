package com.edupay.mapper;

import com.edupay.dto.response.AccountantResponse;
import com.edupay.entity.Accountant;
import org.springframework.stereotype.Component;

@Component
public class AccountantMapper {

    public AccountantResponse toResponse(Accountant accountant) {
        AccountantResponse response = new AccountantResponse();
        response.setId(accountant.getId());
        response.setFirstName(accountant.getFirstName());
        response.setLastName(accountant.getLastName());
        response.setEmail(accountant.getEmail());
        response.setPhone(accountant.getPhone());
        response.setCreatedAt(accountant.getCreatedAt());
        if (accountant.getInstitution() != null) {
            response.setInstitutionId(accountant.getInstitution().getId());
            response.setInstitutionName(accountant.getInstitution().getName());
        }
        return response;
    }
}