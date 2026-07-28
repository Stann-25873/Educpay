package com.edupay.service;

import com.edupay.dto.request.CreateFeeRequest;
import com.edupay.dto.response.FeeResponse;
import java.util.List;
import java.util.UUID;

public interface FeeService {
    FeeResponse createFee(CreateFeeRequest request);
    FeeResponse getFee(UUID id);
    List<FeeResponse> getAllFees();
    List<FeeResponse> getFeesByLevel(String level);
    FeeResponse updateFee(UUID id, CreateFeeRequest request);
    void deleteFee(UUID id);
}
