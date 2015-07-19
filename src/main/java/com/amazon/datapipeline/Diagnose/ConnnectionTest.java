package com.amazon.datapipeline.Diagnose;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

public class ConnnectionTest extends TestCase{

    @Test
    public void testConnectionToUs_West_2(){
        try{
            List<String> commands = new ArrayList<String>();
            commands.add("/sbin/ping");
            commands.add("-c");
            commands.add("3");
            commands.add("datapipeline.us-west-2.amazonaws.com");
            ProcessBuilder pb = new ProcessBuilder(commands);
            Process process = pb.start();
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            Assert.assertEquals(stdError.readLine().length(),0);
        } catch(Exception e){
        	fail("not able to connect to us-west-2" + (e.getMessage() == null ? "" : "\n" + "Deatiled:" + e.getMessage()));
        }
    }

    @Test
    public void testConnectionToUs_East_1(){
        try{
            List<String> commands = new ArrayList<String>();
            commands.add("/sbin/ping");
            commands.add("-c");
            commands.add("3");
            commands.add("datapipeline.us-east-1.amazonaws.com");
            ProcessBuilder pb = new ProcessBuilder(commands);
            Process process = pb.start();
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            Assert.assertEquals(stdError.readLine().length(),0);
        } catch(Exception e){
        	fail("not able to connect to us-east-1" + "\n" + (e.getMessage() == null ? "" : "\n" + "Deatiled:" + e.getMessage()));
        }
    }


    @Test
    public void testConnectionToEu_West_1(){
        try{
            List<String> commands = new ArrayList<String>();
            commands.add("/sbin/ping");
            commands.add("-c");
            commands.add("3");
            commands.add("datapipeline.eu-west-1.amazonaws.com");
            ProcessBuilder pb = new ProcessBuilder(commands);
            Process process = pb.start();
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            Assert.assertEquals(stdError.readLine().length(),0);
        } catch(Exception e){
        	fail("not able to connect to eu-west-1" + "\n" + (e.getMessage() == null ? "" : "\n" + "Deatiled:" + e.getMessage()));
        }
    }

    @Test
    public void testConnectionToAp_SouthEast_2(){
        try{
            List<String> commands = new ArrayList<String>();
            commands.add("/sbin/ping");
            commands.add("-c");
            commands.add("3");
            commands.add("datapipeline.ap-southeast-2.amazonaws.com");
            ProcessBuilder pb = new ProcessBuilder(commands);
            Process process = pb.start();
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            Assert.assertEquals(stdError.readLine().length(),0);
        } catch(Exception e){
        	fail("not able to connect to ap-southeast-2" + "\n" + (e.getMessage() == null ? "" : "\n" + "Deatiled:" + e.getMessage()));
        }
    }

    @Test
    public void testConnectionToAp_NorthEast_1(){
        try{
            List<String> commands = new ArrayList<String>();
            commands.add("/sbin/ping");
            commands.add("-c");
            commands.add("3");
            commands.add("datapipeline.ap-northeast-1.amazonaws.com");
            ProcessBuilder pb = new ProcessBuilder(commands);
            Process process = pb.start();
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            Assert.assertEquals(stdError.readLine().length(),0);
        } catch(Exception e){
        	fail("not able to connect to ap-Northeast-1" + "\n" + (e.getMessage() == null ? "" : "\n" + "Deatiled:" + e.getMessage()));
        }
    }


}

