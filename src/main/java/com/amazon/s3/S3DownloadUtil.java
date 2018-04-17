package com.amazon.s3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;

public class S3DownloadUtil extends S3 {

	public File uploadFile(String fileName, String bucketName) throws FileNotFoundException, IOException
	{
		@SuppressWarnings("deprecation")
		AmazonS3 s3Client = new AmazonS3Client(this.getCredentials());
		try {
			System.out.println("Downloading an object");
			com.amazonaws.services.s3.model.S3Object s3object = s3Client.getObject(new GetObjectRequest(
					bucketName, fileName));
			System.out.println("Content-Type: "  + 
					s3object.getObjectMetadata().getContentType());

			File downloadedFile = new File(s3object.getKey());
			IOUtils.copy(s3object.getObjectContent(),new FileOutputStream(downloadedFile));
			return downloadedFile;

		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which" +
					" means your request made it " +
					"to Amazon S3, but was rejected with an error response" +
					" for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, which means"+
					" the client encountered " +
					"an internal error while trying to " +
					"communicate with S3, " +
					"such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}

		return null;	
	}

}
