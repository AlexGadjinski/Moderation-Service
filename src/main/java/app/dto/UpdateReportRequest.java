package app.dto;

import app.model.ReportStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateReportRequest {

    @NotNull(message = "Status is required")
    private ReportStatus status;

    @NotNull(message = "Resolved by id is required")
    private UUID resolvedById;
}
