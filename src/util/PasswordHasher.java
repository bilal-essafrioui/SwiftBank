package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordHasher {
	
	
	// Hash the input password using SHA-256 and encode it in Base64. 
	public static String hashPassword(String password) {
		try {
			
			// Create SHA-256 digest instance
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			 // Compute the hash of the password bytes
			byte[] hashedBytes = md.digest(password.getBytes());
			
			// Encode the hash bytes to a Base64 string for storage
			return Base64.getEncoder().encodeToString(hashedBytes);
			
		} catch (NoSuchAlgorithmException e) {
			// If SHA-256 algorithm is not available, throw a runtime exception
			throw new RuntimeException("Error hashing password ", e);
		}
	}
	
	// method for verifying the password
	public static boolean verifyPassword (String plainPassword, String hashedPassword) {
		String hashedInput = hashPassword(plainPassword);
		return hashedInput.equals(hashedPassword);
	}

}
