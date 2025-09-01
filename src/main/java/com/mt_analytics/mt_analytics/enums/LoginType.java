package com.mt_analytics.mt_analytics.enums;

public enum LoginType {
    EMAIL_PASSWORD("Email and password"),
    MOBILE("Mobile otp"),
    GOOGLE("Google token"),
    FACEBOOK("Facebook token");

    private final String displayName;

    LoginType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
