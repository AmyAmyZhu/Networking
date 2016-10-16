import java.net.ServerSocket;

/**
 * Created by amyzhu on 16/10/16.
 */
public class server {
    public static void main(String argv[]) throws Exception {
        int n_port;
        int req_code;

        // Error: wrong number of inputs
        if(argv.length != 1){
            System.out.println("Error: Wrong number of inputs.");
            System.out.println("Number of parameters: 1");
            System.out.println("Parameter:");
            System.out.println("    $1: <req_code>");
            System.exit(0);
        } try {
            req_code = Integer.parseInt(argv[0]);
        } catch (Exception e){
            System.out.println("Error: req_code should be an integer");
            System.exit(0);
        }

        // Read parameters from command line
        req_code = Integer.parseInt(argv[0]);

        // Create a TCP socket
        ServerSocket TCPSocket = new ServerSocket(0);
        // Get port from TCP socket
        n_port = TCPSocket.getLocalPort();
        System.out.println("SERVER_PORT=" + n_port);
    }
}
