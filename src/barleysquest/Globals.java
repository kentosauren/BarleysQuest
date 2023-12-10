

package barleysquest;

/**
 *
 * @author Kent
 */
public class Globals {


          /**
     * The relationship between meters and px
     */
    static public double m = 100; // px/m

    /**
     * For convenience: the gravity on earth in px/ms^
     */
    
    static public int tileSize = 50;//each tile is 50px
    static public int gameHeight =  12;// 600px / 50px
    static public int gameWidth = 16; // 800 / 50px
    static public double g = 9.81 * m / 1000000; // px/ms^2
}
