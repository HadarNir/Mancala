package Game;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class MancalaClientTest {
    public static void main(String[] args) {
        //main
        String mancalaHost = "127.0.0.1";
        Scanner input;
        int port;
        try // connect to server, get streams and start outputThread
        {
            // make connection to server
            Socket connection = new Socket(
                    InetAddress.getByName(mancalaHost), 12346);
            // get streams for input and output
            input = new Scanner(connection.getInputStream());
            if (input.hasNext()) {
                port = input.nextInt();
                input.nextLine();
                MancalaClient app = new MancalaClient("127.0.0.1", port);
            }
        } // end try
        catch (IOException ioException) {
            ioException.printStackTrace();
        } // end catch
    }
}
