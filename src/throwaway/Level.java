package throwaway;

import java.awt.Dimension;

/**
    The level layout is a 2d-array of tile numbers:
    11, 12, 13,
    21, 22, 23,
    31, 32, 33,
    
    The size of the level equals the array dimensions:
    LEVEL_NUM_TILES_X
    LEVEL_NUM_TILES_Y
    
    Rendering: 
              11 
           21    12
        31    22    13
           32    23
              33 
    
    Tiles are rendered from back to front:
    11, 12, 13, 21, 22, 23, 31, 32, 33

    displacement of each tile in a tile row: 8, 4
    displacement of tile rows: -8, 4
    
    Tiles are stored in a TileSheet.
    Each tile has
    - position/dimension in TileSheet
    - number/id used in level array
    
    Additionally, there is an array of moving objects, sprites and their positions.
    
    each tile occupies a basic tile size on the floor plane:
    TILE_SIZE_X
    TILE_SIZE_Y
    
    The actual size of the image drawn for a tile may differ.
 */

public class Level {
    private TileSheet tiles;
    
    public static final int TILE_WALL = 0;
    public static final int TILE_FLOOR = 1;
    
    public static final int[][] testlevel = {
        {TILE_WALL, TILE_WALL,  TILE_WALL,  TILE_WALL},
        {TILE_WALL, TILE_FLOOR, TILE_FLOOR, TILE_WALL},
        {TILE_WALL, TILE_FLOOR, TILE_FLOOR, TILE_WALL},
        {TILE_WALL, TILE_WALL,  TILE_WALL,  TILE_WALL},
    };
    
    /* level dimensions */
    private int numCellsX = 4;
    private int numCellsY = 4;
    
    
    /* 
    Set the tile set,
    iso cell dimensions,
    level width/height in cells
    cell displacement
    row displacement
    */
    public Level(TileSheet tiles){
        this.tiles = tiles;
    }
    
    public void render(Bitmap destination){
        /* cell row/column counters */
        int cellX, cellY;

        /* same as isoCellSize in Tile sheet - where to put this? */
        int cellDisplacementX = 8;
        int cellDisplacementY = 4;
                
        /* initial pixel coordinate of a cell row */
        int xRow = numCellsX*cellDisplacementX;
        int yRow = 0;
        
        /* pixel coordinates of a cell */
        int x, y;
        
        for (cellY = 0; cellY<numCellsY; cellY++){
            xRow-=cellDisplacementX;
            yRow+=cellDisplacementY;
            
            x = xRow;
            y = yRow;
            
            for (cellX = 0; cellX<numCellsX; cellX++){
                this.tiles.renderTile(testlevel[cellY][cellX], x, y, destination);
                
                x+=cellDisplacementX;
                y+=cellDisplacementY;
            }
        }
        
    }
    
}