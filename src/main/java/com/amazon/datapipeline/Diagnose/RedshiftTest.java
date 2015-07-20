package com.amazon.datapipeline.Diagnose;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.redshift.AmazonRedshiftClient;
import com.amazonaws.services.redshift.model.Cluster;

public class RedshiftTest extends TestCase {
  
  String accessId = System.getProperty("accessId");
  String privateKey = System.getProperty("privateKey");
  AWSCredentials credentials = new BasicAWSCredentials(accessId, privateKey);
  AmazonRedshiftClient redshiftClient = new AmazonRedshiftClient(credentials);
  
  @Test
  public void testReadAccessToRedshift() {
    try {
      List <Cluster> existingClusters = redshiftClient.describeClusters().getClusters();
      if (existingClusters.size() == 0){
        System.out.println("\nNo clusters found");
        return;
      } else {
        System.out.println("\nExisting clusters:");
        for (Cluster cluster : existingClusters) {
          System.out.println(" - " + cluster.getClusterIdentifier());
        }
      }    
    } catch (Exception e) {
      fail("Redshift describe clusters failed : " + e.getMessage());
    }    
  }
  
  @Ignore
  @Test
  public void testWriteAccessToRedshift() {
    try {
      
    } catch (Exception e) {
      fail("Redshift clusters failed : " + e.getMessage());
    }
  }
}
