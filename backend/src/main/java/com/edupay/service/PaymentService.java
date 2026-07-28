package com.edupay.service;

import com.edupay.dto.request.CreatePaymentRequest;
import com.edupay.dto.response.PaymentResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PaymentService {
    PaymentResponse createPayment(CreatePaymentRequest request);
    PaymentResponse getPayment(UUID id);
    List<PaymentResponse> getAllPayments();
    List<PaymentResponse> getPaymentsByStudent(UUID studentId);
    List<PaymentResponse> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate);
    List<PaymentResponse> getPaymentsByMethod(String method);
}
