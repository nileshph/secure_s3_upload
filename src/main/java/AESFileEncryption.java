import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESFileEncryption {

	byte[] hashedPassword = null;
	FileInputStream fileToEncrypt = null;

	AESFileEncryption(byte[] hashedPassword, FileInputStream filetoencrypt) {
		this.hashedPassword = hashedPassword;
		this.fileToEncrypt = filetoencrypt;
	}

	public AESFileEncryption() {
	}

	public FileOutputStream encrypt() {
		FileOutputStream outFile = null;
		SecretKey secret = new SecretKeySpec(hashedPassword, "AES");
		try {
			outFile = new FileOutputStream("Files\\encryptedFile.aes");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secret);
			AlgorithmParameters params = cipher.getParameters();

			FileOutputStream ivOutFile = new FileOutputStream("Files\\iv.enc");
			byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
			ivOutFile.write(iv);
			ivOutFile.close();

			// file encryption
			byte[] input = new byte[64];
			int bytesRead;

			while ((bytesRead = fileToEncrypt.read(input)) != -1) {
				byte[] output = cipher.update(input, 0, bytesRead);
				if (output != null)
					outFile.write(output);
			}

			byte[] output = cipher.doFinal();
			if (output != null)
				outFile.write(output);

			fileToEncrypt.close();
			outFile.flush();
			outFile.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return outFile;
	}

	//Method to generate SHA512 hash from password
	public static byte[] hashPassword(String password) {
		//generate salt and store it somewhere along with hashed password, maybe in a file
		byte[] salt = new byte[8];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
		int iterations = 65536;
		int keyLength = 256;
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
			SecretKey key = skf.generateSecret(spec);
			byte[] res = key.getEncoded();
			return res;

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}
}
