
package barleysquest;

import java.awt.event.*;

import java.awt.Graphics2D;
import com.golden.gamedev.*;
//import com.golden.gamedev.engine.BaseAudioRenderer;

import com.golden.gamedev.object.*;
import com.golden.gamedev.engine.audio.JavaLayerMp3Renderer;

/**
 * The root class of all commands, updating and rendering for the entire game.
 * All media and visible objects are run through this class.
 * Extends Game - GameObject
 * @author Kent
 */
public class TheGame extends Game {

    //creates a playfield which will be changed to the current level so it can be rendered
    private PlayField currentLevel;
    private PlayField pauseMenu;
    private int playerLives;
    private boolean paused;
    private boolean musicOnOff;
    private static int stageCounter, playerScore, gems;
    private boolean menuBool;

    {
        distribute = true;
    }
    /****************************************************************************/
    /**************************** GAME SKELETON *********************************/
    /****************************************************************************/
    @Override
    protected void initEngine()
    {
        super.initEngine();

        // set sound effect to use mp3
        // bsSound.setBaseRenderer(new JavaLayerMp3Renderer());

        // set music to use mp3
        bsMusic.setBaseRenderer(new JavaLayerMp3Renderer());

    }

    public void initResources()
    {

        setFPS(60);//sets the fps of the game to 60
        bsGraphics.setWindowTitle("Barleys Quest - The war of the elements");
        pauseMenu = new PauseMenu(this);
        
        currentLevel = new MainMenu(this);
        musicOnOff = true;
        menuBool = false;
        paused = false;
        playerLives = 3;
        stageCounter = 0;
        gems = 0;
    }

    /****************************************************************************/
    /***************************** UPDATE **********************************/
    /****************************************************************************/
    public void update(long elapsedTime)
    {

        //game tester's mode
       
        if (keyPressed(KeyEvent.VK_1))
        {
            stageCounter = 0;
            currentLevel = new PreStageScreen(this);
        } else if (keyPressed(KeyEvent.VK_2))
        {
            stageCounter = 1;
            currentLevel = new PreStageScreen(this);
        } else if (keyPressed(KeyEvent.VK_3))
        {
            stageCounter = 2;
            currentLevel = new PreStageScreen(this);
        } else if (keyPressed(KeyEvent.VK_4))
        {
            stageCounter = 3;
            currentLevel = new PreStageScreen(this);
        } else if (keyPressed(KeyEvent.VK_5))
        {
            stageCounter = 3;
            currentLevel = new CongratulationsScreen(this);
        }

        if (keyPressed(KeyEvent.VK_ESCAPE) && !menuBool)
        {
            
            paused = !paused;
            
        }
        if (paused)
        {
            pauseMenu.update(elapsedTime);
        } else
        {
            currentLevel.update(elapsedTime);
        }
    }//end update

    /****************************************************************************/
    /***************************** RENDER **********************************/
    /****************************************************************************/
    public void render(Graphics2D g)
    {
        //renders the current level
        if (paused)
        {
            pauseMenu.render(g);
        } else
        {
            currentLevel.render(g);
        }
    }

    public void setCurrentLevel(PlayField currentLevel)
    {
        this.currentLevel = currentLevel;
    }

    public PlayField getCurrentLevel()
    {
        return currentLevel;
    }

    public int getPlayerLives()
    {
        return playerLives;
    }

    public void setPlayerLives(int playerLives)
    {
        this.playerLives = playerLives;
    }

    public boolean isPaused()
    {
        return paused;
    }

    public void setPaused(boolean paused)
    {
        this.paused = paused;
    }

    public boolean isMusicOnOff()
    {
        return musicOnOff;
    }

    public void setMusicOnOff(boolean musicOnOff)
    {
        this.musicOnOff = musicOnOff;
    }

    public int getStageCounter()
    {
        return stageCounter;
    }

    public void setStageCounter(int stageCounter)
    {
        this.stageCounter = stageCounter;
    }

    void setMenuBool(boolean b)
    {
       menuBool = b;
    }

    public int getPlayerScore()
    {
        return playerScore;
    }

    public void setPlayerScore(int playerScore)
    {
        this.playerScore = playerScore;
    }

    public int getGems()
    {
        return gems;
    }

    public void setGems(int gems)
    {
        this.gems = gems;
    }



}//end TheGame

