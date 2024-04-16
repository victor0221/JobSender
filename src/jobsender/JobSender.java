package jobsender;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class JobSender {
    public static void main(String[] args) {
        //since we dont actually need to use minix to spin up VM's this should do
        final String SERVER_IP = "localhost";
        final int SERVER_PORT = 42069; // our load balancer port :)
        
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to load balancer.");
            //still needs doing....
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
