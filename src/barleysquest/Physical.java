
package barleysquest;

import com.golden.gamedev.object.AnimatedSprite;
import java.awt.image.BufferedImage;

/**
 * All physical objects which will be affected by gravity will extend from this
 * class.
 * The direction attribute keep track of every objects faced direction.
 * @author Kent
 */
public class Physical extends AnimatedSprite {

    protected String direction;
    protected boolean staticDefense;

    public Physical(BufferedImage[] image)
    {
        super(image);
    }

    @Override
    public void update(long elapsedTime)
    {

     //   if(!staticDefense)
        addGravity(elapsedTime, Globals.g);
        super.update(elapsedTime);

    }

    public void addGravity(long elapsedTime, double a)
    {
     
        if (elapsedTime == 0)
        {
            return;
        }
        
        setVerticalSpeed(getVerticalSpeed() + elapsedTime * a);
    }

    public String getDirection()
    {
        return direction;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }
}
