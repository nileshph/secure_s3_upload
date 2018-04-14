package Secure_Cloud.Secure_File_Upload;

import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class Upload_File {

	public static void main(String[] args)
	{
		String bucket_name = "secure-cloud-project";
		String key_name = "test.txt";
		String file_name = key_name;
		
		AWSCredentials credentials = new BasicAWSCredentials(
				"AKIAIWGDTEDC62T2PTQA", 
				"nceHgUrJHpNE4iN4k4/v1+JY7+8lmyQlJ4X44Y13"
				);

		@SuppressWarnings("deprecation")
		AmazonS3 s3client = new AmazonS3Client(credentials);	
		
		try {
			System.out.println("Uploading a new object to S3 from a file\n");
			File file = new File(file_name);
			s3client.putObject(new PutObjectRequest(
					bucket_name, key_name, file));

		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which " +
					"means your request made it " +
					"to Amazon S3, but was rejected with an error response" +
					" for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, which " +
					"means the client encountered " +
					"an internal error while trying to " +
					"communicate with S3, " +
					"such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}

	}

}
