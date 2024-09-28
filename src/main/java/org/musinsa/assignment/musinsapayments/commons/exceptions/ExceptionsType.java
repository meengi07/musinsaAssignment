package org.musinsa.assignment.musinsapayments.commons.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionsType {
    POINT_USE_EXCEED(400, "사용가능한 포인트가 부족합니다."),
    POINT_CANCEL_EXCEED(400, "보유한 포인트보다 많은 포인트를 취소할 수 없습니다."),
    POINT_ALREADY_USED(400, "이미 사용된 포인트는 취소할 수 없습니다."),
    POINT_EARN_MINIMUM(400, "최소 적립가능 포인트보다 적은 포인트입니다."),
    POINT_EARN_MAXIMUM(400, "최대 적립가능 포인트보다 많은 포인트입니다."),
    POINT_MAXIMUM(400, "최대 보유 가능 포인트를 초과하였습니다."),
    POINT_NOT_FOUND(400, "포인트를 찾을 수 없습니다."),
    POINT_LEDGER_NOT_FOUND(400, "포인트 내역을 찾을 수 없습니다."),
    INVALID_PARAMETER(400, "잘못된 파라미터가 전달되었습니다."),
    SERVER_ERROR(500, "서버에 문제가 발생하였습니다."),
    NULL_POINTER(500, "NULL 오류가 발생하였습니다.");

    private final int status;
    private final String message;

    ExceptionsType(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
