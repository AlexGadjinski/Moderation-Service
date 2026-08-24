package app.dto;

import app.model.ReportReason;
import app.model.TargetType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateReportRequest {

    @NotNull(message = "Target type is required")
    private TargetType targetType;

    @NotNull(message = "Target id is required")
    private UUID targetId;

    @NotNull(message = "Community id is required")
    private UUID communityId;

    @NotNull(message = "Reporter id is required")
    private UUID reporterId;

    @NotNull(message = "Reason is required")
    private ReportReason reason;

    @Size(max = 2000, message = "Details must not exceed 2000 characters")
    private String details;
}
