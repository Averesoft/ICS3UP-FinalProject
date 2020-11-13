//Jeremy Liang and Krish Patel
//11/10/20
//Mr. Guglielmi
//Thread the moves the tetromino down
import java.awt.*;
import java.awt.image.*;
import hsa.Console;
public class MoveDown extends Thread
{
    Console c;
    static int blockX, blockY, rotation;
    int dropRate = 500;
    int height, width;
    int landed[][], block[][][];
    int diff = 10;
    int linesCleared;
    BufferedImage cyan = null;
    BufferedImage blue = null;
    BufferedImage green = null;
    BufferedImage orange = null;
    BufferedImage purple = null;
    BufferedImage red = null;
    BufferedImage yellow = null;
    BufferedImage grey = null;  
    MoveDown(Console c) {
	this.c = c;
    }
    public void drop() {
	try {
	    sleep(dropRate-linesCleared*10);
	} catch (Exception e) {
	}
	blockY++;
	height = block[rotation].length;
	width = block[rotation][0].length;
	drawLanded();
	drawCurrentTetromino();
    }
    public void drawLanded () {
	for (int i = 0 ; i < 16 ; i++) {
	    for (int j = 0 ; j < 10 ; j++) {
		int num = landed [i] [j];
		if (num == 1) {
		    c.drawImage (blue, j * 30 + diff, i * 30 + diff, null);
		}
		else if (num == 2) {
		    c.drawImage (cyan, j * 30 + diff, i * 30 + diff, null);
		}
		else if (num == 3) {
		    c.drawImage (green, j * 30 + diff, i * 30 + diff, null);
		}
		else if (num == 4) {
		    c.drawImage (orange, j * 30 + diff, i * 30 + diff, null);
		}
		else if (num == 5) {
		    c.drawImage (purple, j * 30 + diff, i * 30 + diff, null);
		}
		else if (num == 6) {
		    c.drawImage (red, j * 30 + diff, i * 30 + diff, null);
		}
		else if (num == 7) {
		    c.drawImage (yellow, j * 30 + diff, i * 30 + diff, null);
		}
		else if (num == 0) {
		    c.drawImage (grey, j * 30 + diff, i * 30 + diff, null);
		}
	    }
	}
    }
    public void drawCurrentTetromino() {
	
	for (int i = 0; i < height; i++) {
	    for (int j = 0; j < width; j++) {
		int num = block[rotation][i][j];
		if (num == 1)
		{
		    c.drawImage (blue, (j+blockX) * 30 + diff, (i+blockY) * 30 + diff, null);
		}
		else if (num == 2)
		{
		    c.drawImage (cyan, (j+blockX) * 30 + diff, (i+blockY) * 30 + diff, null);
		}
		else if (num == 3)
		{
		    c.drawImage (green, (j+blockX) * 30 + diff, (i+blockY) * 30 + diff, null);
		}
		else if (num == 4)
		{
		    c.drawImage (orange, (j+blockX) * 30 + diff, (i+blockY) * 30 + diff, null);
		}
		else if (num == 5)
		{
		    c.drawImage (purple, (j+blockX) * 30 + diff, (i+blockY) * 30 + diff, null);
		}
		else if (num == 6)
		{
		    c.drawImage (red, (j+blockX) * 30 + diff, (i+blockY) * 30 + diff, null);
		}
		else if (num == 7)
		{
		    c.drawImage (yellow, (j+blockX) * 30 + diff, (i+blockY) * 30 + diff, null);
		}
	    }
	}
    }
    public void run() {
	blockY = 2;
	height = height;
	while(true) {
	    drop();
	}
    }
} // MoveDown class
