

package barleysquest;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.Timer;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Simple class for writing text to the screen on a given time-interval (optional)
 *
 * @author Kent
 */
public class Writer extends Sprite{
        private GameFont font;
    private int delay;
    private String text;
    private Timer finishedTimer;
    private Timer startTimer;
    private Color color;
    private boolean center = true;
    private boolean display = true;

    public Writer(String text, Color color, GameFont font, int x, int y) {
        super(x, y);
        this.text = text;
        this.font = font;
        this.color = color;

    }

    public Writer(int delay, String text, Color color, GameFont font, int x, int y) {
        super( (x-font.getWidth(text))/2, y);

        this.delay = delay;
        this.text = text;
        this.font = font;
        this.color = color;
        finishedTimer = new Timer(delay);
    }


    public Writer(int start, int delay, String text, Color color, GameFont font, int x, int y) {
        super( (x-font.getWidth(text))/2, y);
        this.delay = delay;
        this.text = text;
        this.font = font;
        this.color = color;
        finishedTimer = new Timer(delay);

        display = false;
        startTimer = new Timer(start);
    }


    @Override
    public void update(long elapsedTime) {
        super.update(elapsedTime);
        if(startTimer!=null && startTimer.action(elapsedTime)) {
            startTimer = null;
            display = true;
        }

        if(display && finishedTimer!= null && finishedTimer.action(elapsedTime)) {
            setActive(false);
        }
    }


    public void setText(String text) {
        this.text = text;
    }


    public void setFont(GameFont font) {
        this.font = font;
    }


    @Override
    public void render(Graphics2D g) {
        if(display) {
            if(color==null) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(color);
            }
            font.drawString(g, text, (int)getX(), (int)getY());
        }
    }


}