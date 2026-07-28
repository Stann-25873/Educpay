package com.edupay.serviceImpl;

import com.edupay.dto.request.CreatePaymentRequest;
import com.edupay.dto.response.PaymentResponse;
import com.edupay.entity.Institution;
import com.edupay.entity.Invoice;
import com.edupay.entity.Payment;
import com.edupay.entity.Student;
import com.edupay.exception.ResourceNotFound;
import com.edupay.exception.UnauthorizedTenantAccess;
import com.edupay.mapper.PaymentMapper;
import com.edupay.repository.InstitutionRepository;
import com.edupay.repository.InvoiceRepository;
import com.edupay.repository.PaymentRepository;
import com.edupay.repository.StudentRepository;
import com.edupay.security.SecurityUtils;
import com.edupay.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final InvoiceRepository invoiceRepository;
    private final InstitutionRepository institutionRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                               StudentRepository studentRepository,
                               InvoiceRepository invoiceRepository,
                               InstitutionRepository institutionRepository,
                               PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
        this.invoiceRepository = invoiceRepository;
        this.institutionRepository = institutionRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    @Transactional
    public PaymentResponse createPayment(CreatePaymentRequest request) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Institution institution = institutionRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFound("Institution not found"));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFound("Student not found: " + request.getStudentId()));
        if (!student.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }

        Payment payment = new Payment();
        payment.setInstitution(institution);
        payment.setStudent(student);
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setMethod(request.getMethod());
        payment.setReference(request.getReference());
        payment.setPaidAt(OffsetDateTime.now());

        if (request.getInvoiceId() != null) {
            Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                    .orElseThrow(() -> new ResourceNotFound("Invoice not found: " + request.getInvoiceId()));
            if (!invoice.getTenantId().equals(tenantId)) {
                throw new UnauthorizedTenantAccess("Cross-tenant access denied");
            }
            payment.setInvoice(invoice);
        }

        payment = paymentRepository.save(payment);
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPayment(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Payment not found: " + id));
        if (!payment.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getAllPayments() {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return paymentRepository.findAll().stream()
                .filter(p -> p.getTenantId().equals(tenantId))
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByStudent(UUID studentId) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return paymentRepository.findAll().stream()
                .filter(p -> p.getTenantId().equals(tenantId) && p.getStudent().getId().equals(studentId))
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return paymentRepository.findAll().stream()
                .filter(p -> p.getTenantId().equals(tenantId)
                        && p.getPaidAt() != null
                        && !p.getPaidAt().toLocalDate().isBefore(startDate)
                        && !p.getPaidAt().toLocalDate().isAfter(endDate))
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByMethod(String method) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return paymentRepository.findAll().stream()
                .filter(p -> p.getTenantId().equals(tenantId) && method.equals(p.getMethod()))
                .map(paymentMapper::toResponse)
                .toList();
    }
}