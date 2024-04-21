package jobsender;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class JobSender {
    public static void main(String[] args) {
        
        Scanner hostCap = new Scanner(System.in);
        System.out.println("Enter your host number (e.g. 'localhost'): ");
        String activeHost = hostCap.nextLine();
        Scanner portCap = new Scanner(System.in);
        System.out.println("Enter port number: ");
        int activePort = portCap.nextInt();
        Config config = new Config(activePort, activeHost);
        
        try {
            Socket socket = new Socket(config.getHost(), config.getPort());
            System.out.println("Connected to load balancer.");
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            
            int jobCounter = 1;
            String continueFlag;
            
            do{
                Scanner jobsCap = new Scanner(System.in);
                System.out.println("Enter the number of jobs to be sent: ");
                int numberofJobs = jobsCap.nextInt();
                
                List<Job> jobs = generateJobs(numberofJobs, jobCounter);
                jobCounter += numberofJobs;
                
                for(Job job: jobs){
                    outputStream.writeObject(job);
                    System.out.println("Sent job: " + job.getJobName() + " with duration: " + job.getJobTime() + "ms");
                }
                    System.out.println("All jobs sent to the Load Balancer.");
                    
                    Scanner flagCap = new Scanner(System.in);
                    System.out.println("Do you want to send more jobs to the LB? (y/n)");
                    continueFlag = flagCap.nextLine().toLowerCase();
                        
            } while (continueFlag.equals("y"));

            socket.close();
            System.out.println("okay");
            
            while (true) {

            }
        } catch (IOException e) {
            System.out.println("Connection FAILED! Ensure load balancer is started!");
            
            //debug helper
            Scanner myObj = new Scanner(System.in);
            System.out.println("press 'y' to view stack: ");
            String userInput = myObj.nextLine();
            if("y".equals(userInput) ){
               e.printStackTrace(); 
            }
        }
    }
    
    private static List<Job> generateJobs(int numJobs, int startNum){
        List<Job> jobs = new ArrayList<>();
        Random random = new Random();
        for (int i = startNum; i < startNum + numJobs; i++){
            String name = "Job #" + i;
            int time = 1000 + random.nextInt(9000);
            jobs.add(new Job(name, time));
        }
        return jobs;
    }
    
}
