package hust.util;

import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

	private static final AuthService INSTANCE = new AuthService();
	
	private AuthService() {}
	
	public static AuthService getInstance() {
		return INSTANCE;
	}
	

    public String bEncrypt(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(10));
	}
    
 
    public boolean validPassword(String currentPassword, String storedPassword) {
    	boolean validate = BCrypt.checkpw(currentPassword, storedPassword);
    	return validate;
    }
} 
