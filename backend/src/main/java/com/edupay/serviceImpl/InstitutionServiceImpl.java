package com.edupay.serviceImpl;

import com.edupay.dto.request.CreateInstitutionRequest;
import com.edupay.dto.response.InstitutionResponse;
import com.edupay.entity.Institution;
import com.edupay.exception.ResourceNotFound;
import com.edupay.mapper.InstitutionMapper;
import com.edupay.repository.InstitutionRepository;
import com.edupay.service.InstitutionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    public InstitutionServiceImpl(InstitutionRepository institutionRepository,
                                  InstitutionMapper institutionMapper) {
        this.institutionRepository = institutionRepository;
        this.institutionMapper = institutionMapper;
    }

    @Override
    @Transactional
    public InstitutionResponse createInstitution(CreateInstitutionRequest request) {
        Institution institution = new Institution();
        institution.setName(request.getName());
        institution.setType(request.getType());
        institution = institutionRepository.save(institution);
        return institutionMapper.toResponse(institution);
    }

    @Override
    @Transactional(readOnly = true)
    public InstitutionResponse getInstitution(UUID id) {
        Institution institution = institutionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Institution not found with id: " + id));
        return institutionMapper.toResponse(institution);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstitutionResponse> getAllInstitutions() {
        return institutionRepository.findAll().stream()
                .map(institutionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public InstitutionResponse updateInstitution(UUID id, CreateInstitutionRequest request) {
        Institution institution = institutionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Institution not found with id: " + id));
        institution.setName(request.getName());
        institution.setType(request.getType());
        institution = institutionRepository.save(institution);
        return institutionMapper.toResponse(institution);
    }

    @Override
    @Transactional
    public void deleteInstitution(UUID id) {
        Institution institution = institutionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Institution not found with id: " + id));
        institutionRepository.delete(institution);
    }
}
