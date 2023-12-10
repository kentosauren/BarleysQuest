
package barleysquest;

import java.awt.Color;
import java.awt.event.KeyEvent;


/**
 *
 * @author Kent
 */
class PauseMenu extends MenuGeneral {

    private Writer continueGameText, backToMainMenu;
    private int menuCounter;

    public PauseMenu(TheGame game)
    {
        super(game);

        //defines three writer-varibales for text use
        continueGameText = new Writer("Continue game", Color.WHITE, menuFont, (80), 320);
        backToMainMenu = new Writer("Exit to main menu ", Color.WHITE, menuFont, (80), 420);
        exitText = new Writer("Exit game", Color.WHITE, menuFont, (80), 520);


        //adding to playfield
        add(continueGameText);
        add(exitText);
        add(backToMainMenu);



        //menucounter for the navigation of the menu
        menuCounter = 0;
        pointerLocationWriter[0] = continueGameText;
        pointerLocationWriter[1] = backToMainMenu;
        pointerLocationWriter[2] = exitText;


    }

    @Override
    public void update(long elapsedTime)
    {
        //System.out.println(menuCounter);


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

            }
        
        if (game.keyPressed(KeyEvent.VK_ENTER))
        {
            if (menuCounter == 0)
            {
                game.setPaused(false);
            }
            if (menuCounter == 1)
            {
                game.setCurrentLevel(new MainMenu(game));
                game.setPaused(false);
            }
            if (menuCounter == 2)
            {
                game.finish();

            }
        }
        hero.setLocation((pointerLocationWriter[menuCounter].getX() - 70), ((pointerLocationWriter[menuCounter].getY())-(hero.getHeight()/3)));
        super.update(elapsedTime);


    }

}
