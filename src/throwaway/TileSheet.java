package throwaway;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Describe class <code>TileSheet</code> here.
 *
 * @author <a href="mailto:lsv@lap06lx.lsv.uni-saarland.de">LSV User</a>
 * @version 1.0
 */
public class TileSheet {
    private static final String RESOURCE_DIR = "/art";

    private Bitmap tileSheet;

    /* positions and dimensions of the tiles on the tile sheet */
    private int [] xPos, yPos, xSize, ySize;
    
    /* */
    private int isoCellSizeX;
    private int isoCellSizeY;
    
    public Dimension getIsoCellSize(){
        return new Dimension(isoCellSizeX, isoCellSizeY);
    }
    
    public TileSheet(String resourceName) {
    
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

	this.tileSheet = new Bitmap(pixels, width, height);
    }
    
    /* Set up the positions and dimensions of the tiles.
        Currently, this assumes that all tiles have the same size and are ordered in a regular grid on the tile sheet. */
        
    public void setupTiles(int numTiles, int numTilesRow, int tileSizeX, int tileSizeY, int cellSizeX, int cellSizeY) {
        this.xPos = new int[numTiles];
        this.yPos = new int[numTiles];
        this.xSize = new int[numTiles];
        this.ySize = new int[numTiles];
        this.isoCellSizeX = cellSizeX;
        this.isoCellSizeY = cellSizeY;

        int x = 0;
        int y = 0;
        int i;
        for (i=0; i<numTiles; i++) {
            xPos[i] = x;
            yPos[i] = y;
            xSize[i] = tileSizeX;
            ySize[i] = tileSizeY;
            
            x = x + tileSizeX;
            y = y + tileSizeY;
            if (i % numTilesRow == 0 ){
                x = 0;
            }
        }
    }

/*
    The tile sheet is a large bitmap.
    The tiles are defined by position and dimension in the bitmap
     
    tileNo: tile number
    x, y: position in destination bitmap
        
    public void render(Bitmap src, int srcx, int srcy, int srcw, int srch, int dstx, int dsty) 
        src, srcx, srcy, srcw, srch: source bitmap, position and dimensions
        dstx, dsty: destination position in the display bitmaps
*/
    
    public void renderTile(int tileNo, int x, int y, Bitmap destination) {
        /* map tileNo to position and dimensions in tilesheet */
        destination.render(tileSheet, xPos[tileNo], yPos[tileNo], xSize[tileNo], ySize[tileNo], x, y);
    }
}