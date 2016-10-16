/**
 * Created by amyzhu on 16/10/16.
 */
public class client {
    public static void main(String argv[]) throws Exception {
        String server_address;
        int n_port;
        String req_code;

        // Error: wrong number of inputs
        if(argv.length != 4){
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

    }
}
