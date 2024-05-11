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
                int numberofJobs = captureInt("numOfJobs", pm);

                List<Job> jobs = generateJobs(numberofJobs, jobCounter);
                jobCounter += numberofJobs;
                try {
                    for (Job job : jobs) {
                        outputStream.writeUTF("JOB_SUBMISSION");
                        outputStream.writeObject(job);
                        pm.handlePrompt("jobDetails", job.getJobTime(), job.getJobName());
                    }
                } catch (IOException e) {
                    errorHandler(e, pm);
                }
                pm.handlePrompt("jobsSent", 0, null);

                continueFlag = captureContinueFlag("moreJobsQ", pm);

            } while (continueFlag.equals("y"));

            normalCompletion = true;
            outputStream.writeUTF("CLOSE_CONNECTION");
            outputStream.flush();

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
        int activePort = captureInt("port", pm);
        
        return new Config(activePort, activeHost);
    }
    
    private static int captureInt(String promptCode, PromptHandler pm) {
        int validInt = 0;
        boolean validInput = false;
        while (!validInput) {
            Scanner scanner = new Scanner(System.in); 
            pm.handlePrompt(promptCode, 0, null);
            if (scanner.hasNextInt()) {
                validInt = scanner.nextInt(); 
                validInput = true; 
            } else {
                pm.handlePrompt("invalidInt", 0, null);
                scanner.next(); 
            }
        }
        return validInt;
    }

    private static String captureContinueFlag(String promptCode, PromptHandler pm) {
        String continueFlag = "";
        boolean validInput = false;
        while (!validInput) {
            Scanner scanner = new Scanner(System.in); 
            pm.handlePrompt(promptCode, 0, null);
            continueFlag = scanner.nextLine().toLowerCase();
            if (continueFlag.equals("y") || continueFlag.equals("n")) {
                validInput = true; 
            } else {
                pm.handlePrompt("invalidFlag", 0, null);
            }
        }
        return continueFlag;
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
