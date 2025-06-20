package com.udemine.course_manage.exception;

public enum ErrorCode {
    USER_EXISTED(1001,"User existed"),
    EMAIL_EXISTED(1002,"Email existed"),
    INFORMATION_EXISTED(1003,"Information existed"),
    NAME_EXISTED(1005,"Name existed"),
    UNCATEGORIZED_EXCEPTION(9999,"uncategorized error"),
    USERNAME_INVALID(1006,"Username at least 3 characters"),
    PASSWORD_INVALID(1007,"Password must be at least 8 characters"),
    TITLE_EXISTED(1008,"Title existed"),
    ROLE_EXISTED(1009, "Role existed");

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
