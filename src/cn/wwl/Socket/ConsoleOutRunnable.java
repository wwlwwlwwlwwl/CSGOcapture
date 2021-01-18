package cn.wwl.Socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

/**
 * @ClassName ConsoleOutRunnable
 * @Description //TODO
 * @Author WangWeiLi
 * @Date 2021/1/2 10:26
 * @Version //TODO
 **/
public class ConsoleOutRunnable implements Runnable {

    private Socket socket;
    private Thread currentThread;
    public ConsoleOutRunnable(Socket socket) {
        this.socket = socket;
        this.currentThread = Thread.currentThread();
    }


    @Override
    public void run() {
        Thread.currentThread().setName("Input Stream Getter");
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            currentThread.interrupt();
        }

        if (inputStream == null) {
            System.out.println("InputStream == NULL!");
            currentThread.interrupt();
        }

        while (!currentThread.isInterrupted()) {
            try {
            if (inputStream.read() != -1) {
                String s = new String(inputStream.readAllBytes());
                System.out.println(s);
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
