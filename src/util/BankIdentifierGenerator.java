package util;

public class BankIdentifierGenerator {
	
	/* 
		 * Account Number Generator Method
		 * [Bank Code][Branch Code][Unique Client ID]
	*/
	public static String generateAccountNumber() {
	    String bankCode = "1234";
	    String branchCode = "5678";
	    int uniquePart = (int)(Math.random() * 1_000_00000); // up to 8 digits
	    return String.format("%s%s%08d", bankCode, branchCode, uniquePart);
	}
	
	/* 
		 * Rib Account Generator Method
		 * [Bank Code][Branch Code][Account Number][RIB Key] 
	 */
	public static String generateRIB() {
	    String bankCode = "12345";
	    String branchCode = "67890";
	    String accountNumber = String.format("%011d", (long)(Math.random() * 1_000_000_00000L)); // 11-digit
	    String ribKey = String.format("%02d", (int)(Math.random() * 100)); // 2-digit key
	    return String.format("%s%s%s%s", bankCode, branchCode, accountNumber, ribKey);
	}


}
