package fr.esgi.app.util;

public class TestConstants {
    public static final String URL_ADMIN_BASIC = "/api/v1/admin";
    public static final String URL_ADMIN_ADD = "/api/v1/admin/add";
    public static final String URL_ADMIN_EDIT = "/api/v1/admin/edit";
    public static final String URL_ADMIN_GET_USER = "/api/v1/admin/user/";
    public static final String URL_AUTH_BASIC = "/api/v1/auth";
    public static final String URL_AUTH_LOGIN = "/api/v1/auth/login";
    public static final String URL_AUTH_FORGOT = "/api/v1/auth/forgot";
    public static final String URL_AUTH_RESET = "/api/v1/auth/reset";
    public static final String URL_REGISTRATION_BASIC = "/api/v1/registration";
    public static final String URL_REGISTRATION_ACTIVATE = "/api/v1/registration/activate/{code}";
    public static final String URL_USERS_BASIC = "/api/v1/users";

    public static final Integer USER_ID = 122;
    public static final String USER_EMAIL = "test123@test.com";
    public static final String USER_PASSWORD = "admin123";
    public static final String ROLE_USER = "USER";
    public static final String USER_PROVIDER = "LOCAL";

    public static final String USER_PASSWORD_RESET_CODE = "3f9bcdb0-2241-4c34-803e-598b497d571f";
    public static final String USER_ACTIVATION_CODE = "8e97dc37-2cf5-47e2-98e0";
    public static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE2MjExODI4MTcsImV4cCI6MjIyNTk4MjgxN30.5GxJbuta48cVrn9EWYKrSQruk9jm06fpBu87JxTY_uk";

    public static final Integer USER2_ID = 126;
    public static final String USER2_EMAIL = "helloworld@test.com";
    public static final String USER2_NAME = "John2";

    public static final String EMAIL_FAILURE = "1t2e3st123@test.com";
    public static final String ADMIN_EMAIL = "admin@gmail.com";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Doe";
}
