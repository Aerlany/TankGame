import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerTwo extends Tank {

    public PlayerTwo(String img, int x, int y, GamePanel gamePanel,
                     String upImg, String downImg, String leftImg, String rightImg) {
        super(img, x, y, gamePanel, upImg, downImg, leftImg, rightImg);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            default:
                break;
        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
            case KeyEvent.VK_K:
                attack();
            default:
                break;
        }
    }
    public void move(){
        if (up) {
            upward();
        } else if (down) {
            downward();
        } else if (left) {
            leftward();
        } else if (right) {
            rightward();
        }
    }


    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img, x, y, null);
        move();
    }

    @Override
    public Rectangle gerRec() {
        return new Rectangle(x, y, width, height);
    }
}

