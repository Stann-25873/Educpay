package com.edupay.serviceImpl;

import com.edupay.dto.response.InvoiceResponse;
import com.edupay.entity.Invoice;
import com.edupay.exception.ResourceNotFound;
import com.edupay.exception.UnauthorizedTenantAccess;
import com.edupay.mapper.InvoiceMapper;
import com.edupay.repository.InvoiceRepository;
import com.edupay.security.SecurityUtils;
import com.edupay.service.InvoiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponse getInvoice(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Invoice not found: " + id));
        if (!invoice.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> getAllInvoices() {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return invoiceRepository.findAll().stream()
                .filter(i -> i.getTenantId().equals(tenantId))
                .map(invoiceMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> getInvoicesByStudent(UUID studentId) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return invoiceRepository.findAll().stream()
                .filter(i -> i.getTenantId().equals(tenantId) && i.getStudent().getId().equals(studentId))
                .map(invoiceMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> getInvoicesByStatus(String status) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return invoiceRepository.findAll().stream()
                .filter(i -> i.getTenantId().equals(tenantId) && status.equals(i.getStatus()))
                .map(invoiceMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> getInvoicesByDateRange(LocalDate startDate, LocalDate endDate) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return invoiceRepository.findAll().stream()
                .filter(i -> i.getTenantId().equals(tenantId)
                        && i.getIssueDate() != null
                        && !i.getIssueDate().isBefore(startDate)
                        && !i.getIssueDate().isAfter(endDate))
                .map(invoiceMapper::toResponse)
                .toList();
    }
}