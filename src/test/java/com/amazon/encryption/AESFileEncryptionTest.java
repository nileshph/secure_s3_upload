package com.amazon.encryption;
import java.io.File;

import com.amazon.encryption.AESFileEncryption;

import junit.framework.TestCase;

public class AESFileEncryptionTest extends TestCase {

	AESFileEncryption aesfileencryption;

	protected void setUp() throws Exception {
		super.setUp();
		byte[] hash = AESFileEncryption.hashPassword("jay");
		aesfileencryption = new AESFileEncryption(hash , new File("test_file"));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testS3Upload()
	{
		aesfileencryption.encrypt();
	}
}
