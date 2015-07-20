package com.amazon.datapipeline.Diagnose;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import org.junit.Test;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

import junit.framework.TestCase;

public class DynamoDbTest extends TestCase {

  String accessId = System.getProperty("accessId");
  String privateKey = System.getProperty("privateKey");
  AWSCredentials credentials = new BasicAWSCredentials(accessId, privateKey);
  AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentials);
  DynamoDB dynamoDB = new DynamoDB(ddbClient);
  String tableName = "datapipelineDiagnose-delete-me" + UUID.randomUUID();

  @Test
  public void testReadAccessInDynamoDb() {
    try {
      TableCollection<ListTablesResult> tables = dynamoDB.listTables();
      Iterator<Table> iterator = tables.iterator();
      if (!iterator.hasNext()) {
        System.out.println("\nNo tables found");
        return;
      } else {
        System.out.println("\nListing existing table names:");
        while (iterator.hasNext()) {
          Table table = iterator.next();
          System.out.println("-" + table.getTableName());
        } 
      }
    } catch (Exception e) {
      fail("DyanomoDB list tables failed : " + e.getMessage());
    }
  }

  @Test
  public void testWriteAccesstoDynamoDb() {
    try {
      ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
      attributeDefinitions.add(new AttributeDefinition()
        .withAttributeName("Id")
        .withAttributeType("N"));
      
      ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
      keySchema.add(new KeySchemaElement()
        .withAttributeName("Id")
        .withKeyType(KeyType.HASH));
      
      CreateTableRequest request = new CreateTableRequest()
        .withTableName(tableName)
        .withKeySchema(keySchema)
        .withAttributeDefinitions(attributeDefinitions)
        .withProvisionedThroughput(new ProvisionedThroughput()
        .withReadCapacityUnits(5L)
        .withWriteCapacityUnits(6L));

      System.out.println("Issuing CreateTable request for " + tableName);
      Table table = dynamoDB.createTable(request);
      System.out.println("Waiting for " + tableName + " to be created...");
      table.waitForActive();
      System.out.println("Issuing DeleteTable request for " + tableName);
      table.delete();
      System.out.println("Waiting for " + tableName + " to be deleted...");
      table.waitForDelete();
    } catch (Exception e) {
      fail("DynamoDb create/delete table failed: " + e.getMessage());
    }
  }
}
