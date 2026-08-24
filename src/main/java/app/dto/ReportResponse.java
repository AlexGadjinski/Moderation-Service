package app.dto;

import app.model.ReportReason;
import app.model.ReportStatus;
import app.model.TargetType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class ReportResponse {

    private UUID id;
    private TargetType targetType;
    private UUID targetId;
    private UUID communityId;
    private UUID reporterId;
    private ReportReason reason;
    private String details;
    private ReportStatus status;
    private UUID resolvedById;
    private LocalDateTime createdOn;
    private LocalDateTime resolvedOn;
}
