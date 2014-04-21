package com.jason.usedcar.model;

public class Result {
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
