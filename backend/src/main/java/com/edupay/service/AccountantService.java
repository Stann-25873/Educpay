package com.edupay.service;

import com.edupay.dto.request.CreateAccountantRequest;
import com.edupay.dto.response.AccountantResponse;
import java.util.List;
import java.util.UUID;

public interface AccountantService {
    AccountantResponse createAccountant(CreateAccountantRequest request);
    AccountantResponse getAccountant(UUID id);
    List<AccountantResponse> getAllAccountants();
    AccountantResponse updateAccountant(UUID id, CreateAccountantRequest request);
    void deleteAccountant(UUID id);
}