package com.amazon.datapipeline.Diagnose;

import java.util.UUID;

import org.junit.Test;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rds.AmazonRDSClient;
import com.amazonaws.services.rds.model.CreateDBInstanceRequest;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DeleteDBInstanceRequest;

import junit.framework.TestCase;

public class RDSTest extends TestCase{
	//AWSCredentials vaildCredentials = new BasicAWSCredentials("AKIAI2EBG2H5FBIVNUPA", "jCG/TTVbwiXyqRJ2cGEfY/Q5CpX9LudpESor0XEO");
	//AWSCredentials invaildCredentials = new BasicAWSCredentials("AKIAILV7E3A2SPLJ3GJQ", "erdSivlSOFRam9BsCIONIVVljAeISDYm5FQiJcbb");
	String accessId = System.getProperty("accessId");
	String privateKey = System.getProperty("privateKey");
	AWSCredentials credentials = new BasicAWSCredentials(accessId, privateKey);
	AmazonRDSClient rdsClient = new AmazonRDSClient(credentials);	
	String id = "DataPipelinediagnose" + UUID.randomUUID().toString();
	int storage = 5;
	String dbInstanceClass = "db.t2.micro";
	String engine = "mysql";
	String user = "DiagnoseTestUser";
	String pwd = "DiagnoseTestPwd";
	
	@Test
	public void testReadAccessInRDS(){
		try{
			rdsClient.describeDBInstances();
		} catch(Exception e){
			fail("Not able to preform DescribeDBInstance" + "\n" + "Detail: " + e.getMessage());
		}
	}
	
	@Test
	public void testCreateDBInRDS(){
		try{
			CreateDBInstanceRequest request = new CreateDBInstanceRequest(id,storage,dbInstanceClass,engine,user,pwd);
			DBInstance test_instance = rdsClient.createDBInstance(request);
			test_instance.setDBName("dataPipeline_diagnose_test_DB");
			deleteDBInstance();
		} catch(Exception e){
			fail("not able to create DB in RDS" +  "\n" + "Detail: " + e.getMessage());
		}
	}
	
	private void deleteDBInstance(){
		DeleteDBInstanceRequest request = new DeleteDBInstanceRequest(id);
		request.withSkipFinalSnapshot(true);
		rdsClient.deleteDBInstance(request);
	}
	
}
