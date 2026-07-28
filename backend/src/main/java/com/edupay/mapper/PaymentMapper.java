package com.edupay.mapper;

import com.edupay.dto.response.PaymentResponse;
import com.edupay.entity.Invoice;
import com.edupay.entity.Payment;
import com.edupay.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentResponse toResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setAmount(payment.getAmount());
        response.setCurrency(payment.getCurrency());
        response.setMethod(payment.getMethod());
        response.setReference(payment.getReference());
        response.setPaidAt(payment.getPaidAt());
        response.setCreatedAt(payment.getCreatedAt());

        if (payment.getInstitution() != null) {
            response.setInstitutionId(payment.getInstitution().getId());
            response.setInstitutionName(payment.getInstitution().getName());
        }

        Student student = payment.getStudent();
        if (student != null) {
            response.setStudentId(student.getId());
            response.setStudentName(student.getFirstName() + " " + student.getLastName());
        }

        Invoice invoice = payment.getInvoice();
        if (invoice != null) {
            response.setInvoiceId(invoice.getId());
            response.setInvoiceNumber(invoice.getInvoiceNumber());
        }

        return response;
    }
}