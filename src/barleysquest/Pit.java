
package barleysquest;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.Timer;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kent
 */
public class Pit extends Sprite {

    private boolean collidedPlayer;
    private Timer timer = new Timer(200);
    Player player;
    boolean  touched = false;

    public Pit(BufferedImage image)
    {
        super(image);
        collidedPlayer = false;
    }

    @Override
    public void update(long elapsedTime)
    {
        if (collidedPlayer)
        {
            if(!touched)
                player.addHealth(-3);
            
            touched = true;
            if (timer.action(elapsedTime))
            {
                player.addHealth(-3);
                
                if(player.getHealth()<0)
                    player.setHealth(0);
            }
            collidedPlayer = false;
        }else
        {
            touched = false;
            
        }
        super.update(elapsedTime);
    }

    public boolean isCollidedPlayer()
    {
        return collidedPlayer;
    }

    public void setCollidedPlayer(boolean collidedPlayer)
    {
        this.collidedPlayer = collidedPlayer;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }
}
