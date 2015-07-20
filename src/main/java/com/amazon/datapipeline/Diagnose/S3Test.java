package com.amazon.datapipeline.Diagnose;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import junit.framework.TestCase;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3Test extends TestCase {
  
  String accessId = System.getProperty("accessId");
  String privateKey = System.getProperty("privateKey");
  AWSCredentials credentials = new BasicAWSCredentials(accessId, privateKey);
  AmazonS3 s3Client = new AmazonS3Client(credentials);
  Region usWest2 = Region.getRegion(Regions.US_WEST_2);
  String key = "datapipelineDiagnose-delete-me" + UUID.randomUUID();
  
  public void setup() {
    //s3Client.setRegion(usWest2);
  }
  
  @Test
  public void testReadAccessInS3() {    
    try {     
      List<Bucket> existingBuckets = s3Client.listBuckets();
      if (existingBuckets.size() == 0){
        System.out.println("\nNo buckets found)");
        return;
      } else {
        System.out.println("\nExisting buckets:");
        for (Bucket bucket : existingBuckets) {
          System.out.println(" - " + bucket.getName());
        }
      }
    } catch (Exception e) {
      fail("S3 list buckets failed : " + e.getMessage());
    }
  }
  
  @Test
  public void testUploadObjectInS3() throws IOException{
    try {
      List<Bucket> existingBuckets = s3Client.listBuckets();
      if (existingBuckets.size() == 0){
        fail("Test could not complete : No buckets found");
        return;
      }
      String randomClientBucket = existingBuckets.get(0).getName();
      System.out.println("\nUploading a new object (" + key + ") to S3 bucket(" + randomClientBucket + ")");
      s3Client.putObject(new PutObjectRequest(randomClientBucket, key, createSampleFile()));     
    } catch (Exception e) {
      fail("S3 create/delete object failed: " + e.getMessage());
    }
  }

  private static File createSampleFile() throws IOException {
    File file = File.createTempFile("aws-java-sdk-", ".txt");
    file.deleteOnExit();

    Writer writer = new OutputStreamWriter(new FileOutputStream(file));
    writer.write("abcdefghijklmnopqrstuvwxyz\n");
    writer.write("01234567890112345678901234\n");
    writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
    writer.write("01234567890112345678901234\n");
    writer.write("abcdefghijklmnopqrstuvwxyz\n");
    writer.close();

    return file;
  }
}
