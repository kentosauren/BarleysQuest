
package barleysquest;


import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.GameFontManager;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.background.ColorBackground;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Displaying lives left before it takes you back to the current level.
 * @author Kent
 */
class DeathScreen extends PlayField {

    private Timer timer;
    private TheGame game;
    private ColorBackground midScreenBackgr;
    private Writer livesLeft;
    private GameFont gameOverFont;
    private LevelDesign lvl;
    private Sprite playerHead;

    public DeathScreen(TheGame game)
    {
        super();

        this.game = game;
        lvl = new LevelDesign();

        midScreenBackgr = new ColorBackground(new Color(0, 0, 0));
        gameOverFont = (new GameFontManager()).getFont(new Font("arial", Font.BOLD, 60));
        livesLeft = new Writer("x " +game.getPlayerLives(), Color.white, gameOverFont, 0, 0);
        livesLeft.setLocation(game.getWidth()/2, (game.getHeight()/2) - (livesLeft.getHeight()));
        playerHead = new Sprite(game.getImage("images/playerSprite/player_head_larger.png"));
        playerHead.setLocation(livesLeft.getX() - playerHead.getWidth() - 50, livesLeft.getY()+25);
        timer = new Timer(2000);
        timer.setActive(true);
        timer.setCurrentTick(0);

        add(playerHead);
        add(livesLeft);
    }

    @Override
    public void update(long elapsedTime)
    {
        if (timer.action(elapsedTime))
            game.setCurrentLevel(new LevelLoader(game));

        midScreenBackgr.update(elapsedTime);
        livesLeft.update(elapsedTime);
        super.update(elapsedTime);
    }

    @Override
    public void render(Graphics2D g)
    {
        livesLeft.render(g);
        midScreenBackgr.render(g);
        super.render(g);
    }
}
