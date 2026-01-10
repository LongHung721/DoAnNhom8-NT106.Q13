package com.netcafe.backend.dto;

public class ErrorResponse {
    private ErrorBody error;

    public ErrorResponse(String message) {
        this.error = new ErrorBody(message);
    }

    public ErrorBody getError() { return error; }

    public static class ErrorBody {
        private String message;
        public ErrorBody(String message) { this.message = message; }
        public String getMessage() { return message; }
    }
}
