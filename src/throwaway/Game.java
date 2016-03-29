package throwaway;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

/**
 * Describe class <code>Game</code> here.
 *
 * @author <a href="mailto:lsv@lap06lx.lsv.uni-saarland.de">LSV User</a>
 * @version 1.0
 */
public class Game extends Canvas {
    private static final int WIDTH = 320;
    private static final int HEIGHT = 200;
    private static final int ZOOM = 3;
    private static final int FRAMES_PER_SECOND = 60;

    private int ticks;
    private BufferedImage image;
    private Bitmap screen;
    private int[] pixels;

    private SpriteSheet sprites;

    private InputHandler inputHandler;
    
    public Game() {
	this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	this.screen = new Bitmap(pixels, WIDTH, HEIGHT);

	this.sprites = new SpriteSheet("sprites", 16);

	this.inputHandler = new InputHandler(this);
    }

    public Dimension getPreferredSize() {
	return new Dimension(ZOOM * WIDTH, ZOOM * HEIGHT);
    }
    
    public void run() {
	ticks = 0;

	long time = System.currentTimeMillis();
	long nextPrint = time + 1000;

	long nanosPerFrame = 1000000000 / FRAMES_PER_SECOND;
	long nextTick = System.nanoTime() + nanosPerFrame;
	
	int fps = 0;
	while (true) {
	    handleInput();
	    tick();
	    render();

	    long sleepNanos = nextTick - System.nanoTime();

	    while (sleepNanos > 0) {
		long sleepMillis = sleepNanos / 1000000;
		int sleepRest = (int) (sleepNanos - 1000000 * sleepMillis);

		try {
		    Thread.sleep(sleepMillis, sleepRest);
		} catch (InterruptedException ignore) {
		    /* empty */
		}

		sleepNanos = nextTick - System.nanoTime();
	    }

	    fps++;
	    if (System.currentTimeMillis() > nextPrint) {
		System.out.println("fps: " + fps);
		fps = 0;
		nextPrint += 1000;
	    }

	    nextTick += nanosPerFrame;
	}
    }

    int debugx = WIDTH / 2 - 8;
    int debugy = HEIGHT / 2 - 8;

    private void handleInput() {
	inputHandler.tick();

	if (inputHandler.down) debugy++;
	if (inputHandler.up) debugy--;
	if (inputHandler.left) debugx--;
	if (inputHandler.right) debugx++;
    }

    private void tick() {
	ticks++;
    }

    private void render() {
	BufferStrategy bs = getBufferStrategy();
	if (bs == null) {
	    createBufferStrategy(2);
	    requestFocus();
	    return;
	}

	Graphics g = bs.getDrawGraphics();

	// clear to white
	for (int i = 0; i < pixels.length; i++) {
	    pixels[i] = ticks + i;
	}
	sprites.renderSprite(screen, 0, 0, 1, 1, debugx, debugy, false, false);

	g.drawImage(image, 0, 0, ZOOM * WIDTH, ZOOM * HEIGHT, null);
	g.dispose();
	bs.show();
    }
    
    public void update(Graphics g) {
	/* empty */
    }
    
    public static void main(String[] args) {
	Game game = new Game();

	JFrame frame = new JFrame("ThrowAway");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().add(game);
	frame.pack();
	frame.setResizable(false);
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
	
	game.run();
    }
}