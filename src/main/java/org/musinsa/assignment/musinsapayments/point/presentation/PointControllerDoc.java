package org.musinsa.assignment.musinsapayments.point.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Point", description = "포인트 적립/사용/취소 API")
public interface PointControllerDoc {

    @Operation(summary = "포인트 적립", description = "포인트를 적립합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "포인트 적립 성공", content=
            @Content(mediaType = "application/json", schema = @Schema(implementation = PointEarnResponse.class))
        ),
        @ApiResponse(responseCode = "400", description = "포인트 적립 실패")
    })
    ResponseEntity<PointEarnResponse> earnPoint(PointEarnRequest earnRequest);

    @Operation(summary = "포인트 취소", description = "적립된 포인트를 취소합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "포인트 취소 성공", content=
            @Content(mediaType = "application/json", schema = @Schema(implementation = PointEarnResponse.class))
        ),
        @ApiResponse(responseCode = "400", description = "포인트 취소 실패")
    })
    ResponseEntity<PointEarnResponse> cancelPoint(Long pointId);

    @Operation(summary = "포인트 사용", description = "포인트를 사용합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "포인트 사용 성공", content=
            @Content(mediaType = "application/json", schema = @Schema(implementation = PointUseResponse.class))
        ),
        @ApiResponse(responseCode = "400", description = "포인트 사용 실패")
    })
    ResponseEntity<PointUseResponse> usePoint(PointUseRequest useRequest);

    @Operation(summary = "포인트 사용 취소", description = "사용한 포인트를 취소합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "포인트 사용 취소 성공", content=
            @Content(mediaType = "application/json", schema = @Schema(implementation = PointUseResponse.class))
        ),
        @ApiResponse(responseCode = "400", description = "포인트 사용 취소 실패")
    })
    ResponseEntity<PointUseResponse> cancelUsedPoint(PointUseRequest useRequest);

}
