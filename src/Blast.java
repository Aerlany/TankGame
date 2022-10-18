import java.awt.*;

public class Blast extends GameObject {
    public static Image[] imgs = new Image[5];
    int explodeCount = 0;

    static {
        for (int i = 0 ;i<5;i++){
            imgs[i] = Toolkit.getDefaultToolkit().getImage("img/blast"+(i)+".gif");        }

    }

    public Blast(String img, int x, int y, GamePanel gamePanel) {
        super(img, x, y, gamePanel);
    }

    @Override
    public void paintSelf(Graphics g) {
        if (explodeCount<5 ){
            g.drawImage(imgs[explodeCount],x,y,null);
            explodeCount++;
        }
    }

    @Override
    public Rectangle gerRec() {
        return null;
    }
}
