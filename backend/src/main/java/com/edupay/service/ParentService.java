package com.edupay.service;

import com.edupay.dto.request.CreateParentRequest;
import com.edupay.dto.response.ParentResponse;
import java.util.List;
import java.util.UUID;

public interface ParentService {
    ParentResponse createParent(CreateParentRequest request);
    ParentResponse getParent(UUID id);
    List<ParentResponse> getAllParents();
    ParentResponse updateParent(UUID id, CreateParentRequest request);
    void deleteParent(UUID id);
}
