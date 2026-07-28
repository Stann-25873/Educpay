package com.edupay.service;

import com.edupay.dto.response.ReportResponse;
import java.time.LocalDate;

public interface ReportService {
    ReportResponse getRevenueSummary(LocalDate startDate, LocalDate endDate);
}