package com.amazon.encryption;
import java.io.File;

import com.amazon.encryption.AESFileDecryption;
import com.amazon.encryption.AESFileEncryption;

import junit.framework.TestCase;

public class AESFileDecryptionTest extends TestCase {
	
	AESFileDecryption aesfiledecryption;

	protected void setUp() throws Exception {
		super.setUp();
		byte[] hash = AESFileEncryption.hashPassword("jay");
		aesfiledecryption = new AESFileDecryption(hash , new File("Files\\test_file.enc"));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testS3Upload()
	{
		aesfiledecryption.decrypt();
	}

}