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
	if (srcx < 0) {
	    srcw += srcx;
	    dstx += srcx;
	    srcx = 0;
	}

	if (srcx + srcw >= src.width) {
	    srcw = src.width - srcx;
	}

	if (srcy < 0) {
	    srch += srcy;
	    dsty += srcy;
	    srcy = 0;
	}

	if (srcy + srch >= src.height) {
	    srch = src.height - srcy;
	}

	if (dstx < 0) {
	    if (!hflip) {
		srcx -= dstx;
	    }
	    srcw += dstx;
	    dstx = 0;
	}

	if (dstx + srcw >= width) {
	    if (hflip) {
		srcx += dstx + srcw - width;
	    }
	    srcw = width - dstx - 1;
	}

	if (dsty < 0) {
	    if (!vflip) {
		srcy -= dsty;
	    }
	    srch += dsty;
	    dsty = 0;
	}

	if (dsty + srch >= height) {
	    if (vflip) {
		srcy += dsty + srch - height;
	    }
	    srch = height - dsty - 1;
	}

	// DEBUG //
	System.out.println("srcx: " + srcx + " srcy: " + srcy + " srcw: " + srcw + " srch: " + srch + " dstx: " + dstx + " dsty: " + dsty + " width: " + width + " height: " + height);

	if (srcw <= 0 || srch <= 0) {
	    return;
	}
	
	for (int y = 0; y < srch; y++) {
	    for (int x = 0; x < srcw; x++) {
		int index = dstx + (hflip ? srcw - x -1 : x) + (dsty + (vflip ? srch - y - 1 : y)) * width;

		int oldPixel = pixels[index];

		int or = (oldPixel >> 16) & 0xFF;
		int og = (oldPixel >> 8) & 0xFF;
		int ob = oldPixel & 0xFF;

		int newPixel = src.pixels[srcx + x + (srcy + y) * src.width];

		int alpha = (newPixel >> 24) & 0xFF;

		int nr = (newPixel >> 16) & 0xFF;
		int ng = (newPixel >> 8) & 0xFF;
		int nb = newPixel & 0xFF;

		int r = ((or * (255-alpha) + nr * alpha) / 255) & 0xFF;
		int g = ((og * (255-alpha) + ng * alpha) / 255) & 0xFF;
		int b = ((ob * (255-alpha) + nb * alpha) / 255) & 0xFF;
		
		pixels[index] = (r << 16) | (g << 8) | b;
	    }
	}
    }
}