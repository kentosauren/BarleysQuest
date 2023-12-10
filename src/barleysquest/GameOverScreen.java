

package barleysquest;


import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.GameFontManager;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.background.ColorBackground;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * This is the class displaying the game over screen if player's life is less than zero
 * @author Kent
 */
public class GameOverScreen extends PlayField {

    private Timer timer;
    private TheGame game;
    private ColorBackground midScreenBackgr;
    private Writer gameOverText;
    private Writer scoreText;
    private GameFont gameOverFont;

    GameOverScreen(TheGame game)
    {
        super();

        this.game = game;
        midScreenBackgr = new ColorBackground(new Color(0, 0, 0));
        gameOverFont = (new GameFontManager()).getFont(new Font("Agency FB", Font.BOLD, 60));
        gameOverText = new Writer("GAME OVER ", Color.white, gameOverFont, 0, 0);
        gameOverText.setLocation(100,230);

        scoreText = new Writer("Score: "+game.getPlayerScore(), Color.white, gameOverFont, 0, 0);
        scoreText.setLocation(100, 360);
        timer = new Timer(4000);
        timer.setActive(true);
        timer.setCurrentTick(0);

        add(gameOverText);
        add(scoreText);

    }

    @Override
    public void update(long elapsedTime)
    {
        if (timer.action(elapsedTime))
            {
            
            game.setCurrentLevel(new MainMenu(game));
            }
             midScreenBackgr.update(elapsedTime);
        gameOverText.update(elapsedTime);
        super.update(elapsedTime);
    }

    @Override
    public void render(Graphics2D g)
    {
        gameOverText.render(g);
        midScreenBackgr.render(g);
        super.render(g);
    }
}