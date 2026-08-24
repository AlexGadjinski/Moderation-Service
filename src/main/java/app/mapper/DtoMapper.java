package app.mapper;

import app.dto.ReportResponse;
import app.model.Report;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoMapper {

    public static ReportResponse toReportResponse(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .targetType(report.getTargetType())
                .targetId(report.getTargetId())
                .communityId(report.getCommunityId())
                .reporterId(report.getReporterId())
                .reason(report.getReason())
                .details(report.getDetails())
                .status(report.getStatus())
                .resolvedById(report.getResolvedById())
                .createdOn(report.getCreatedOn())
                .resolvedOn(report.getResolvedOn())
                .build();
    }
}
