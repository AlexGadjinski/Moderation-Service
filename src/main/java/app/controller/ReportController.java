package app.controller;

import app.dto.CreateReportRequest;
import app.dto.ReportResponse;
import app.dto.UpdateReportRequest;
import app.mapper.DtoMapper;
import app.model.Report;
import app.model.ReportStatus;
import app.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponse> createReport(@Valid @RequestBody CreateReportRequest request) {
        Report report = reportService.createReport(request);
        ReportResponse response = DtoMapper.toReportResponse(report);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportResponse> updateReport(@PathVariable UUID id,
                                                       @Valid @RequestBody UpdateReportRequest request) {
        Report report = reportService.updateReport(id, request);
        ReportResponse response = DtoMapper.toReportResponse(report);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReportResponse>> getReportsByCommunity(@RequestParam UUID communityId,
                                                                      @RequestParam(required = false) ReportStatus status) {
        List<ReportResponse> response = reportService.getReportsByCommunity(communityId, status).stream()
                .map(DtoMapper::toReportResponse)
                .toList();

        return ResponseEntity.ok(response);
    }
}
