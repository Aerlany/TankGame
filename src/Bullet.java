import java.awt.*;
import java.util.ArrayList;

public class Bullet extends GameObject {

    //尺寸
    int width = 15;
    int height = 15;
    //速度
    int speed = 7;
    //方向
    Direction direction;

    public Bullet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel);
        this.direction = direction;
    }

    public void upward() {
        y -= speed;
    }

    public void downward() {
        y += speed;
    }

    public void leftward() {
        x -= speed;
    }

    public void rightward() {
        x += speed;
    }

    public void go() {
        switch (direction) {
            case UP:
                upward();
                break;
            case DOWN:
                downward();
                break;
            case LEFT:
                leftward();
                break;
            case RIGHT:
                rightward();
                break;
        }
        this.moveToBorder();
        this.hitBase();
    }

    //敌人与玩家子弹碰撞
    //intersects 由于检测碰撞
    public void hitBot() {
        ArrayList<Bot> botArrayList = this.gamePanel.bots;
        for (Bot bot : botArrayList) {
            if (this.gerRec().intersects(bot.gerRec())) {
                this.gamePanel.blastList.add(new Blast("",bot.x-15,bot.y-15,this.gamePanel));
                this.gamePanel.bots.remove(bot);
                this.gamePanel.removeList.add(this);
                this.gamePanel.killed++;
                break;
            }
        }
    }

    //子弹与墙碰撞
    public void hitWall() {
        ArrayList<Wall> walls = this.gamePanel.wallList;
        for (Wall wall : walls) {
            if (this.gerRec().intersects(wall.gerRec())) {
                this.gamePanel.wallList.remove(wall);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }

    //子弹与基地碰撞
    public void hitBase() {
        ArrayList<Base> bases = this.gamePanel.baseList;
        for (Base base:bases) {
            if (this.gerRec().intersects(base.gerRec())) {
                this.gamePanel.blastList.add(new Blast("",base.x-15,base.y-15,this.gamePanel));
                this.gamePanel.baseList.remove(base);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }

    //判断子弹出界
    public void moveToBorder() {
        if (x < 0 || x + width > this.gamePanel.getWidth()) {
            this.gamePanel.removeList.add(this);
        } else if (y < 0 || y + height > this.gamePanel.getHeight()) {
            this.gamePanel.removeList.add(this);
        }
    }

    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img, x, y, null);
        this.go();
        this.hitBot();
        this.hitWall();
    }

    @Override
    public Rectangle gerRec() {
        return new Rectangle(x, y, width, height);
    }
}
