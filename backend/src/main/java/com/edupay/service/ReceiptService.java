package com.edupay.service;

import com.edupay.dto.response.ReceiptResponse;
import java.util.List;
import java.util.UUID;

public interface ReceiptService {
    ReceiptResponse getReceipt(UUID id);
    List<ReceiptResponse> getAllReceipts();
    ReceiptResponse getReceiptByPayment(UUID paymentId);
}
