package com.jason.usedcar.response;

public class Response {

    private boolean executionResult;

    private String message;

    public boolean isExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(boolean executionResult) {
        this.executionResult = executionResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
