package ru.fedorov.application.domian;

public class Data {
    private final Operation operation;
    private final String keyword;
    private final String siteHost;

    public Data(Operation operation, String keyword, String siteHost) {
        this.operation = operation;
        this.keyword = keyword;
        this.siteHost = siteHost;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getSiteHost() {
        return siteHost;
    }
}