/**
 * 
 */
package com.atroshonok.utilits;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class validates the input data of registration form
 *
 * @author Atroshonok Ivan
 *
 */
public class InputDataValidator {

    /* Registration form */
    private static final Pattern LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9]{6,45}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9]{6,45}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9.]+@[a-zA-Z0-9]+\\.[a-zA-Z]+$");
    private static final Pattern FIRSTNAME_PATTERN = Pattern.compile("^[a-zA-Zа-яА-ЯёЁ ]+$");
    private static final Pattern LASTNAME_PATTERN = Pattern.compile("^[a-zA-Zа-яА-ЯёЁ ]+$");
    private static final Pattern AGE_PATTERN = Pattern.compile("^[0-9]{1,3}$");

    public static boolean validateLogin(CharSequence inputData) {
	Matcher matcher = LOGIN_PATTERN.matcher(inputData);
	return matcher.matches();
    }

    public static boolean validatePassword(CharSequence inputData) {
	Matcher matcher = PASSWORD_PATTERN.matcher(inputData);
	return matcher.matches();
    }

    public static boolean validateEmail(CharSequence inputData) {
	Matcher matcher = EMAIL_PATTERN.matcher(inputData);
	return matcher.matches();
    }

    public static boolean validateFirstName(CharSequence inputData) {
	Matcher matcher = FIRSTNAME_PATTERN.matcher(inputData);
	return matcher.matches();
    }

    public static boolean validateLastName(CharSequence inputData) {
	Matcher matcher = LASTNAME_PATTERN.matcher(inputData);
	return matcher.matches();
    }

    public static boolean validateAge(CharSequence inputData) {
	Matcher matcher = AGE_PATTERN.matcher(inputData);
	return matcher.matches();
    }

}
