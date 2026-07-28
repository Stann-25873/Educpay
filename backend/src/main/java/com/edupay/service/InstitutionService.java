package com.edupay.service;

import com.edupay.dto.request.CreateInstitutionRequest;
import com.edupay.dto.response.InstitutionResponse;
import java.util.List;
import java.util.UUID;

public interface InstitutionService {
    InstitutionResponse createInstitution(CreateInstitutionRequest request);
    InstitutionResponse getInstitution(UUID id);
    List<InstitutionResponse> getAllInstitutions();
    InstitutionResponse updateInstitution(UUID id, CreateInstitutionRequest request);
    void deleteInstitution(UUID id);
}
