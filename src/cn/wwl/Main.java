package cn.wwl;

import cn.wwl.GameControl.SkinCapture;
import cn.wwl.Socket.SocketTransfer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*
        if (args.length == 0) {
            System.out.println("Port Not Found!");
            System.exit(0);
        }
         */

        //int port = Integer.parseInt(args[0]);

        //-insecure -netconport 10090
        int port = 10090;
        SocketTransfer.getInstance().connect(port);
        /*
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        while (true) {
            String s = scanner.next();
            if (s.contains("exit")) {
                SocketTransfer.getInstance().disconnect();
                System.exit(0);
            }
            SocketTransfer.getInstance().sendData(s);
        }

         */
        SkinCapture.getInstance().start();
    }
}
