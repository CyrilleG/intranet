package utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class BlowFish {
	public static String encryptBlowfish(String to_encrypt, String strkey) {
		  try {
		    SecretKeySpec key = new SecretKeySpec(strkey.getBytes(), "Blowfish");
		     Cipher cipher = Cipher.getInstance("Blowfish");
		     cipher.init(Cipher.ENCRYPT_MODE, key);
		     return new String(cipher.doFinal(to_encrypt.getBytes()));
		  } catch (Exception e) { return null; }
		}
		 
		public static String decryptBlowfish(String to_decrypt, String strkey) {
		  try {
		     SecretKeySpec key = new SecretKeySpec(strkey.getBytes(), "Blowfish");
		     Cipher cipher = Cipher.getInstance("Blowfish");
		     cipher.init(Cipher.DECRYPT_MODE, key);
		     byte[] decrypted = cipher.doFinal(to_decrypt.getBytes());
		     return new String(decrypted);
		  } catch (Exception e) { return null; }
		}
}
