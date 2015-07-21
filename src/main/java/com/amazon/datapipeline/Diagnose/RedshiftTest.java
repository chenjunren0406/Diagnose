package com.amazon.datapipeline.Diagnose;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

import org.junit.Test;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.redshift.AmazonRedshiftClient;
import com.amazonaws.services.redshift.model.Cluster;
import com.google.common.base.Throwables;

public class RedshiftTest extends TestCase {
  
  AWSCredentialsProvider credentials = new DefaultAWSCredentialsProviderChain();
  AmazonRedshiftClient redshiftClient = new AmazonRedshiftClient(credentials);
  
  @Test
  public void testReadAccessToRedshift() {
    try {
      List <Cluster> existingClusters = redshiftClient.describeClusters().getClusters();
      if (existingClusters.size() == 0){
        //System.out.println("\nNo clusters found");
        return;
      } else {
        //System.out.println("\nExisting clusters:");
        for (Cluster cluster : existingClusters) {
          //System.out.println(" - " + cluster.getClusterIdentifier());
        }
      }    
    } catch (Exception e) {
      fail("Redshift describe clusters failed : " + e.getMessage());
    }    
  }  
}
