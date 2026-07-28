package com.edupay.service;

import com.edupay.dto.response.InvoiceResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface InvoiceService {
    InvoiceResponse getInvoice(UUID id);
    List<InvoiceResponse> getAllInvoices();
    List<InvoiceResponse> getInvoicesByStudent(UUID studentId);
    List<InvoiceResponse> getInvoicesByStatus(String status);
    List<InvoiceResponse> getInvoicesByDateRange(LocalDate startDate, LocalDate endDate);
}
