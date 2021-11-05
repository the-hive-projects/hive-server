package org.thehive.hiveserver.validation;

public class ValidationConstraints {

    //---USER---
    //Username
    public static final int USER_USERNAME_LENGTH_MIN=4;
    public static final int USER_USERNAME_LENGTH_MAX=64;
    public static final String USER_USERNAME_PATTERN_REGEXP="^[a-zA-Z0-9]+$";
    //Password
    public static final int USER_PASSWORD_LENGTH_MIN =4;
    public static final int USER_PASSWORD_LENGTH_MAX =64;
    public static final String USER_PASSWORD_PATTERN_REGEXP ="^[a-zA-Z0-9]+$";

    //---USERINFO---
    //Firstname
    public static final int USERINFO_FIRSTNAME_LENGTH_MIN=4;
    public static final int USERINFO_FIRSTNAME_LENGTH_MAX=64;
    public static final String USERINFO_FIRSTNAME_PATTERN_REGEXP="^[a-zA-Z0-9]+$";
    //Lastname
    public static final int USERINFO_LASTNAME_LENGTH_MIN=4;
    public static final int USERINFO_LASTNAME_LENGTH_MAX=64;
    public static final String USERINFO_LASTNAME_PATTERN_REGEXP="^[a-zA-Z0-9]+$";


}
