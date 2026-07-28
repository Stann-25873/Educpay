package com.edupay.mapper;

import com.edupay.dto.response.ReceiptResponse;
import com.edupay.entity.Receipt;
import org.springframework.stereotype.Component;

@Component
public class ReceiptMapper {

    public ReceiptResponse toResponse(Receipt receipt) {
        ReceiptResponse response = new ReceiptResponse();
        response.setId(receipt.getId());
        response.setReceiptNumber(receipt.getReceiptNumber());
        response.setAmount(receipt.getAmount());
        response.setCurrency(receipt.getCurrency());
        response.setIssuedAt(receipt.getIssuedAt());
        response.setCreatedAt(receipt.getCreatedAt());

        if (receipt.getInstitution() != null) {
            response.setInstitutionId(receipt.getInstitution().getId());
            response.setInstitutionName(receipt.getInstitution().getName());
        }

        if (receipt.getPayment() != null) {
            response.setPaymentId(receipt.getPayment().getId());
        }

        return response;
    }
}