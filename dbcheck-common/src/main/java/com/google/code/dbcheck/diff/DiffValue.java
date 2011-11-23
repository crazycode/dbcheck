package com.google.code.dbcheck.diff;

public class DiffValue {

    private String name;

    private String runningValue;

    private String storedValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRunningValue() {
        return runningValue;
    }

    public void setRunningValue(String runningValue) {
        this.runningValue = runningValue;
    }

    public String getStoredValue() {
        return storedValue;
    }

    public void setStoredValue(String storedValue) {
        this.storedValue = storedValue;
    }

}
