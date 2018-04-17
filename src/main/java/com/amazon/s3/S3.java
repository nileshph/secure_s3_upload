package com.amazon.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

public class S3 {

	private AWSCredentials credentials;

	public AWSCredentials getCredentials() {
		return credentials;
	}

	public void setCredentials(AWSCredentials credentials) {
		this.credentials = credentials;
	}

	public S3() {
		super();
		this.credentials = new BasicAWSCredentials(
				"AKIAIWGDTEDC62T2PTQA", 
				"nceHgUrJHpNE4iN4k4/v1+JY7+8lmyQlJ4X44Y13"
				);
	}


}
