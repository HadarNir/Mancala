package Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;

public class MancalaServerTest {
    public static void main(String[] args) {
        //main
        ServerSocket server;
        int CurPort = 12123;
        try {
            server = new ServerSocket(12346, 100); // set up ServerSocket
            while (true) {
                MancalaServer application = new MancalaServer(CurPort); //starting a server with the current port
                for (int i = 0; i < 2; i++) { // send the same port for two users trying to connect
                    try // wait for connection
                    {
                        Socket socket = server.accept(); // wait for a user to connect
                        try // obtain streams from Socket
                        {
                            Formatter output = new Formatter(socket.getOutputStream());
                            output.format("%d\n", CurPort); // send to the user the current port
                            output.flush();
                        } // end try
                        catch (IOException ioException) {
                            ioException.printStackTrace();
                            System.exit(1);
                        } // end catch
                        finally {
                            try {
                                socket.close(); // close connection to client
                            } // end try
                            catch (IOException ioException) {
                                ioException.printStackTrace();
                                System.exit(1);
                            } // end catch
                        } // end finally
                    } // end try
                    catch (IOException ioException) {
                        ioException.printStackTrace();
                        System.exit(1);
                    } // end catch
                } // end for
                application.execute(); // start the game after both users have connected
                CurPort++; //increase current port
            }
        } // end try
        catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(1);
        }
    }
}
