package com.amazon.s3;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class ListS3Files extends S3 {

	public List<String> getFilesList(String bucketName)
	{
		ArrayList<String> filesList = new ArrayList<String>();
		@SuppressWarnings("deprecation")
		AmazonS3 s3Client = new AmazonS3Client(this.getCredentials());
		try {
			System.out.println("Listing objects");
			final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withMaxKeys(2);
			ListObjectsV2Result result;
			do {               
				result = s3Client.listObjectsV2(req);

				for (S3ObjectSummary objectSummary : 
					result.getObjectSummaries()) {
					filesList.add(objectSummary.getKey());
				}
				//System.out.println("Next Continuation Token : " + result.getNextContinuationToken());
				req.setContinuationToken(result.getNextContinuationToken());
			} while(result.isTruncated() == true ); 

		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, " +
					"which means your request made it " +
					"to Amazon S3, but was rejected with an error response " +
					"for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, " +
					"which means the client encountered " +
					"an internal error while trying to communicate" +
					" with S3, " +
					"such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}

		return filesList;
	}

}
