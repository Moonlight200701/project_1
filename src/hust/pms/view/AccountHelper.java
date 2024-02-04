package hust.pms.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hust.common.Constants;

public class AccountHelper {
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    public static final Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("^([ .-]*[0-9]+[ .-]*)+$");

    public int textAccountFilled(String username, String password) {
    	if (username.equals("") && password.equals("")) {
    		return Constants.USERNAME_PASSWORD_NOT_FILL;
		} else if (username.equals("")) {
			return Constants.USERNAME_NOT_FILL;
		} else if (password.equals("")) {
			return Constants.PASSWORD_NOT_FILL;
		}
    	return Constants.UP_WRONG_TYPE;
    }
    

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    

    public static boolean validatePhoneNumber(String phoneNumber) {
        if(phoneNumber.length()>0){
            Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(phoneNumber);
            return matcher.find();
        }
        return true;
    }
   
}
