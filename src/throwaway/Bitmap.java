package throwaway;

/**
 * Describe class <code>Bitmap</code> here.
 *
 * @author <a href="mailto:lsv@lap06lx.lsv.uni-saarland.de">LSV User</a>
 * @version 1.0
 */
public class Bitmap {
    private int[] pixels;
    private int width;
    private int height;

    public Bitmap(int[] pixels, int width, int height) {
	this.pixels = pixels;
	this.width = width;
	this.height = height;
    }

    public void render(Bitmap src, int srcx, int srcy, int srcw, int srch, int dstx, int dsty) {
	render(src, srcx, srcy, srcw, srch, dstx, dsty, false, false);
    }

    public void render(Bitmap src, int srcx, int srcy, int srcw, int srch, int dstx, int dsty, boolean hflip, boolean vflip) {
	int sx1, sy1;
	int sx2, sy2;

	if (hflip) {
	    sx1 = srcx + srcw - 1;
	    sx2 = srcx;
	} else {
	    sx1 = srcx;
	    sx2 = srcx + srcw - 1;
	}

	if (vflip) {
	    sy1 = srcy + srch - 1;
	    sy2 = srcy;
	} else {
	    sy1 = srcy;
	    sy2 = srcy + srch - 1;
	}

	int dx = sx1 < sx2 ? 1 : (sx1 > sx2 ? -1 : 0);
	int dy = sy1 < sy2 ? 1 : (sy1 > sy2 ? -1 : 0);

	System.out.println("sx1: " + sx1 + " sx2: " + sx2 + " sy1: " + sy1 + " sy2: " + sy2 + " dstx: " + dstx + " dsty: " + dsty);

	int y = dsty;
	int sy = sy1;
	do {
	    int x = dstx;
	    int sx = sx1;
	    do {
		int index = x + y * width;

		int oldPixel = pixels[index];

		int or = (oldPixel >> 16) & 0xFF;
		int og = (oldPixel >> 8) & 0xFF;
		int ob = oldPixel & 0xFF;

		int newPixel = src.pixels[sx + sy * src.width];

		int alpha = (newPixel >> 24) & 0xFF;

		int nr = (newPixel >> 16) & 0xFF;
		int ng = (newPixel >> 8) & 0xFF;
		int nb = newPixel & 0xFF;

		int r = ((or * (255-alpha) + nr * alpha) / 255) & 0xFF;
		int g = ((og * (255-alpha) + ng * alpha) / 255) & 0xFF;
		int b = ((ob * (255-alpha) + nb * alpha) / 255) & 0xFF;
		
		pixels[index] = (r << 16) | (g << 8) | b;

		sx += dx;
		x++;
	    } while (sx != sx2);

	    sy += dy;
	    y++;
	} while (sy != sy2);
	
	// for (int y = 0; y < srch; y++) {
	//     for (int x = 0; x < srcw; x++) {
	// 	int index = dstx + (hflip ? srcw - x -1 : x) + (dsty + (vflip ? srch - y - 1 : y)) * width;

	// 	int oldPixel = pixels[index];

	// 	int or = (oldPixel >> 16) & 0xFF;
	// 	int og = (oldPixel >> 8) & 0xFF;
	// 	int ob = oldPixel & 0xFF;

	// 	int newPixel = src.pixels[srcx + x + (srcy + y) * src.width];

	// 	int alpha = (newPixel >> 24) & 0xFF;

	// 	int nr = (newPixel >> 16) & 0xFF;
	// 	int ng = (newPixel >> 8) & 0xFF;
	// 	int nb = newPixel & 0xFF;

	// 	int r = ((or * (255-alpha) + nr * alpha) / 255) & 0xFF;
	// 	int g = ((og * (255-alpha) + ng * alpha) / 255) & 0xFF;
	// 	int b = ((ob * (255-alpha) + nb * alpha) / 255) & 0xFF;
		
	// 	pixels[index] = (r << 16) | (g << 8) | b;
	//     }
	// }
    }
}