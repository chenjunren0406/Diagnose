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
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.redshift.AmazonRedshiftClient;
import com.amazonaws.services.redshift.model.Cluster;
import com.google.common.base.Throwables;

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

  @Test
  public void testWriteAccessToRedshift() {
  /*  Connection conn = null;
    Statement stmt = null;
    
    String dbURL = ""; 
    String MasterUsername = "";
    String MasterUserPassword = "";
    
    try{
       Class.forName("com.amazon.redshift.jdbc41.Driver");
       String driverClass = "com.amazon.redshift.jdbc41.Driver";
       registerDriver(driverClass, "s3://redshift-downloads/drivers/RedshiftJDBC41-1.1.2.0002.jar");

       System.out.println("Connecting to database...");
       Properties props = new Properties();

       //Uncomment the following line if using a keystore.
       //props.setProperty("ssl", "true");  
       props.setProperty("user", MasterUsername);
       props.setProperty("password", MasterUserPassword);
       conn = DriverManager.getConnection(dbURL, props);
    
       System.out.println("Listing system tables...");
       stmt = conn.createStatement();
       String sql;
       sql = "select * from information_schema.tables;";
       ResultSet rs = stmt.executeQuery(sql);       
       while(rs.next()){
          String catalog = rs.getString("table_catalog");
          String name = rs.getString("table_name");
          System.out.print("Catalog: " + catalog);
          System.out.println(", Name: " + name);
       }
       rs.close();
       stmt.close();
       conn.close();
    }catch(Exception e){
      e.printStackTrace();
      fail("Redshift acess failed : " + e.getMessage());
    }finally{
       //Finally block to close resources.
       try{
          if(stmt!=null)
             stmt.close();
       }catch(Exception ex){
       }// nothing we can do
       try{
          if(conn!=null)
             conn.close();
       }catch(Exception e){
          fail("Redshift create table failed : " + e.getMessage());
       }
    }  */
  }
  
  private void registerDriver(String driverClass, String driverJarUri) {
    try {
      URL driverUrl = new URL(driverJarUri);;     
      URL[] classLoaderUrls = new URL[]{driverUrl};
      URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);
      Driver driver = (Driver)Class.forName(driverClass, true, urlClassLoader).newInstance();         
      DriverManager.registerDriver(driver);
    }
    catch(Exception e) {
      Throwables.propagate(e);      
    }    
  }  
}
