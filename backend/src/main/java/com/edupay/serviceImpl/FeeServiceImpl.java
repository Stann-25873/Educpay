package com.edupay.serviceImpl;

import com.edupay.dto.request.CreateFeeRequest;
import com.edupay.dto.response.FeeResponse;
import com.edupay.entity.Fee;
import com.edupay.entity.Institution;
import com.edupay.exception.ResourceNotFound;
import com.edupay.exception.UnauthorizedTenantAccess;
import com.edupay.mapper.FeeMapper;
import com.edupay.repository.FeeRepository;
import com.edupay.repository.InstitutionRepository;
import com.edupay.security.SecurityUtils;
import com.edupay.service.FeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class FeeServiceImpl implements FeeService {

    private final FeeRepository feeRepository;
    private final InstitutionRepository institutionRepository;
    private final FeeMapper feeMapper;

    public FeeServiceImpl(FeeRepository feeRepository,
                          InstitutionRepository institutionRepository,
                          FeeMapper feeMapper) {
        this.feeRepository = feeRepository;
        this.institutionRepository = institutionRepository;
        this.feeMapper = feeMapper;
    }

    @Override
    @Transactional
    public FeeResponse createFee(CreateFeeRequest request) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Institution institution = institutionRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFound("Institution not found"));

        Fee fee = new Fee();
        fee.setInstitution(institution);
        fee.setCode(request.getCode());
        fee.setTitle(request.getTitle());
        fee.setDescription(request.getDescription());
        fee.setAmount(request.getAmount());
        fee.setCurrency(request.getCurrency());
        fee.setBillingPeriod(request.getBillingPeriod());
        fee.setLevel(request.getLevel());

        fee = feeRepository.save(fee);
        return feeMapper.toResponse(fee);
    }

    @Override
    @Transactional(readOnly = true)
    public FeeResponse getFee(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Fee fee = feeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Fee not found: " + id));
        if (!fee.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        return feeMapper.toResponse(fee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeResponse> getAllFees() {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return feeRepository.findAll().stream()
                .filter(f -> f.getTenantId().equals(tenantId))
                .map(feeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeResponse> getFeesByLevel(String level) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return feeRepository.findAll().stream()
                .filter(f -> f.getTenantId().equals(tenantId) && level.equals(f.getLevel()))
                .map(feeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public FeeResponse updateFee(UUID id, CreateFeeRequest request) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Fee fee = feeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Fee not found: " + id));
        if (!fee.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        fee.setCode(request.getCode());
        fee.setTitle(request.getTitle());
        fee.setDescription(request.getDescription());
        fee.setAmount(request.getAmount());
        fee.setCurrency(request.getCurrency());
        fee.setBillingPeriod(request.getBillingPeriod());
        fee.setLevel(request.getLevel());
        fee = feeRepository.save(fee);
        return feeMapper.toResponse(fee);
    }

    @Override
    @Transactional
    public void deleteFee(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Fee fee = feeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Fee not found: " + id));
        if (!fee.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        feeRepository.delete(fee);
    }
}