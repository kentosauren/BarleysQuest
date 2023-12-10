
package barleysquest;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * This class displays the main menu in the game and enables all of it's controls
 * @author Kent
 */
public class MainMenu extends MenuGeneral {
    
    protected Writer newGameText, optionText;
    private int menuCounter;



    public MainMenu(TheGame game)
    {
        super(game);
        game.setMenuBool(true);
   //     game.setStageCounter(0);

        newGameText = new Writer("New Game", Color.WHITE, menuFont, (canvasX / 3), 320);
        optionText = new Writer("Options", Color.WHITE, menuFont, (canvasX / 3), 420);
        exitText = new Writer("Exit", Color.WHITE, menuFont, (canvasX / 3), 520);

        add(newGameText);
        add(optionText);
        add(exitText);

        menuCounter = 0;
        pointerLocationWriter[0] = newGameText;
        pointerLocationWriter[1] = optionText;
        pointerLocationWriter[2] = exitText;

       // if(game.isMusicOnOff())
        game.playMusic("music/mainMenuSong.mp3");

    }

    @Override
    public void update(long elapsedTime)
    {
    
        

            if (game.keyPressed(KeyEvent.VK_UP))
            {
                menuCounter--;
                if (menuCounter < 0)
                {
                    menuCounter = 2;
                }
            } else if (game.keyPressed(KeyEvent.VK_DOWN))
            {
                menuCounter++;

                if (menuCounter > 2)
                {
                    menuCounter = 0;
                }
            } else if (game.keyPressed(KeyEvent.VK_ENTER) || game.keyPressed(KeyEvent.VK_Z))
            {
                if (menuCounter == 0)
                    {
                    game.setPlayerScore(0);
                    game.setGems(0);
                    game.setStageCounter(0);
                    game.setMenuBool(false);
                    game.setCurrentLevel(new PreStageScreen(game));
                    game.playMusic("");

                    }
                if (menuCounter == 1)
                    game.setCurrentLevel(new OptionMenu(game));
                if (menuCounter == 2)
                    game.finish();

            }
            hero.setLocation((pointerLocationWriter[menuCounter].getX() - 70), ((pointerLocationWriter[menuCounter].getY())-(hero.getHeight()/3)));
            super.update(elapsedTime);
        }
    }


    
