package com.vanhuuhien99.school_device_management.customtypes;

public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Gender fromValue(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.getValue().equals(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + value);
    }
}
