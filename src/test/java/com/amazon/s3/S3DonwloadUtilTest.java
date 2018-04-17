package com.amazon.s3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;

public class S3DonwloadUtilTest extends TestCase {

	S3DownloadUtil s3DownloadUtil;
	
	public S3DonwloadUtilTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		s3DownloadUtil = new S3DownloadUtil();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testDownload()
	{
		String bucketName = "secure-cloud-project";
		String keyName = "test";
		
		try {
			File file = s3DownloadUtil.downloadFile(keyName, bucketName);
			assertEquals(keyName, file.getName());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
