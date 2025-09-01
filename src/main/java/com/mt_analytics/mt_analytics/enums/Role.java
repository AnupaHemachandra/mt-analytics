package com.mt_analytics.mt_analytics.enums;

public enum Role {
    CUSTOMER("Customer"),
    SUPERADMIN("Super Admin"),
    ADMIN("Admin"),
    TECH("Tech Support");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
