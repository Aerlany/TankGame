import java.awt.*;
import java.util.ArrayList;

public class EnemyBullet extends Bullet{

    public EnemyBullet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel, direction);
    }


    //敌人子弹与玩家碰撞
    //intersects 由于检测碰撞
    public void hitPlayer(){
        ArrayList<Tank> players = this.gamePanel.playerList;
        for (Tank player :players){
            if (this.gerRec().intersects(player.gerRec())){
                this.gamePanel.blastList.add(new Blast("",player.x-15,player.y-15,this.gamePanel));
                this.gamePanel.playerList.remove(player);
                this.gamePanel.removeList.add(this);
                player.alive = false;
                break;
            }
        }
    }
    public void enemyHitWall(){
        ArrayList<Wall> walls =this.gamePanel.wallList;
        for (Wall wall :walls){
            if (this.gerRec().intersects(wall.gerRec())){
                this.gamePanel.wallList.remove(wall);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }

    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img, x, y, null);
        this.go();
        this.hitPlayer();
        this.enemyHitWall();
    }
    @Override
    public Rectangle gerRec() {
        return new Rectangle(x, y, width, height);
    }

}
