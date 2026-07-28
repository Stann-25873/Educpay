package com.edupay.serviceImpl;

import com.edupay.dto.request.CreateAccountantRequest;
import com.edupay.dto.response.AccountantResponse;
import com.edupay.entity.Accountant;
import com.edupay.entity.Institution;
import com.edupay.exception.ResourceNotFound;
import com.edupay.exception.UnauthorizedTenantAccess;
import com.edupay.mapper.AccountantMapper;
import com.edupay.repository.AccountantRepository;
import com.edupay.repository.InstitutionRepository;
import com.edupay.security.SecurityUtils;
import com.edupay.service.AccountantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AccountantServiceImpl implements AccountantService {

    private final AccountantRepository accountantRepository;
    private final InstitutionRepository institutionRepository;
    private final AccountantMapper accountantMapper;

    public AccountantServiceImpl(AccountantRepository accountantRepository,
                                  InstitutionRepository institutionRepository,
                                  AccountantMapper accountantMapper) {
        this.accountantRepository = accountantRepository;
        this.institutionRepository = institutionRepository;
        this.accountantMapper = accountantMapper;
    }

    @Override
    @Transactional
    public AccountantResponse createAccountant(CreateAccountantRequest request) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Institution institution = institutionRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFound("Institution not found"));

        Accountant accountant = new Accountant();
        accountant.setInstitution(institution);
        accountant.setFirstName(request.getFirstName());
        accountant.setLastName(request.getLastName());
        accountant.setEmail(request.getEmail());
        accountant.setPhone(request.getPhone());

        accountant = accountantRepository.save(accountant);
        return accountantMapper.toResponse(accountant);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountantResponse getAccountant(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Accountant accountant = accountantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Accountant not found: " + id));
        if (!accountant.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        return accountantMapper.toResponse(accountant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountantResponse> getAllAccountants() {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return accountantRepository.findAll().stream()
                .filter(a -> a.getTenantId().equals(tenantId))
                .map(accountantMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public AccountantResponse updateAccountant(UUID id, CreateAccountantRequest request) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Accountant accountant = accountantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Accountant not found: " + id));
        if (!accountant.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        accountant.setFirstName(request.getFirstName());
        accountant.setLastName(request.getLastName());
        accountant.setEmail(request.getEmail());
        accountant.setPhone(request.getPhone());
        accountant = accountantRepository.save(accountant);
        return accountantMapper.toResponse(accountant);
    }

    @Override
    @Transactional
    public void deleteAccountant(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Accountant accountant = accountantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Accountant not found: " + id));
        if (!accountant.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        accountantRepository.delete(accountant);
    }
}