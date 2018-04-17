package com.amazon.s3;

import java.io.File;

import junit.framework.TestCase;

public class S3UploadUtilTest extends TestCase {

	S3UploadUtil s3UploadUtil;
	
	public S3UploadUtilTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		s3UploadUtil = new S3UploadUtil();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testS3Upload()
	{
		s3UploadUtil.uploadFile(new File("test_file"), "secure-cloud-project");
	}

}
