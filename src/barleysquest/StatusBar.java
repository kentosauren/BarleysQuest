
package barleysquest;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This is where all status bars are generated and rendered.
 * It calculates dynamically the gauge level.
 * @author Kent
 */
public class StatusBar {

    private Color borderColor = Color.BLACK;
    private Color barColor ;
    private int x;
    private int y;
    private int height = 120;
    private int width = 30;
    private int max;
    private int min;

    public StatusBar(int x, int y, int max, int min, Color barColor)
    {
        this.x = x;
        this.y = y;
        this.max = max;
        this.min = min;

        this.barColor = barColor;

    }

    public void drawStatusBar(Graphics2D g, int statusInfo)
    {
        g.setColor(borderColor);
        g.drawRect(x, y, width, height);

        int interval = max - min;
        double fract = (double) statusInfo / interval;

        g.setColor(barColor);
        int valheight = (int) (fract * height);
        g.fillRect(x, y + (height - valheight), width, valheight);
    }

   public int getX()
    {
        return x;
    }

    int getY()
    {
        return y;
    }

    void setColor(Color barColor)
    {
      this.barColor = barColor;
    }
}
