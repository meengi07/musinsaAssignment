package org.musinsa.assignment.musinsapayments.point.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record PointUseRequest(
    @Schema(description = "사용자 ID", example = "1")
    @NotNull(message = "사용자 ID는 필수입니다.")
    Long userId,
    @Schema(description = "주문 ID", example = "1")
    @NotNull(message = "주문 ID는 필수입니다.")
    Long orderId,
    @Schema(description = "사용할 포인트", example = "100")
    @NotNull(message = "사용할 포인트는 필수입니다.")
    Long amount
) {
}
