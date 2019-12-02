package Utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Encrypt {

	public String encrypt(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(input.getBytes("utf8"));
			String result = String.format("%064x", new BigInteger(1, digest.digest()));
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}  
