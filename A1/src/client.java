import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by amyzhu on 16/10/16.
 */
public class client {
    public static void main(String argv[]) throws Exception {
        String server_address;
        int n_port;
        String req_code;

        // Error: wrong number of inputs
        if(argv.length != 4) {
            System.out.println("Error: Wrong number of inputs.");
            System.out.println("Number of parameters: 4");
            System.out.println("Parameter:");
            System.out.println("    $1: <server_address>");
            System.out.println("    $2: <n_port>");
            System.out.println("    $3: <req_code>");
            System.out.println("    $4: message");
            System.exit(0);
        } try {
            n_port = Integer.parseInt(argv[1]);
        } catch (Exception e) {
            System.out.println("Error: n_port should be an integer");
            System.exit(0);
        }

        // Read parameters from command line
        server_address = argv[0];
        n_port = Integer.parseInt(argv[1]);
        req_code = argv[2];

        // Connect server address and n_port with TCP socket and send req_code to server
        Socket TCP_socket = new Socket(server_address, n_port);
        DataOutputStream outputStream = new DataOutputStream(TCP_socket.getOutputStream());
        outputStream.writeBytes(req_code + "\n");

        /* Instruction from TCPclient.java */
        // Get random port(r_port) from server by TCP socket
        BufferedReader from_server = new BufferedReader(new InputStreamReader(TCP_socket.getInputStream()));
        int r_port = Integer.parseInt(from_server.readLine());
        TCP_socket.close();

        // Use UDP socket to send message to client
        DatagramSocket UDP_socket = new DatagramSocket();
        sendMessage(r_port, argv, server_address, UDP_socket);
        UDP_socket.close();

    }

    private static void sendMessage(int r_port, String argv[], String server_address, DatagramSocket UDP_socket) throws Exception {
        // Receive and send messages to server
        byte[] receive_message = new byte[1024];
        byte[] send_message = new byte[1024];

        // Get string want to be reversed from the main parameter $4 <message>
        String reverse_string = argv[3];
        // Get message
        send_message = reverse_string.getBytes();
        // Get IPA address
        InetAddress IPA_address = InetAddress.getByName(server_address);
        
        // Create packet to send message and receive reversed string
        DatagramPacket send_packet = new DatagramPacket(send_message, send_message.length, IPA_address, r_port);
        UDP_socket.send(send_packet);
        DatagramPacket receive_packet = new DatagramPacket(receive_message, receive_message.length);
        UDP_socket.receive(receive_packet);
        
        // Print reversed string
        String changed_string = new String(receive_packet.getData());
        System.out.println(changed_string);

    }
}
