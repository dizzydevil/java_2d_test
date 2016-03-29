package throwaway;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Describe class <code>SpriteSheet</code> here.
 *
 * @author <a href="mailto:lsv@lap06lx.lsv.uni-saarland.de">LSV User</a>
 * @version 1.0
 */
public class SpriteSheet {
    private static final String RESOURCE_DIR = "/art";

    private Bitmap spriteSheet;
    private int spriteSize;

    public SpriteSheet(String resourceName, int spriteSize) {
	this.spriteSize = spriteSize;

	/* load resource */
	URL res = getClass().getResource(RESOURCE_DIR + "/" + resourceName + ".png");
	if (res == null) {
	    throw new IllegalArgumentException("No such resource '" + resourceName + "'");
	}

	BufferedImage image = null;
	try {
	    image = ImageIO.read(res);
	} catch (IOException io) {
	    throw new IllegalArgumentException("Failed to load resource '" + resourceName + "': " + io.getMessage());
	}
	int width = image.getWidth();
	int height = image.getHeight();

	int[] pixels = new int[width * height];
	image.getRGB(0, 0, width, height, pixels, 0, width);

	this.spriteSheet = new Bitmap(pixels, width, height);
    }

    public void renderSprite(Bitmap destination, int spritex, int spritey, int spriteWidth, int spriteHeight, int x, int y) {
	renderSprite(spriteSheet,
		     spritex * spriteSize, spritey * spriteSize, spriteWidth * spriteSize, spriteHeight * spriteSize,
		     x, y, false, false);
    }

    public void renderSprite(Bitmap destination, int spritex, int spritey, int spriteWidth, int spriteHeight, int x, int y,
			     boolean hflip, boolean vflip) {
	destination.render(spriteSheet,
			   spritex * spriteSize, spritey * spriteSize, spriteWidth * spriteSize, spriteHeight * spriteSize,
			   x, y, hflip, vflip);
    }
}