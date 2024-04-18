package jobsender;
import java.io.IOException;
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
            
            // the block below will send data to our load balancer (EXAMPLE)
            OutputStream outputStream = socket.getOutputStream();
            String message = "EXAMPLE DATA";
            outputStream.write(message.getBytes());
            socket.close();
            
            while (true) {

            }
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
