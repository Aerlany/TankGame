import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JFrame {

    //定义双缓存图片
    Image offScreenImage = null;
    //窗口大小
    int width = 925;//1920
    int height = 610;//1080
    //定义指针图片
    Image select = Toolkit.getDefaultToolkit().getImage("img/zzz.png");
    int y = 350;
    //开始音乐


    //游戏模式 1.单人模式 2.联机模式 3.游戏胜利 4.游戏失败 5.游戏暂停
    int state = 0;
    int a = 1;
    //重绘次数
    int count = 0;

    //游戏元素列表
    ArrayList<Bullet> bullets = new ArrayList<>();
    //创建敌人列表
    ArrayList<Bot> bots = new ArrayList<>();
    int enemyCount = 0;
    int killed = 0;
    //删除子弹列表
    ArrayList<Bullet> removeList = new ArrayList<>();
    //玩家列表
    ArrayList<Tank> playerList = new ArrayList<>();
    //围墙列表
    ArrayList<Wall> wallList = new ArrayList<>();
    //基地列表
    ArrayList<Base> baseList = new ArrayList<>();
    //爆炸元素
    ArrayList<Blast> blastList = new ArrayList<>();

    //定义玩家一
    PlayerOne playerOne = new PlayerOne("img/p1tankU.gif", 120, 540, this,
            "img/p1tankU.gif", "img/p1tankD.gif",
            "img/p1tankL.gif", "img/p1tankR.gif");
    //玩家二
    PlayerTwo playerTwo = new PlayerTwo("img/p1tankU.gif", 805, 540, this,
            "img/p1tankU.gif", "img/p1tankD.gif",
            "img/p1tankL.gif", "img/p1tankR.gif");

    public GamePanel() throws FileNotFoundException {
    }

    //窗口的启动方法
    public void launch() {
        //标题
        setTitle("坦克大战 version 1.0");
        //设置窗口大小
        setSize(width, height);
        //使窗口居中
        setLocationRelativeTo(null);
        //添加关闭事件
        setDefaultCloseOperation(3);
        //用户不能调整大小
        setResizable(true);
        //使窗口可见
        setVisible(true);
        //添加键盘监视器
        this.addKeyListener(new GamePanel.KeyMonitor());

        for (int i = 0; i < 15; i++) {
            wallList.add(new Wall("img/steels.gif", 61 * i, 200, this));
        }
        blastList.add(new Blast("",0,0,this));

        baseList.add(new Base("img/base2.gif", 427, 550, this));
        wallList.add(new Wall("img/wall2.gif", 427, 489, this));//
        wallList.add(new Wall("img/wall2.gif", 366, 550, this));
        wallList.add(new Wall("img/wall2.gif", 366, 489, this));
        wallList.add(new Wall("img/wall2.gif", 488, 550, this));
        wallList.add(new Wall("img/wall2.gif", 488, 489, this));

        //重绘
        while (true) {

            //批量添加敌人
            if ((state == 1 || state == 2) && count % 100 == 1 && enemyCount < 10) {
                Random random = new Random();
                int rum = random.nextInt(800);
                bots.add(new Bot("img/enemy1U.gif", rum, 100, this,
                        "img/enemy1U.gif", "img/enemy1D.gif",
                        "img/enemy1L.gif", "img/enemy1R.gif"));
                enemyCount++;
            }

            repaint();
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //游戏胜利判定
            if (bots.size() == 0 && enemyCount == 10) {
                state = 3;
            }
            //游戏失败判定
            else if ((state == 1 || state == 2) && playerList.size() == 0 || baseList.size() == 0) {
                state = 4;
            }
        }

    }

    //paint（）方法
    @Override
    public void paint(Graphics g) {
//        System.out.println(bullets.size());

        //创建和容器大小一样的图片
        if (offScreenImage == null) {
            offScreenImage = this.createImage(width, height);
        }
        //获取图片画笔
        Graphics gImage = offScreenImage.getGraphics();
        //设置画笔颜色
        gImage.setColor(Color.BLACK);
        //绘制实心矩形
        gImage.fillRect(0, 0, width, height);
        //改变画笔颜色
        gImage.setColor(Color.WHITE);
        //改变文字的样式和大小
        gImage.setFont(new Font("楷体", Font.BOLD, 30));
        //添加文字
        if (state == 0) {

//            gImage.drawString("选择游戏模式", 300, 300);
//            gImage.drawString("单人模式", 330, 350);
//            gImage.drawString("联机模式", 330, 400);
            Image be = Toolkit.getDefaultToolkit().getImage("img/begin.gif");
            gImage.drawImage(be,206,100,this);
            //绘制指针
            gImage.drawImage(select, 340, y, null);
        } else if (state == 1 || state == 2) {
//            gImage.drawString("游戏开始", width/2-70, height/2);
            if (state == 1) {
                gImage.drawString("消灭敌人:"+killed, 10, 60);
            } else if (state == 2) {
                gImage.drawString("消灭敌人:"+killed, 10, 60);
            }
            //绘制玩家1
            for (Tank player : playerList) {
                player.paintSelf(gImage);
            }

            //绘制子弹
            for (Bullet bullet : bullets) {
                bullet.paintSelf(gImage);
            }
            bullets.removeAll(removeList);//消除子弹
            //生成敌人
            for (Bot bot : bots) {
                bot.paintSelf(gImage);
            }
            //绘制墙
            for (Wall wall : wallList) {
                wall.paintSelf(gImage);
            }
            //绘制基地
            for (Base base : baseList) {
                base.paintSelf(gImage);
            }
            //绘制爆炸
            for (Blast blast :blastList){
                blast.paintSelf(gImage);
            }

            //重绘一次
            count++;
        } else if (state == 3) {
            gImage.drawString("游戏胜利", width / 2 - 70, height / 2);
        } else if (state == 4) {
//            gImage.drawString("游戏失败", width / 2 - 70, height / 2);
            Image gameover = Toolkit.getDefaultToolkit().getImage("img/gameover.png");
            gImage.drawImage(gameover,222,200,this);
        } else if (state == 5) {
            gImage.drawString("游戏暂停", width / 2 - 70, height / 2);
        }
        //将缓存区图形绘制到容器中
        g.drawImage(offScreenImage, 0, 0, null);

    }

    //键盘监视器
    class KeyMonitor extends KeyAdapter {
        //按下键盘
        @Override
        public void keyPressed(KeyEvent e) {
            //返回键值
//            System.out.println(e.getKeyChar());
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_1:
                    a = 1;
                    y = 350;
                    break;
                case KeyEvent.VK_2:
                    a = 2;
                    y = 380;
                    break;
                case KeyEvent.VK_ENTER:
                    state = a;
                    playerList.add(playerOne);
                    if (state == 2){
                        playerList.add(playerTwo);
                    }
                    break;
                case KeyEvent.VK_P:
                    if (state != 5) {
                        a = state;
                        state = 5;
                    } else {
                        state = a;
                    }

                default:
                    playerOne.keyPressed(e);
                    playerTwo.keyPressed(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            playerOne.keyReleased(e);
            playerTwo.keyReleased(e);
        }
    }

    //main
    public static void main(String[] args) throws FileNotFoundException {
        GamePanel go = new GamePanel();
        go.launch();
    }
}
