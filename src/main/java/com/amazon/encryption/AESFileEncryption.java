package com.amazon.encryption;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.amazon.s3.S3UploadUtil;

public class AESFileEncryption {

	byte[] hashedPassword = null;
	File file = null;

	public AESFileEncryption(byte[] hash, File file) {
		this.hashedPassword = hash;
		this.file = file;
	}

	public AESFileEncryption() {
	}

	public File encrypt() {
		FileOutputStream outFile = null;
		File encrypted = null;
		// byte[] hash = hashedPassword.getBytes(StandardCharsets.UTF_8);
		SecretKey secret = new SecretKeySpec(hashedPassword, "AES");
		try {
			FileInputStream fileToEncrypt = new FileInputStream(file);
			encrypted = new File("Files\\"+file.getName()+".enc");
			System.out.println(encrypted.getName());
			outFile = new FileOutputStream(encrypted);
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
		S3UploadUtil s3Upload = new S3UploadUtil();
		s3Upload.uploadFile(encrypted, "secure-cloud-project");
		return encrypted;
	}

	//Method to generate SHA512 hash from password
	public static byte[] hashPassword(String password) {
		//generate salt and store it somewhere along with hashed password, maybe in a file
		String salt = null;
		// SecureRandom secureRandom = new SecureRandom();
		// secureRandom.nextBytes(salt);
		int iterations = 65536;
		int keyLength = 256;
		salt = "123123123";
		System.out.println("Salt is: "+salt);
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterations, keyLength);
			SecretKey key = skf.generateSecret(spec);
			byte[] res = key.getEncoded();
			System.out.println("byte[] hash is: "+key.getEncoded()+" String hash is: "+key.getEncoded().toString());
			return key.getEncoded();

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}
}