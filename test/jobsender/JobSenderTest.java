/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package jobsender;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jobsender.JOB_TEMPLATE.Job;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author usula
 */
public class JobSenderTest {
    
    public JobSenderTest() {
    }

    @Test
    public void testGenerateJobs() {
        List<Job> jobs = generateJobs(5, 1);
        assertEquals(5, jobs.size());
    }
    
    @Test
    public void testPrompHandler() {
        var result = mockPromptHandler(10,0,null,null);
        assertEquals(10, result);
        var result2 = mockPromptHandler(0,10,null,null);
        assertEquals(10, result2);
        var result3 = mockPromptHandler(0,0,"1",null);
        assertEquals("1", result3);
        var result4 = mockPromptHandler(0,0,null,"1");
        assertEquals("1", result4);
    }
    
    @Test
    public void testConfigSetup() {
        String host = "localhost";
        int port = 12345;
        var result = mockPromptHandler(port,0,null,null);
        assertEquals(12345, result);
        var result2 = mockPromptHandler(0,0,host,null);
        assertEquals("localhost", result2);
    }
    @Test
    public void errorHandling() {
        String err = "InvalidInput";
        mockErrorHandler(err);
    }


    
    // mocks
    private static List<Job> generateJobs(int numJobs, int startNum) {
        List<Job> jobs = new ArrayList<>();
        Random random = new Random();
        for (int i = startNum; i < startNum + numJobs; i++) {
            String name = "Job #" + i;
            int time = 1000 + random.nextInt(9000);
            jobs.add(new Job(name, time));
        }
        return jobs;
    }
    
    
    private static Object mockErrorHandler(String err){
        mockPromptHandler(0, 0, err,null);
        Object userInput = "y";
        mockPromptHandler(0, 0, "press y",null);
        if ("y".equals(userInput)) {
            return err;
        }
        return "default";
    }
    
    private static Object mockPromptHandler(int int1, int int2, String string1, String string2) {
        if(int1 != 0){
            return int1;
        }
        if(int2 !=0){
            return int2;
        }
        if(string1 != null){
            return string1;
        }
        if(string2 != null){
            return string2;
        }
        return "no input";
    }

    
}
