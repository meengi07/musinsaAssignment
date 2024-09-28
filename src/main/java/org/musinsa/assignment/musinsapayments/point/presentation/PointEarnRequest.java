package org.musinsa.assignment.musinsapayments.point.presentation;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record PointEarnRequest(
    @Schema(description = "사용자 ID", example = "1")
    @NotNull(message = "사용자 ID는 필수입니다.")
    Long userId,
    @Schema(description = "적립금액", example = "100")
    @NotNull(message = "적립금액은 필수입니다.")
    Long amount,
    @Schema(description = "수동 적립 여부", example = "false")
    boolean manually,
    @Schema(description = "만료일", example = "2024-12-31")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate expireDate
) {
}
