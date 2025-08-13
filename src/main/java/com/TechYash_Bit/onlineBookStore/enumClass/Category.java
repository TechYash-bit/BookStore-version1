package com.TechYash_Bit.onlineBookStore.enumClass;

public enum Category {
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    SCIENCE("Science"),
    HISTORY("History"),
    BIOGRAPHY("Biography"),
    TECHNOLOGY("Technology"),
    CHILDREN("Children"),
    EDUCATION("Education");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
