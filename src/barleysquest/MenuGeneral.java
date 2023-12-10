
package barleysquest;

import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.GameFontManager;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.ImageBackground;
import java.awt.Font;
import java.awt.image.BufferedImage;

/**
 * The parent class for all the menues in the game.
 * Extends Playfield
 * @author Kent
 */
public class MenuGeneral extends PlayField {

    protected TheGame game;
    protected ImageBackground bg;//background defined as image
    protected BufferedImage backgr;//my background image
    protected Writer exitText;
    protected GameFont menuFont;
    protected int canvasX = 800;
    protected int canvasY = 600;
    protected BufferedImage image;
    protected Writer pointerLocationWriter[];
    protected Sprite hero;

    public MenuGeneral(TheGame game)
    {
        super();
        this.game = game;

        image = game.getImage("images/playerSprite/player_shoot.png", true);
        hero = new Sprite(image);
        pointerLocationWriter = new Writer[3];
        menuFont = (new GameFontManager()).getFont(new Font("arial", Font.PLAIN, 20));

        add(hero);

        //avoiding hero being visible on screen before menu loads
        hero.setLocation(-100, -100);
        load();
    }

    public void update(long elapsedTime)
    {

        super.update(elapsedTime);

    }

    public void load()
    {

        backgr = game.getImage("images/backgrounds/mainMenuBackground.png");
        bg = new ImageBackground(backgr);
        setBackground(bg);


    }
}
