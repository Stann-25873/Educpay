package com.edupay.mapper;

import com.edupay.dto.response.InvoiceResponse;
import com.edupay.entity.Invoice;
import com.edupay.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    public InvoiceResponse toResponse(Invoice invoice) {
        InvoiceResponse response = new InvoiceResponse();
        response.setId(invoice.getId());
        response.setInvoiceNumber(invoice.getInvoiceNumber());
        response.setIssueDate(invoice.getIssueDate());
        response.setDueDate(invoice.getDueDate());
        response.setStatus(invoice.getStatus());
        response.setTotalAmount(invoice.getTotalAmount());
        response.setPaidAmount(invoice.getPaidAmount());
        response.setCreatedAt(invoice.getCreatedAt());

        if (invoice.getInstitution() != null) {
            response.setInstitutionId(invoice.getInstitution().getId());
            response.setInstitutionName(invoice.getInstitution().getName());
        }

        Student student = invoice.getStudent();
        if (student != null) {
            response.setStudentId(student.getId());
            response.setStudentName(student.getFirstName() + " " + student.getLastName());
        }

        if (invoice.getFee() != null) {
            response.setFeeId(invoice.getFee().getId());
            response.setFeeTitle(invoice.getFee().getTitle());
        }

        return response;
    }
}