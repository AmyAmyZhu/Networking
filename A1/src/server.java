import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by amyzhu on 16/10/16.
 */
public class server {
    public static void main(String argv[]) throws Exception {
        int n_port;
        int req_code;

        // Error: wrong number of inputs
        if(argv.length != 1) {
            System.out.println("Error: Wrong number of inputs.");
            System.out.println("Number of parameters: 1");
            System.out.println("Parameter:");
            System.out.println("    $1: <req_code>");
            System.exit(0);
        } try {
            req_code = Integer.parseInt(argv[0]);
        } catch (Exception e) {
            System.out.println("Error: req_code should be an integer");
            System.exit(0);
        }

        // Read parameters from command line
        req_code = Integer.parseInt(argv[0]);

        // Create a TCP socket
        ServerSocket TCP_socket = new ServerSocket(0);
        // Get port from TCP socket
        n_port = TCP_socket.getLocalPort();
        System.out.println("SERVER_PORT=" + n_port);

        while (true) {
            // Connect client socket and TCP socket
            Socket client_socket = TCP_socket.accept();
            // Compare client received code and request code
            compareCode(req_code, client_socket);

            DataOutputStream outputStream = new DataOutputStream(client_socket.getOutputStream());
            DatagramSocket UDP_socket = new DatagramSocket(0);
            int random_port = UDP_socket.getLocalPort();
            outputStream.writeBytes(random_port + "\n");
            reverseString(UDP_socket);
            UDP_socket.close();
        }
    }

    private static void compareCode(int req_code, Socket client_socket) throws Exception {
        // Get code client received from a stream from client
        BufferedReader client_read = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
        int client_code = Integer.parseInt(client_read.readLine());

        // Error: code client received not equal to req_code
        if(req_code != client_code) {
            client_socket.close();
            System.out.println("Error: code client received not euqal to req_code");
            System.exit(0);
        }
    }

    private static void reverseString(DatagramSocket UDP_socket) throws Exception {
        byte[] receive_message = new byte[1024];
        byte[] send_message = new byte[1024];

        DatagramPacket receive_packet = new DatagramPacket(receive_message, receive_message.length);
        UDP_socket.receive(receive_packet);
        String client_string = new String(receive_packet.getData());
        InetAddress IPA_address = receive_packet.getAddress();
        int sb_port = receive_packet.getPort();
        String reverse_string = new StringBuilder(client_string).reverse().toString();
        send_message = reverse_string.getBytes();
        DatagramPacket send_packet = new DatagramPacket(send_message, send_message.length, IPA_address, sb_port);
        UDP_socket.send(send_packet);

    }
}
