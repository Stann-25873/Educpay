package com.edupay.serviceImpl;

import com.edupay.dto.response.ReceiptResponse;
import com.edupay.entity.Receipt;
import com.edupay.exception.ResourceNotFound;
import com.edupay.exception.UnauthorizedTenantAccess;
import com.edupay.mapper.ReceiptMapper;
import com.edupay.repository.ReceiptRepository;
import com.edupay.security.SecurityUtils;
import com.edupay.service.ReceiptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ReceiptMapper receiptMapper;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository, ReceiptMapper receiptMapper) {
        this.receiptRepository = receiptRepository;
        this.receiptMapper = receiptMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public ReceiptResponse getReceipt(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Receipt receipt = receiptRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Receipt not found: " + id));
        if (!receipt.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        return receiptMapper.toResponse(receipt);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReceiptResponse> getAllReceipts() {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return receiptRepository.findAll().stream()
                .filter(r -> r.getTenantId().equals(tenantId))
                .map(receiptMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReceiptResponse getReceiptByPayment(UUID paymentId) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Receipt receipt = receiptRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new ResourceNotFound("Receipt not found for payment: " + paymentId));
        if (!receipt.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        return receiptMapper.toResponse(receipt);
    }
}