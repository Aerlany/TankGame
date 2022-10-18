import java.awt.*;
import java.util.ArrayList;

public abstract class Tank extends GameObject {
    //尺寸
    public int width = 60;
    public int height = 60;
    //速度
    private final int speed = 3;
    public Direction direction = Direction.UP;
    //四个方向的图片
    private String upImg;
    private String leftImg;
    private String rightImg;
    private String downImg;
    //tank 移动
    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;

    //攻击冷却状态
    private boolean attackCoolDown = true;
    //攻击冷却间隔（单位为ms）
    private int attackCoolDownTime = 1000;
    //坦克存活状态
    public boolean alive = true;

    public Tank(String img, int x, int y, GamePanel gamePanel, String upImg, String downImg, String leftImg, String rightImg) {
        super(img, x, y, gamePanel);
        this.upImg = upImg;
        this.downImg = downImg;
        this.leftImg = leftImg;
        this.rightImg = rightImg;
    }


    public void leftward() {
        direction = Direction.LEFT;
        setImg(leftImg);
        if (hitWall(x - speed, y) && hitBorder(x - speed, y)) {
            this.x -= speed;
        }
    }

    public void rightward() {
        direction = Direction.RIGHT;
        setImg(rightImg);
        if (hitWall(x + speed, y) && hitBorder(x + speed, y)) {
            this.x += speed;
        }
    }

    public void upward() {
        direction = Direction.UP;
        setImg(upImg);
        if (hitWall(x, y - speed) && hitBorder(x, y - speed)) {
            this.y -= speed;
        }
    }

    public void downward() {
        direction = Direction.DOWN;
        setImg(downImg);
        if (hitWall(x, y + speed) && hitBorder(x, y + speed)) {
            this.y += speed;
        }
    }

    public void setImg(String img) {
        this.img = Toolkit.getDefaultToolkit().getImage(img);
    }

    //检测坦克和墙体碰撞
    public boolean hitWall(int x, int y) {
        //围墙列表
        ArrayList<Wall> walls = this.gamePanel.wallList;
        //坦克下一步的矩形
        Rectangle next = new Rectangle(x, y, width, height);
        //遍历列表
        for (Wall wall : walls) {
            if (next.intersects(wall.gerRec())) {
                return false;
            }
        }
        return true;
    }

    //检测坦克与边界碰撞
    public boolean hitBorder(int x, int y) {
        if (x < 0) {
            return false;
        } else if (x + width > this.gamePanel.getWidth()) {
            return false;
        } else if (y < 0) {
            return false;
        } else if (y + height > this.gamePanel.getHeight()) {
            return false;
        }
        return true;
    }

    //子弹
    public void attack() {
        if (attackCoolDown && alive) {
        Point p = this.getHeadPoint();
        Bullet bullet = new Bullet("img/enemymissile.gif", p.x, p.y, this.gamePanel, this.direction);
        this.gamePanel.bullets.add(bullet);

        //启动线程
            new AttackCd().start();
        }
    }

    //通过添加线程来控制子弹冷却时间
    class AttackCd extends Thread {
        public void run() {
            attackCoolDown = false;
            try {
                Thread.sleep(attackCoolDownTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            attackCoolDown = true;
            //终止线程
            this.stop();
        }
    }

    public Point getHeadPoint() {
        switch (direction) {
            case UP:
                return new Point(x + width / 2 - 6, y);
            case DOWN:
                return new Point(x + width / 2 - 6, y + height);
            case LEFT:
                return new Point(x, y + height / 2 - 6);
            case RIGHT:
                return new Point(x + width / 2 + 12, y + height / 2 - 6);
            default:
                return null;
        }
    }


    @Override
    public abstract void paintSelf(Graphics g);

    @Override
    public abstract Rectangle gerRec();
}
