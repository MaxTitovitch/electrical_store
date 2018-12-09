package main;

import connection.ServerSocketConnector;
import view.ServerController;

public class Main {
    public static void main(String[] args) {
        //ServerSocketConnector connector = new ServerSocketConnector();
        //ServerSocketConnector.runServer();
        ServerController controller = new ServerController();
        controller.run();
    }
}
