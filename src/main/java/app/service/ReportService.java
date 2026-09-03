package app.service;

import app.dto.CreateReportRequest;
import app.dto.UpdateReportRequest;
import app.exception.BusinessRuleException;
import app.exception.ReportNotFoundException;
import app.model.Report;
import app.model.ReportStatus;
import app.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public Report createReport(CreateReportRequest request) {

        Report report = reportRepository.save(initializeReport(request));
        log.info("Report created with id [{}] for {} with id [{}] in community [{}].",
                report.getId(), report.getTargetType().name().toLowerCase(), report.getTargetId(), report.getCommunityId());

        return report;
    }

    public Report updateReport(UUID id, UpdateReportRequest request) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("Report with id [%s] does not exist.".formatted(id)));

        if (report.getStatus() != ReportStatus.PENDING) {
            throw new BusinessRuleException("Report with id [%s] has already been resolved.".formatted(id));
        }

        if (request.getStatus() == ReportStatus.PENDING) {
            throw new BusinessRuleException("Cannot set report status back to pending.");
        }

        report.setStatus(request.getStatus());
        report.setResolvedById(request.getResolvedById());
        report.setResolvedOn(LocalDateTime.now());

        Report updatedReport = reportRepository.save(report);
        log.info("Report [{}] resolved with status [{}] by user with id [{}].",
                updatedReport.getId(), updatedReport.getStatus(), updatedReport.getResolvedById());
        return updatedReport;
    }

    public Page<Report> getReportsByCommunity(UUID communityId, ReportStatus status, Pageable pageable) {
        if (status == null) {
            return reportRepository.findByCommunityId(communityId, pageable);
        }

        return reportRepository.findByCommunityIdAndStatus(communityId, status, pageable);
    }

    private Report initializeReport(CreateReportRequest request) {
        return Report.builder()
                .targetType(request.getTargetType())
                .targetId(request.getTargetId())
                .communityId(request.getCommunityId())
                .reporterId(request.getReporterId())
                .reason(request.getReason())
                .details(request.getDetails())
                .status(ReportStatus.PENDING)
                .createdOn(LocalDateTime.now())
                .build();
    }
}
