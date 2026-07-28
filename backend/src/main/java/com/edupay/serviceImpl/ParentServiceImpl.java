package com.edupay.serviceImpl;

import com.edupay.dto.request.CreateParentRequest;
import com.edupay.dto.response.ParentResponse;
import com.edupay.entity.Institution;
import com.edupay.entity.Parent;
import com.edupay.exception.ResourceNotFound;
import com.edupay.exception.UnauthorizedTenantAccess;
import com.edupay.mapper.ParentMapper;
import com.edupay.repository.InstitutionRepository;
import com.edupay.repository.ParentRepository;
import com.edupay.security.SecurityUtils;
import com.edupay.service.ParentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final InstitutionRepository institutionRepository;
    private final ParentMapper parentMapper;

    public ParentServiceImpl(ParentRepository parentRepository,
        InstitutionRepository institutionRepository,
ParentMapper parentMapper) {
        this.parentRepository = parentRepository;
        this.institutionRepository = institutionRepository;
        this.parentMapper = parentMapper;
    }

    @Override
    @Transactional
    public ParentResponse createParent(CreateParentRequest request) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Institution institution = institutionRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFound("Institution not found"));

        Parent parent = new Parent();
        parent.setInstitution(institution);
        parent.setFirstName(request.getFirstName());
        parent.setLastName(request.getLastName());
        parent.setEmail(request.getEmail());
        parent.setPhone(request.getPhone());

        parent = parentRepository.save(parent);
        return parentMapper.toResponse(parent);
    }

    @Override
    @Transactional(readOnly = true)
    public ParentResponse getParent(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Parent not found: " + id));
        if (!parent.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        return parentMapper.toResponse(parent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParentResponse> getAllParents() {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return parentRepository.findAll().stream()
                .filter(p -> p.getTenantId().equals(tenantId))
                .map(parentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ParentResponse updateParent(UUID id, CreateParentRequest request) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Parent not found: " + id));
        if (!parent.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        parent.setFirstName(request.getFirstName());
        parent.setLastName(request.getLastName());
        parent.setEmail(request.getEmail());
        parent.setPhone(request.getPhone());
        parent = parentRepository.save(parent);
        return parentMapper.toResponse(parent);
    }

    @Override
    @Transactional
    public void deleteParent(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Parent not found: " + id));
        if (!parent.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        parentRepository.delete(parent);
    }
}