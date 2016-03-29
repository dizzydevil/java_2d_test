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

    private int ticks;
    private BufferedImage image;
    private Bitmap screen;
    private int[] pixels;

    private SpriteSheet sprites;

    public Game() {
	this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	this.screen = new Bitmap(pixels, WIDTH, HEIGHT);

	this.sprites = new SpriteSheet("sprites", 16);
    }

    public Dimension getPreferredSize() {
	return new Dimension(ZOOM * WIDTH, ZOOM * HEIGHT);
    }
    
    public void run() {
	ticks = 0;

	long time = System.currentTimeMillis();
	long nextPrint = time + 1000;

	int fps = 0;
	while (true) {
	    tick();
	    render();

	    fps++;
	    if (System.currentTimeMillis() > nextPrint) {
		System.out.println("fps: " + fps);
		fps = 0;
		nextPrint += 1000;
	    }

	    try {
		Thread.sleep(2);		
	    } catch (InterruptedException ignore) {
		/* empty */
	    }
	}
    }

    private void tick() {
	ticks++;
    }

    private void render() {
	BufferStrategy bs = getBufferStrategy();
	if (bs == null) {
	    createBufferStrategy(2);
	    return;
	}

	Graphics g = bs.getDrawGraphics();

	// DEBUG //
	for (int i = 0; i < pixels.length; i++) {
	    pixels[i] = ticks + i;
	}
	sprites.renderSprite(0, 0, 1, 1, 100, 100, screen);

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