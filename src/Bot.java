import java.awt.*;
import java.util.Random;

public class Bot extends Tank {
    private int moveCount = 0;

    public Bot(String img, int x, int y, GamePanel gamePanel, String upImg, String downImg, String leftImg, String rightImg) {
        super(img, x, y, gamePanel, upImg, downImg, leftImg, rightImg);
    }

    public Direction getRandomDirection() {
        Random random = new Random();
        int randomNum = random.nextInt(4);
        switch (randomNum) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.RIGHT;
            default:
                return null;
        }
    }

    public void go() {
        attack();
        if (moveCount >= 20) {
            direction = getRandomDirection();
            moveCount = 0;
        } else {
            moveCount++;
        }
        switch (direction) {
            case UP -> upward();
            case DOWN -> downward();
            case LEFT -> leftward();
            case RIGHT -> rightward();
        }
    }

    public void attack() {
        Point p = getHeadPoint();
        Random random = new Random();
        int a = random.nextInt(600);
        if (a <= 5) {
            this.gamePanel.bullets.add(new EnemyBullet("img/enemymissile.gif", p.x, p.y, this.gamePanel, this.direction));
        }
    }

    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img, x, y, null);
        go();
    }

    @Override
    public Rectangle gerRec() {
        return new Rectangle(x, y, width, height);
    }
}
