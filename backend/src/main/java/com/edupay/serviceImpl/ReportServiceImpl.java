package com.edupay.serviceImpl;

import com.edupay.dto.response.ReportResponse;
import com.edupay.repository.FeeRepository;
import com.edupay.repository.InvoiceRepository;
import com.edupay.repository.PaymentRepository;
import com.edupay.repository.StudentRepository;
import com.edupay.security.SecurityUtils;
import com.edupay.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ReportServiceImpl implements ReportService {

    private final StudentRepository studentRepository;
    private final FeeRepository feeRepository;
    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    public ReportServiceImpl(StudentRepository studentRepository,
                              FeeRepository feeRepository,
                              PaymentRepository paymentRepository,
                              InvoiceRepository invoiceRepository) {
        this.studentRepository = studentRepository;
        this.feeRepository = feeRepository;
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ReportResponse getRevenueSummary(LocalDate startDate, LocalDate endDate) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());

        long totalStudents = studentRepository.findAll().stream()
                .filter(s -> s.getTenantId().equals(tenantId))
                .count();

        long totalFees = feeRepository.findAll().stream()
                .filter(f -> f.getTenantId().equals(tenantId))
                .count();

        BigDecimal totalCollected = paymentRepository.findAll().stream()
                .filter(p -> p.getTenantId().equals(tenantId)
                        && p.getPaidAt() != null
                        && !p.getPaidAt().toLocalDate().isBefore(startDate)
                        && !p.getPaidAt().toLocalDate().isAfter(endDate))
                .map(p -> p.getAmount() != null ? p.getAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOutstanding = invoiceRepository.findAll().stream()
                .filter(i -> i.getTenantId().equals(tenantId)
                        && !"PAID".equals(i.getStatus()))
                .map(i -> {
                    BigDecimal total = i.getTotalAmount() != null ? i.getTotalAmount() : BigDecimal.ZERO;
                    BigDecimal paid = i.getPaidAmount() != null ? i.getPaidAmount() : BigDecimal.ZERO;
                    return total.subtract(paid);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal collectionRate = BigDecimal.ZERO;
        BigDecimal totalExpected = totalCollected.add(totalOutstanding);
        if (totalExpected.compareTo(BigDecimal.ZERO) > 0) {
            collectionRate = totalCollected.multiply(BigDecimal.valueOf(100))
                    .divide(totalExpected, 2, RoundingMode.HALF_UP);
        }

        Map<String, BigDecimal> breakdownByLevel = new HashMap<>();
        feeRepository.findAll().stream()
                .filter(f -> f.getTenantId().equals(tenantId) && f.getLevel() != null)
                .forEach(f -> breakdownByLevel.merge(f.getLevel(), f.getAmount(), BigDecimal::add));

        ReportResponse response = new ReportResponse();
        response.setReportType("REVENUE_SUMMARY");
        response.setStartDate(startDate);
        response.setEndDate(endDate);
        response.setTotalStudents(totalStudents);
        response.setTotalFees(totalFees);
        response.setTotalCollected(totalCollected);
        response.setTotalOutstanding(totalOutstanding);
        response.setCollectionRate(collectionRate);
        response.setBreakdownByLevel(breakdownByLevel);

        return response;
    }
}