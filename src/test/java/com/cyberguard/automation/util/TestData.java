package com.cyberguard.automation.util;

public final class TestData {

    public static final String ADMIN_USERNAME = "admin@cyberguard.com";
    public static final String ADMIN_PASSWORD = "AdminSofka123456";
    public static final String VALID_SOURCE_IP = "192.168.1.100";

    public static final String ADMIN_EMAIL = "admin@cyberguard.com";
    public static final String HANDLER_EMAIL = "incident.handler@cyberguard.com";
    public static final String HANDLER_PASSWORD = "HandlerSofka123456";

    public static final long RUN_SUFFIX = System.currentTimeMillis();
    public static final String NEW_USER_EMAIL = "sp.test." + RUN_SUFFIX + "@cyberguard.com";
    public static final String NEW_USER_FULLNAME = "SP Tester " + RUN_SUFFIX;
    public static final String NEW_USER_USERNAME = "sp.test." + RUN_SUFFIX;
    public static final String NEW_USER_ROLE = "soc_analyst";

    public static final String NON_EXISTENT_UUID = "00000000-0000-0000-0000-000000000000";

    private TestData() {
    }
}
