import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESFileDecryption {

	byte[] hashedPassword = null;
	FileInputStream fileToDecrypt = null;

	public AESFileDecryption(byte[] hashedPassword, FileInputStream fileToDecrypt) {
		this.hashedPassword = hashedPassword;
		this.fileToDecrypt = fileToDecrypt;
	}

	public FileOutputStream decrypt() {
		FileOutputStream outFile = null;
		SecretKey secret = new SecretKeySpec(hashedPassword, "AES");
		try {
			FileInputStream ivFis = new FileInputStream("Files\\iv.enc");
			byte[] iv = new byte[16];
			ivFis.read(iv);
			ivFis.close();
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
			outFile = new FileOutputStream("Files\\plaintext_decrypted.txt");
			byte[] in = new byte[64];
			int read;
			while ((read = fileToDecrypt.read(in)) != -1) {
				byte[] output = cipher.update(in, 0, read);
				if (output != null)
					outFile.write(output);
			}

			byte[] output = cipher.doFinal();
			if (output != null)
				outFile.write(output);
			fileToDecrypt.close();
			outFile.flush();
			outFile.close();
			System.out.println("File Decrypted.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outFile;
	}
}
