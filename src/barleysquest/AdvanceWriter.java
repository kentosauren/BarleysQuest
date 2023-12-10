

package barleysquest;

import com.golden.gamedev.object.GameFont;

/**
 * Extends the Writer, adding support for prefix and appendix the the given text
 * Used to display time, score etc
 *
 * @author Kent
 */
public class AdvanceWriter extends Writer {
    
    private String prefix;
    private String appendix;
    private String value;

    public AdvanceWriter(String prefix, String initValue, String appendix, GameFont font, int x, int y) {
        super(null, null, font, x, y);
        this.prefix = prefix;
        this.value = initValue;
        this.appendix = appendix;
        setFont(font);
        update(0);
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void update(long elapsedTime) {
        setText(prefix+value+appendix);
    }

}
