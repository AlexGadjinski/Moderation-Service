package app.service;

import app.dto.CreateReportRequest;
import app.dto.UpdateReportRequest;
import app.exception.BusinessRuleException;
import app.exception.ReportNotFoundException;
import app.model.Report;
import app.model.ReportStatus;
import app.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public Report createReport(CreateReportRequest request) {
        Report report = Report.builder()
                .targetType(request.getTargetType())
                .targetId(request.getTargetId())
                .communityId(request.getCommunityId())
                .reporterId(request.getReporterId())
                .reason(request.getReason())
                .details(request.getDetails())
                .status(ReportStatus.PENDING)
                .createdOn(LocalDateTime.now())
                .build();

        return reportRepository.save(report);
    }

    public Report updateReport(UUID id, UpdateReportRequest request) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("Report with id [%s] does not exist.".formatted(id)));

        if (request.getStatus() == ReportStatus.PENDING) {
            throw new BusinessRuleException("Cannot set report status back to pending.");
        }

        report.setStatus(request.getStatus());
        report.setResolvedById(request.getResolvedById());
        report.setResolvedOn(LocalDateTime.now());

        return reportRepository.save(report);
    }

    public List<Report> getReportsByCommunity(UUID communityId, ReportStatus status) {

        if (status != null) {
            return reportRepository.findByCommunityIdAndStatus(communityId, status);
        }

        return reportRepository.findByCommunityId(communityId);
    }
}
