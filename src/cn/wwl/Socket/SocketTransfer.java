package cn.wwl.Socket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName SocketTransfer
 * @Description Transfer message to CSGO
 * @Author wwlwwl
 * @Date 2021/1/2 10:23
 * @Version 1.0
 **/
public class SocketTransfer {

    public SocketTransfer() {
        this.socket = new Socket();
    }
    private static SocketTransfer instance;
    private Socket socket;
    private Thread inputThread;
    private BufferedOutputStream outputStream = null;

    public static SocketTransfer getInstance() {
        if (instance == null) {
            instance = new SocketTransfer();
        }
        return instance;
    }

    public void connect(int port) {
        try {
            socket.connect(new InetSocketAddress("127.0.0.1",port));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        if (!socket.isConnected() || socket.isClosed()) {
            System.out.println("Connect Socket Failed!");
            System.exit(0);
        }

        try {
            outputStream = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputThread = new Thread(new ConsoleOutRunnable(socket));
        inputThread.start();
        System.out.println("Socket Connected.");
    }

    public void sendData(String command) {
        if (!socket.isConnected()) {
            System.out.println("Not Connected!");
            System.exit(0);
        }
        if (outputStream == null) {
            System.out.println("Stream Not Inited!");
            System.exit(0);
        }
        //System.out.println("Send CMD:" + command);
        try {
            outputStream.write(command.getBytes(StandardCharsets.UTF_8));
            outputStream.write("\n".getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            socket.getOutputStream().flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
