
package barleysquest;


import com.golden.gamedev.object.Sprite;
import java.awt.image.BufferedImage;

/**
 * Class for managing elevators (game prop)
 * @author Kent
 */
public class Elevator extends Sprite {

    private double velocity;
    private double startPosX, startPosY;
    private boolean collidedPlayer;
    private Player player;

    public Elevator(BufferedImage images)
    {

        super(images);
        velocity = 0.012;
        startPosX = getX();
        startPosY = getY();

    }

    @Override
    public void update(long elapsedTime)
    {

        horizontalPatrol(velocity, elapsedTime);

        if(collidedPlayer)
        {
            //calculating the player to track the elevator
            player.setCanJump(true);
            player.setVerticalSpeed(0.0);
            player.stand(player.getDirection());
            player.setY((this.getY()-player.getHeight()));

            double diff = getX() - getOldX();
            player.setX((player.getX() + (2*diff)));

            collidedPlayer = false;
        }
       
        super.update(elapsedTime);
    }

    public void horizontalPatrol(double velocity, long elapsedTime)
    {
            setHorizontalSpeed(velocity * elapsedTime);        
    }

    public double getVelocity()
    {
        return velocity;
    }

    public void setVelocity(double velocity)
    {
        this.velocity = velocity;
    }


    public double getStartPosX()
    {
        return startPosX;
    }

    public void setStartPosX(double startPosX)
    {
        this.startPosX = startPosX;
    }

    public double getStartPosY()
    {
        return startPosY;
    }

    public void setStartPosY(double startPosY)
    {
        this.startPosY = startPosY;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public boolean isCollidedPlayer()
    {
        return collidedPlayer;
    }

    public void setCollidedPlayer(boolean collidedPlayer)
    {
        this.collidedPlayer = collidedPlayer;
    }






}
