package jobsender;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
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
            Job job = new Job("Task_1", 5000); //ms
            outputStream.writeObject(job.getJobTime());
            System.out.println("Job sent to load balancer.");
            socket.close();
            
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
    
}
