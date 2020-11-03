// The "Tetromino" class.
import java.awt.image.*;
import java.awt.*;

public class Tetromino
{
    private int[] [] [] coords;
    private int height;
    private int width;
    public Tetromino (int[] [] [] coords)
    {
	this.coords = coords;
    }


    public int[] [] [] getArray ()
    {
	return coords;
    }


    public int getWidth (int rotation)
    {
	width = coords [rotation] [0].length;
	return width;
    }


    public int getHeight (int rotation)
    {
	height = coords [rotation].length ;
	return height;
    }
} // Tetromino class
