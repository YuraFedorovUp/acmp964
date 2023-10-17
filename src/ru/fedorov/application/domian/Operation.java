package ru.fedorov.application.domian;

public enum Operation {
    ADD("Add keyword ", " to "),
    SEARCH("Search ", ""),
    REMOVE("Remove keyword ", " from ");
    private String operationNameStart;
    private String divider;

    Operation(String operationNameStart, String divider) {
        this.operationNameStart = operationNameStart;
        this.divider = divider;
    }

    public String getOperationNameStart() {
        return operationNameStart;
    }

    public String getDivider() {
        return divider;
    }
}
