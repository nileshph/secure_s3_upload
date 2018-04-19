package com.amazon.s3;

import java.util.List;

import junit.framework.TestCase;

public class ListS3FilesTest extends TestCase {

	ListS3Files listS3Files;
	
	public ListS3FilesTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		listS3Files = new ListS3Files();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testListing()
	{
		List<String> list = listS3Files.getFilesList("secure-cloud-project");
		for(String s: list)
		{
			System.out.println(s);
		}
	}
}
