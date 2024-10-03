package com.hhplus.architecture.common.exception;

public record ErrorResponse(
        String code,
        String message
) {
}
