
package barleysquest;

/*
 * @author Kent
 */


import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.GameFontManager;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.background.ColorBackground;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Displays a congratulary screen if game is finished.
 * @author Kent
 */
 class CongratulationsScreen extends PlayField {

    private Timer timer;
    private TheGame game;
    private ColorBackground midScreenBackgr;
    private Writer congratulationsText;
    private Writer scoreText;
    private GameFont gameOverFont;

    CongratulationsScreen(TheGame game)
    {
        super();

        this.game = game;
        midScreenBackgr = new ColorBackground(new Color(0, 204, 102));
        gameOverFont = (new GameFontManager()).getFont(new Font("arial", Font.BOLD, 20));
        congratulationsText = new Writer("CONGRATULATIONS!\n YOU CLEARED THE GAME! ", Color.white, gameOverFont, 0, 0);
        congratulationsText.setLocation(100,230);

        scoreText = new Writer("Score: "+game.getPlayerScore(), Color.white, gameOverFont, 0, 0);
        scoreText.setLocation(100, 360);
        timer = new Timer(8000);
        timer.setActive(true);
        timer.setCurrentTick(0);
       
        add(congratulationsText);
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
        //congratulationsText.update(elapsedTime);
        super.update(elapsedTime);




    }

    @Override
    public void render(Graphics2D g)
    {
        congratulationsText.render(g);
        midScreenBackgr.render(g);
        super.render(g);
    }
}