package by.training.sokolov.core;

public class CommonAppConstants {

//    user@domain.com :       true
//    user@domain.co.in :     true
//    user1@domain.com :      true
//    user.name@domain.com :  true
//    user#@domain.co.in :    true
//    user@domaincom :        true
//
//    user#domain.com :       false
//    @yahoo.com :            false
    public static final String EMAIL_PATTERN = "^(.+)@(.+)$";

//    ^                 # start-of-string
//    (?=.*[0-9])       # a digit must occur at least once
//    (?=.*[a-z])       # a lower case letter must occur at least once
//    (?=.*[A-Z])       # an upper case letter must occur at least once
//    (?=.*[@#$%^&+=])  # a special character must occur at least once
//    (?=\S+$)          # no whitespace allowed in the entire string
//    .{8,}             # anything, at least eight places though
//    $                 # end-of-string
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    public static final String PHONE_NUMBER_PATTERN = "^\\+\\d{12}$";


}
