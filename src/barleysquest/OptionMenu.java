
package barleysquest;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 *
 * @author Kent
 */
class OptionMenu extends MenuGeneral {

    protected Writer musicText, lifeText;
    private int menuCounter;
    private String songArray[];
    private int songCounter;
    private int livesCounter;

    public OptionMenu(TheGame game)
    {
        super(game);

        songArray = new String[5];
        songArray[0] = "music/the_beat_is_back_kbd.mp3";
        songArray[1] = "music/spark_the_flute_kbd.mp3";
        songArray[2] = "music/one_life_apart_kbd.mp3";
        songArray[3] = "music/introVideoSong.mp3";
        songArray[4] = "music/bossTheme.mp3";

        //defines three writer-varibales for text use
        musicText = new Writer("", Color.WHITE, menuFont, (100), 320);
        lifeText = new Writer("", Color.WHITE, menuFont, (100), 420);
        exitText = new Writer("Back ", Color.WHITE, menuFont, (100), 520);


        //adding to playfield
        add(musicText);
        add(lifeText);
        add(exitText);
        //menucounter for the navigation of the menu
        menuCounter = 0;
        songCounter = 0;
        livesCounter = game.getPlayerLives();
        pointerLocationWriter[0] = musicText;
        pointerLocationWriter[1] = lifeText;
        pointerLocationWriter[2] = exitText;


    }

    @Override
    public void update(long elapsedTime)
    {

        musicText.setText("Music Test: Level" + (songCounter + 1) + " " + songArray[songCounter].substring(6, songArray[songCounter].length() - 7));
        lifeText.setText("Lives X " + game.getPlayerLives());


            if (game.keyPressed(KeyEvent.VK_UP))
            {
                menuCounter--;
                if (menuCounter < 0)
                {
                    menuCounter = 2;
                }
            }
            if (game.keyPressed(KeyEvent.VK_DOWN))
            {
                menuCounter++;

                if (menuCounter > 2)
                {
                    menuCounter = 0;
                }
            } else if (game.keyPressed(KeyEvent.VK_ENTER))
            {
                if (menuCounter == 0)
                {
                    game.playMusic(songArray[songCounter]);
                }
                if (menuCounter == 1)
                {
                    game.setCurrentLevel(new OptionMenu(game));
                }
                if (menuCounter == 2)
                {
                    game.setCurrentLevel(new MainMenu(game));
                }
            }


            if (game.keyPressed(KeyEvent.VK_RIGHT) && menuCounter == 0)
            {
                songCounter++;
                if (songCounter >= songArray.length)
                    songCounter = 0;

            } else if (game.keyPressed(KeyEvent.VK_LEFT) && menuCounter == 0)
            {
                songCounter--;
                if (songCounter < 0)
                    songCounter = songArray.length-1;
            }
            if (game.keyPressed(KeyEvent.VK_RIGHT) && menuCounter == 1)
            {
                livesCounter++;
                if (livesCounter > 7)
                    livesCounter = 1;
                game.setPlayerLives(livesCounter);

            } else if (game.keyPressed(KeyEvent.VK_LEFT) && menuCounter == 1)
            {
                livesCounter--;
                if (livesCounter < 1)
                    livesCounter = 7;
                game.setPlayerLives(livesCounter);
            }
            hero.setLocation((pointerLocationWriter[menuCounter].getX() - 70), ((pointerLocationWriter[menuCounter].getY()) - (hero.getHeight() / 3)));
            super.update(elapsedTime);
        }

    }

