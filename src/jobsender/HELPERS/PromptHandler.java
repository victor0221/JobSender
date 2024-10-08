/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jobsender.HELPERS;

/**
 *
 * @author dashi
 */
public class PromptHandler {

    public PromptHandler() {

    }

    public void handlePrompt(String code, int optionalInt, String optionalString) {
        switch (code) {
            case "host":
                System.out.println("Enter your host number (e.g. 'localhost'): ");
                break;
            case "port":
                System.out.println("Enter port number: ");
                break;
            case "lbConnected":
                System.out.println("Connected to load balancer.");
                break;
            case "numOfJobs":
                System.out.println("Enter the number of jobs to be sent: ");
                break;
            case "jobDetails":
                System.out.println("Sent job: " + optionalString + " with duration " + optionalInt + "ms");
                break;
            case "jobsSent":
                System.out.println("All jobs sent to the Load Balancer.");
                break;
            case "moreJobsQ":
                System.out.println("Do you want to send more jobs to the LB? (y/n)");
                break;
            case "noMoreJobs":
                System.out.println("Okay, no more jobs to be sent.");
                break;
            case "connFailed":
                System.out.println("Connection FAILED! Ensure load balancer is started!");
                break;
            case "pressY":
                System.out.println("Press 'y' to view stack: ");
                break;
            case "generalErr":
                System.out.println("AN ERROR HAS OCCURED!");
                break;
            case "invalidInt":
                System.out.println("Invalid input. Please enter a valid integer.");
                break;
            case "invalidFlag":
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                break;
            case "invalidString":
                System.out.println("No input. Please enter something, this cannot be left blank.");
                break;
        }
    }

}
