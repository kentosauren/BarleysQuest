
package barleysquest;

import com.golden.gamedev.GameLoader;
import java.awt.Dimension;

/**
 * Main class which executes the game and remains idle.
 * @author Kent
 */
public class Main {

    /****************************************************************************/
    /***************************** START-POINT **********************************/
    /****************************************************************************/
    public static void main(String[] args)
    {
        GameLoader game = new GameLoader();
        game.setup(new TheGame(), new Dimension(800, 600), false);
        game.start();

    }
}
