package com.edupay.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class ReportResponse {
    private String reportType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long totalStudents;
    private Long totalFees;
    private BigDecimal totalCollected;
    private BigDecimal totalOutstanding;
    private BigDecimal collectionRate;
    private Map<String, BigDecimal> breakdownByLevel;
    private Map<LocalDate, BigDecimal> dailyCollection;
    private String downloadUrl;

    public ReportResponse() {}

    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public Long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(Long totalStudents) { this.totalStudents = totalStudents; }
    public Long getTotalFees() { return totalFees; }
    public void setTotalFees(Long totalFees) { this.totalFees = totalFees; }
    public BigDecimal getTotalCollected() { return totalCollected; }
    public void setTotalCollected(BigDecimal totalCollected) { this.totalCollected = totalCollected; }
    public BigDecimal getTotalOutstanding() { return totalOutstanding; }
    public void setTotalOutstanding(BigDecimal totalOutstanding) { this.totalOutstanding = totalOutstanding; }
    public BigDecimal getCollectionRate() { return collectionRate; }
    public void setCollectionRate(BigDecimal collectionRate) { this.collectionRate = collectionRate; }
    public Map<String, BigDecimal> getBreakdownByLevel() { return breakdownByLevel; }
    public void setBreakdownByLevel(Map<String, BigDecimal> breakdownByLevel) { this.breakdownByLevel = breakdownByLevel; }
    public Map<LocalDate, BigDecimal> getDailyCollection() { return dailyCollection; }
    public void setDailyCollection(Map<LocalDate, BigDecimal> dailyCollection) { this.dailyCollection = dailyCollection; }
    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }
}
