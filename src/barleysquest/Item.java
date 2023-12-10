
package barleysquest;

import java.awt.image.BufferedImage;
import com.golden.gamedev.object.AnimatedSprite;

/**
 * The parent item class - all items generated will inherit from this class.
 * extends AnimatedSprite
 * @author Kent
 */
public class Item extends AnimatedSprite {

    protected int points;
    protected int healingPoints;

    public Item(BufferedImage[] images)
    {
        super(images);
    }

    @Override
    public void update(long elapsedTime)
    {


        super.update(elapsedTime);
    }

    public int getPoints()
    {
        return points;
    }

    public void setPoints(int score)
    {
        this.points = score;
    }
}

class FirstAid extends Item {

    public FirstAid(BufferedImage[] images)
    {
        super(images);
        points = 0;
    }
}

class Gem extends Item {

    public Gem(BufferedImage[] images)
    {
        super(images);
        points = 200;
        healingPoints = 0;
    }
}
