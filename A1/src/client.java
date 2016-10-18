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

        Socket TCP_socket = new Socket(server_address, n_port);
        DataOutputStream outputStream = new DataOutputStream(TCP_socket.getOutputStream());
        outputStream.writeBytes(req_code + "\n");

        BufferedReader from_server = new BufferedReader(new InputStreamReader(TCP_socket.getInputStream()));
        int random_port = Integer.parseInt(from_server.readLine());
        System.out.println("Random Port: " + random_port);
        TCP_socket.close();

        DatagramSocket UDP_socket = new DatagramSocket();
        sendMessage(random_port, argv, server_address, UDP_socket);
        UDP_socket.close();

    }

    private static void sendMessage(int random_port, String argv[], String server_address, DatagramSocket UDP_socket) throws Exception {
        byte[] receive_message = new byte[1024];
        byte[] send_message = new byte[1024];

        String reverse_string = argv[3];
        send_message = reverse_string.getBytes();
        InetAddress ip = InetAddress.getByName(server_address);
        DatagramPacket send_packet = new DatagramPacket(send_message, send_message.length, ip, random_port);
        UDP_socket.send(send_packet);
        DatagramPacket receive_packet = new DatagramPacket(receive_message, receive_message.length);
        UDP_socket.receive(receive_packet);
        String changed_string = new String(receive_packet.getData());
        System.out.println("Reversed String: " + changed_string);

    }
}
