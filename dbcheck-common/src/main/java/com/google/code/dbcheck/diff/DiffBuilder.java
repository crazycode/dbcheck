package com.google.code.dbcheck.diff;

import java.util.ArrayList;
import java.util.List;

public class DiffBuilder {

    private final List<DiffValue> diffValues;

    public DiffBuilder() {
        diffValues = new ArrayList<DiffValue>();
    }

    public DiffBuilder append(String name, String value, String storedValue) {
        DiffValue v = new DiffValue();
        v.setName(name);
        v.setRunningValue(value);
        v.setStoredValue(storedValue);
        if (value != null && !(value.equals(storedValue))) {
            diffValues.add(v);
        } else if (value == null && storedValue != null) {
            diffValues.add(v);
        }

        return this;
    }

    public DiffBuilder append(String name, boolean value, boolean storedValue) {
        DiffValue v = new DiffValue();
        v.setName(name);
        v.setRunningValue(Boolean.toString(value));
        v.setStoredValue(Boolean.toString(storedValue));
        if (!(value == storedValue)) {
            diffValues.add(v);
        }

        return this;
    }

    public DiffBuilder append(String name, int value, int storedValue) {
        DiffValue v = new DiffValue();
        v.setName(name);
        v.setRunningValue(Integer.toString(value));
        v.setStoredValue(Integer.toString(storedValue));
        if (!(value == storedValue)) {
            diffValues.add(v);
        }

        return this;
    }

    public DiffBuilder append(String name, short value, short storedValue) {
        DiffValue v = new DiffValue();
        v.setName(name);
        v.setRunningValue(Short.toString(value));
        v.setStoredValue(Short.toString(storedValue));
        if (!(value == storedValue)) {
            diffValues.add(v);
        }

        return this;
    }

    public List<DiffValue> getDiffValues() {
        return diffValues;
    }

}
