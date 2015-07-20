package com.amazon.datapipeline.Diagnose;

import java.io.File;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.AssertionFailedError;
import junit.framework.TestSuite;
import junit.textui.ResultPrinter;
import junit.textui.TestRunner;

import com.amazon.datapipeline.Diagnose.ConnnectionTest;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Main {
    public static void main(String[] args) {
    	Main testRunner = new Main();
    	if(args.length != 2 && args[0].equals("--config") && args[1].equals("--config")){
    		System.err.println("Command: java -jar Diagnose.jar --config ~/credential.json");
    		System.exit(255);
    	}
    	else{
    		String filePath = args[0].equals("--config") ? args[1] : args[0];
    		testRunner.setCredential(filePath);
    	}
    	testRunner.runTest();
    }
    
    public void runTest(){
        TestRunner testRunner = new TestRunner();
        testRunner.setPrinter(new ResultPrinter(System.out) {
            boolean isfailed = false;

            public void startTest(Test test) {
                getWriter().print("running test: " + test + "...");
                isfailed = false;
            }

            public void addFailure(Test test, AssertionFailedError t) {
                getWriter().println("Failed");
                isfailed = true;
            }

            public void endTest(Test test) {
                if (!isfailed) {
                    getWriter().println("Success");
                }
            }
        });
      testRunner.doRun(new TestSuite(ConnnectionTest.class));
      testRunner.doRun(new TestSuite(S3Test.class));      
      testRunner.doRun(new TestSuite(RDSTest.class)); 
      testRunner.doRun(new TestSuite(DynamoDbTest.class));
      testRunner.doRun(new TestSuite(RedshiftTest.class));
    }
    
    private void setCredential(String filePath){
    	JSONObject jsonObject = null;
    	try {
    		 String contents = Files.toString(new File(filePath), Charsets.UTF_8);
    		 jsonObject = new JSONObject(contents);
		} catch (IOException e) {
			System.err.println(filePath + " does not exist" + "\n" + "Detail: " + e.getMessage());
		} catch(JSONException e){
			System.err.println(filePath + " is not json-format" + "\n" + "Detail: " + e.getMessage());
		}
    	
    	try {
			String privateKey = jsonObject.get("private-key").toString();
			System.setProperty("privateKey", privateKey);
		} catch (JSONException e) {
			System.err.println("can not find object [private-key] in " + filePath);
			System.exit(255);
		}
    	
    	try {
			String accessId = jsonObject.get("access-id").toString();
			System.setProperty("accessId", accessId);
		} catch (JSONException e) {
			System.err.println("can not find object [access-id] in " + filePath);
			System.exit(255);
		}
    	
    }
}
