/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jobsender;

import jobsender.JOB_TEMPLATE.Job;
import jobsender.HELPERS.PromptHandler;
import jobsender.CONFIG.Config;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author usula
 */
public class JobSender {
    
    public JobSender(){
        
    }
    
    public void runJobSender(){
         PromptHandler pm = new PromptHandler();
        Config config = configDataCapture(pm);
        Socket socket = null;
        Boolean normalCompletion = false;

        try {
            socket = new Socket(config.getHost(), config.getPort());
            pm.handlePrompt("lbConnected", 0, null);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            int jobCounter = 1;
            String continueFlag;

            do {
                Scanner jobsCap = new Scanner(System.in);
                pm.handlePrompt("numOfJobs", 0, null);
                int numberofJobs = jobsCap.nextInt();

                List<Job> jobs = generateJobs(numberofJobs, jobCounter);
                jobCounter += numberofJobs;
                try {
                    for (Job job : jobs) {
                        outputStream.writeObject(job);
                        pm.handlePrompt("jobDetails", job.getJobTime(), job.getJobName());
                    }
                } catch (IOException e) {
                    errorHandler(e, pm);
                }
                pm.handlePrompt("jobsSent", 0, null);

                Scanner flagCap = new Scanner(System.in);
                pm.handlePrompt("moreJobsQ", 0, null);
                continueFlag = flagCap.nextLine().toLowerCase();

            } while (continueFlag.equals("y"));

            normalCompletion = true;

        } catch (IOException e) {
            pm.handlePrompt("connFailed", 0, null);
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                errorHandler(e, pm);
            }
            if (normalCompletion) {
                pm.handlePrompt("noMoreJobs", 0, null);
            }
        }
    }
    
    //helper functions
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

    private static Config configDataCapture(PromptHandler pm) {
        Scanner hostCap = new Scanner(System.in);
        pm.handlePrompt("host", 0, null);
        String activeHost = hostCap.nextLine();

        Scanner portCap = new Scanner(System.in);
        pm.handlePrompt("port", 0, null);
        int activePort = portCap.nextInt();

        return new Config(activePort, activeHost);
    }

    private static void errorHandler(IOException e, PromptHandler pm) {
        pm.handlePrompt("generalErr", 0, null);
        Scanner myObj = new Scanner(System.in);
        pm.handlePrompt("pressY", 0, null);
        String userInput = myObj.nextLine();
        if ("y".equals(userInput)) {
            e.printStackTrace();
        }
    }
}
