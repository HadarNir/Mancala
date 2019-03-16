package Game;

public class MancalaClientTest {
    public static void main(String[] args) {
        //main
        MancalaClient application;
        if (args.length == 0)
            application = new MancalaClient("127.0.0.1");
        else
            application = new MancalaClient(args[0]);
    }
}
