package hust.util;

import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
	//$2a$12$5udY7LFE.Nu23zvJ9xXTLuTPAaksqbmLoXP1tQsPQ9M.HYkE0fAn6
	// abc12345
	//public LoginController loginController;
	//public LogoutController logoutController;
	//public Admin_AddStaffForm adminAddStaffForm;
	
	private static final AuthService INSTANCE = new AuthService();
	
	private AuthService() {}
	
	public static AuthService getInstance() {
		return INSTANCE;
	}
	
	/**
     * This function to encrypt password. Using salt (10)
     * @param password
     * @return BCrypt.hashpw(password, gensalt)
     */
    public String bEncrypt(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(10));
	}
    
    /**
     * This function to check valid hasing password from database
     * @param currentPassword
     * @param storePassword
     * @return validate
     */
    public boolean validPassword(String currentPassword, String storedPassword) {
    	boolean validate = BCrypt.checkpw(currentPassword, storedPassword);
    	return validate;
    }
} 
