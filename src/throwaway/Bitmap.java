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

	sx1 = Math.max(sx1, 0);
	sx2 = Math.min(sx2, src.width - 1);
	sy1 = Math.max(sy1, 0);
	sy2 = Math.min(sy2, src.height - 1);

	if (dstx < 0) {
	    sx1 -= dstx;
	    dstx = 0;
	}

	if (dstx + (sx2 - sx1 + 1) >= width) {
	    sx2 -= dstx + (sx2 - sx1 + 1) - width - 1;
	}

	if (dsty < 0) {
	    sy1 -= dsty;
	    dsty = 0;
	}

	if (dsty + (sy2 - sy1 + 1) >= height) {
	    sy2 -= dsty + (sy2 - sy1 + 1) - height - 1;
	}

	int dx = sx1 < sx2 ? 1 : (sx1 > sx2 ? -1 : 0);
	int dy = sy1 < sy2 ? 1 : (sy1 > sy2 ? -1 : 0);

	int y = dsty;
	int sy = sy1 - dy;
	do {
	    sy += dy;

	    int x = dstx;
	    int sx = sx1 - dx;

	    do {
		sx += dx;
		
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

		x++;
	    } while (sx != sx2);

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