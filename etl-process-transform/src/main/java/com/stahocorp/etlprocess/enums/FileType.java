package com.stahocorp.etlprocess.enums;

/**
 * Enum which contains file types. It is used by FileNameParser to redirect file flow.
 */
public enum FileType {
    INFO("info"),
    OPINIONS("opinions");

    private final String value;

    FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FileType getTypeForString(String x) {
        for (FileType type : FileType.values()) {
            if (type.value.equalsIgnoreCase(x)) {
                return type;
            }
        }
        return null;
    }
}
