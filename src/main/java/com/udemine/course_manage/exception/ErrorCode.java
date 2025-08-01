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
    USER_NOT_EXISTED(1008,"User not existed"),
    COURSE_NOT_FOUND(1010,"course not found"),
    ROLE_EXISTED(1009, "Role existed"),
    CATEGORY_NOT_FOUND(1011,"Category not found"),
    CODE_EXISTED(1012,"Code existed"),
    COURSE_DISCOUNT_NOT_FOUND(1013,"course discount not found"),
    DELETE_DONE(1014,"Delete done"),
    CATEGORY_EXISTED(1015,"Category existed"),
    REVIEW_NOT_FOUND(1016,"review not found"),
    POSITION_MODULE_EXISTED(1017,"position existed"),
    LESSONS_NOT_EXIST(1018,"Lessons not existed"),
    ENROLLMENT_NOT_FOUND(1019,"Enrollment not found"),
    REVENUE_EXISTED(1020,"Revenue existed"),
    REVENUE_NOT_FOUND(1021,"Revenue not found"),
    MODULE_NOT_EXIST(1022,"Module not exist"),
    EXERCISE_NOT_FOUND(1023,"Exercise not found"),
    SUBMISSION_EXISTED(1024,"Submission existed"),
    SUBMISSION_NOT_FOUND(1025,"Submission not found"),
    LATE_SUBMISSION_NOT_FOUND(1026,"Late submission not found"),
    LESSON_RESOURCE_NOT_FOUND(1027,"Lesson resource not found"),
    USER_NOT_INSTRUCTOR(1028,"User is not instructor"),
    SCORING_NOT_FOUND(1029,"Scoring not found"),
    MESSAGE_EXISTED(1090,"Message existed"),
    MESSAGE_NOT_FOUND(1091,"Message not found"),
    USER_NOT_EXIST(1099,"user not exist"),
    NOTIFICATION_NOT_EXIST(1098,"Notification not existed"),
    SCORING_EXISTED(1030,"Scoring existed"),
    LESSON_COMMENT_NOT_FOUND(1031,"Lesson comment not found"),
    LESSON_RESPONSE_NOT_FOUND(1033,"Lesson response not found"),
    USER_ROLE_EXISTED(1032,"User role existed"),
    ROLE_NOT_FOUND(1033,"Role not found"),
    USER_ROLE_NOT_FOUND(1034,"User role not found"),
    REVIEW_REQUEST_NOT_FOUND(1035,"Review request not found"),
    UNAUTHENTICATED(1036,"Unauthenticated"),
    ERROR_VERIFY(1037,"Error verify"),
    INVALID_TOKEN(1038,"Invalid token"),
    Expired_Token(1039,"Expired token"),;

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
