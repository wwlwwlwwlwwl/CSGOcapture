package cn.wwl.GameControl;

import cn.wwl.Socket.SocketTransfer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName SkinCapture
 * @Description //TODO
 * @Author WangWeiLi
 * @Date 2021/1/2 10:38
 * @Version //TODO
 **/
public class SkinCapture {

    public static SkinCapture instance;
    private static final int START_COUNT = 765;
    private static final int END_COUNT = 1000;
    private int skin = START_COUNT;
    public static SkinCapture getInstance() {
        if (instance == null) {
            instance = new SkinCapture();
        }
        return instance;
    }

    private int start_Sleep = 5;
    public void start() {
        //-insecure -netconport 10090 -preload -processheap +cl_forcepreload 1
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Robot Started. Delay 5s");
        SocketTransfer.getInstance().sendData("clear");
        SocketTransfer.getInstance().sendData("showconsole");
        SocketTransfer.getInstance().sendData("echo ***************************");
        SocketTransfer.getInstance().sendData("echo *** Robot Started in 5s ***");
        SocketTransfer.getInstance().sendData("echo ***************************");
        while (start_Sleep-- > 0) {
            String msg = String.format("Robot Started in %s s",start_Sleep);
            SocketTransfer.getInstance().sendData("echo " + msg);
            System.out.println(msg);
            sleep(1000);
        }

        System.out.println("Started...");
        SocketTransfer.getInstance().sendData("hideconsole");
        SocketTransfer.getInstance().sendData("sm_rcon sv_cheats 1");
        SocketTransfer.getInstance().sendData("sm_cvar host_timescale 3");
        while (skin <= END_COUNT) {
            //System.out.println("Start Round " + skin);
            SocketTransfer.getInstance().sendData("slot2");
            sleep(50);
            SocketTransfer.getInstance().sendData("say " + skin);
            sleep(50);
            SocketTransfer.getInstance().sendData("+lookatweapon");
            sleep(700);
            BufferedImage bufferedImage = robot.createScreenCapture(new Rectangle(0,0,1280,960));
            saveImage(bufferedImage);
            System.out.println("Capture File " + skin + " Done.");
            sleep(200);
            skin++;
        }
        SocketTransfer.getInstance().sendData("sm_cvar host_timescale 1");
        SocketTransfer.getInstance().sendData("clear");
        SocketTransfer.getInstance().sendData("showconsole");
        SocketTransfer.getInstance().sendData("echo *********************************");
        SocketTransfer.getInstance().sendData(String.format("echo *** Capture %s -> %s Done! ***",START_COUNT,END_COUNT));
        SocketTransfer.getInstance().sendData("echo *********************************");
        System.exit(0);
    }

    public void sleep(int ms) {
        try {
            Thread.currentThread().sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void clickKey(Robot robot,int keyCode) {
        robot.keyPress(keyCode);
        robot.delay(20);
        robot.keyRelease(keyCode);
    }

    public void delay(Robot robot,int delay) {
        robot.delay(delay);
    }

    private File dir = new File("./capture");
    private boolean checked;

    public void saveImage(BufferedImage image) {
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            if (!checked) {
                System.out.println("Dir Exists!");
                for (int i = 1; i < Integer.MAX_VALUE; i++) {
                    File tmpFile = new File("./capture-" + i);
                    if (!tmpFile.exists()) {
                        dir = tmpFile;
                        tmpFile.mkdir();
                        checked = true;
                        break;
                    }
                }
            }
        }

        File imageFile = new File(dir, skin + ".png");

        if (imageFile.exists()) {
            imageFile.delete();
        }

        try {
            ImageIO.write(image,"png",imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
