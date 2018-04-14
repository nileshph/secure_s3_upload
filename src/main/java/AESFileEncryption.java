import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESFileEncryption {

	byte[] hashedPassword = null;
	FileInputStream fileToEncrypt = null;

	AESFileEncryption(byte[] hashedPassword, FileInputStream filetoencrypt) {
		this.hashedPassword = hashedPassword;
		this.fileToEncrypt = filetoencrypt;
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
}
