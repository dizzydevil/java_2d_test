package throwaway;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * Describe class <code>InputHandler</code> here.
 *
 * @author <a href="mailto:lsv@lap06lx.lsv.uni-saarland.de">LSV User</a>
 * @version 1.0
 */
public class InputHandler implements KeyListener {
    private static final int DOWN_CODE = KeyEvent.VK_DOWN;
    private static final int UP_CODE = KeyEvent.VK_UP;
    private static final int LEFT_CODE = KeyEvent.VK_LEFT;
    private static final int RIGHT_CODE = KeyEvent.VK_RIGHT;
    
    public boolean left = false;
    public boolean right = false;
    public boolean up = false;
    public boolean down = false;

    private boolean leftPressed = false;
    private boolean leftReleased = false;
    private boolean rightPressed = false;
    private boolean rightReleased = false;
    private boolean upPressed = false;
    private boolean upReleased = false;
    private boolean downPressed = false;
    private boolean downReleased = false;

    public InputHandler(Game game) {
	game.addKeyListener(this);
    }

    public void keyPressed(KeyEvent e) {
	switch (e.getKeyCode()) {
	case LEFT_CODE: leftPressed = true; break;
	case RIGHT_CODE: rightPressed = true; break;
	case UP_CODE: upPressed = true; break;
	case DOWN_CODE: downPressed = true; break;
	}
    }

    public void keyReleased(KeyEvent e) {
	switch (e.getKeyCode()) {
	case LEFT_CODE: leftReleased = true; break;
	case RIGHT_CODE: rightReleased = true; break;
	case UP_CODE: upReleased = true; break;
	case DOWN_CODE: downReleased = true; break;
	}
    }

    public void keyTyped(KeyEvent e) {
	/* empty */
    }
    
    public void tick() {
	if (leftPressed) { left = true; leftPressed = false; }
	if (leftReleased) { left = false; leftReleased = false; }
	if (rightPressed) { right = true; rightPressed = false; }
	if (rightReleased) { right = false; rightReleased = false; }
	if (downPressed) { down = true; downPressed = false; }
	if (downReleased) { down = false; downReleased = false; }
	if (upPressed) { up = true; upPressed = false; }
	if (upReleased) { up = false; upReleased = false; }
    }
}