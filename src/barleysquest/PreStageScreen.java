
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
 * This page is displayed before every level, introducing it.
 * @author Kent
 */
public class PreStageScreen extends PlayField {

    private Timer timer;
    private TheGame game;
    private ColorBackground midScreenBackgr;
    private Writer introText;
    private GameFont gameOverFont;
    private LevelDesign lvl;
    private Sprite stagePhoto;
    
    PreStageScreen(TheGame game)
    {
        super();

        this.game = game;
        lvl = new LevelDesign();

        midScreenBackgr = new ColorBackground(new Color(0, 0, 0));
        gameOverFont = (new GameFontManager()).getFont(new Font("arial", Font.BOLD, 70));
        introText = new Writer(lvl.getLevelNames(game.getStageCounter()) , Color.white, gameOverFont, 0, 0);
        introText.setLocation(50,100);
        stagePhoto = new Sprite(game.getImage(lvl.getStagePhoto(game.getStageCounter())));
        stagePhoto.setLocation(300, 300);
        timer = new Timer(1800);
        timer.setActive(true);
        timer.setCurrentTick(0);


        add(stagePhoto);
        add(introText);
       
    }

    @Override
    public void update(long elapsedTime)
    {
        if (timer.action(elapsedTime))
            game.setCurrentLevel(new LevelLoader(game));



        midScreenBackgr.update(elapsedTime);
        introText.update(elapsedTime);
        super.update(elapsedTime);




    }

    @Override
    public void render(Graphics2D g)
    {
        introText.render(g);
        midScreenBackgr.render(g);
        super.render(g);
    }
}
