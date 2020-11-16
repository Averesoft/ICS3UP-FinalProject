//Jeremy Liang, Krish Patel
//11/02/20
//Mr. Guglielmi
//Key Press Thread
import java.awt.*;
import java.awt.image.*;
import hsa.Console;

public class KeyPress extends Thread
{
    Console c;           // The output console
    //x and y value of the block, rotation of the block
    int blockX, blockY, rotation;
    //the height and width of the block
    int height, width;
    //landed array and current tetromino
    int landed[][], block[][][];
    //block shift
    int diff = 10;
    BufferedImage cyan = null;
    BufferedImage blue = null;
    BufferedImage green = null;
    BufferedImage orange = null;
    BufferedImage purple = null;
    BufferedImage red = null;
    BufferedImage yellow = null;
    BufferedImage grey = null;  
    KeyPress(Console c) {
	this.c = c;
    }
    //drawing landed
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
    //drawing the current tetromino
    public void drawCurrentTetromino() {
	try {
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
	} catch (Exception e) {
	    Console con = new Console();
	    con.println("An error occurred");
	}
	
    }
    //black box return method
    private boolean checkValid(int w, int h, int landed[][], char direction) {
	boolean validity = true;
	if (direction == 'a') {
	    if (blockX <= 0) {
		validity = false;
	    } else {
		for (int i = blockY; i < blockY + h; i++) {
		    if (landed[i][blockX-1] != 0) {
			validity = false;
		    }
		}
	    }
	    
	} else if (direction == 'd') {
	    if (blockX + w >= 10) {
		validity = false;
	    } else {
		for (int i = blockY; i < blockY + h; i++) {
		    if (landed[i][blockX+w] != 0) {
			validity = false;
		    }
		}
	    }
	    
	}
	return validity;
    }
    public void run() {
	while(true) {
	    //getting keypress
	    char ch = c.getChar();
	    try{
		sleep(10);
	    } catch (Exception e) {
	    }
	    if (ch == 'a' && checkValid(width, height, landed, 'a')) {
		//if moving left is valid, then move
		blockX--;
		drawLanded();
		drawCurrentTetromino();
	    } else if (ch == 'd' && checkValid(width, height, landed, 'd')) {
		//if moving right is valid, then move
		blockX++;
		drawLanded();
		drawCurrentTetromino();
	    } else if (ch == 'w') {
		//check if the block is able to rotate by using temporary variables, if not then the blcok will not rotate
		boolean valid = true;
		int tempRotation = rotation+1; 
		tempRotation%= 4;
		int tempX = blockX; 
		int tempY = blockY; 
		int tempHeight = block[tempRotation].length; 
		int tempWidth = block[tempRotation][0].length;
		while(tempX + tempWidth > 10) {
		    tempX--;
		}
		if (tempY + tempHeight > 16) {
		    valid = false;
		} else {
		    for (int i = tempY; i < tempY + tempHeight; i++) {
			for (int j = tempX; j < tempX + tempWidth; j++) {
			    if (landed[i][j] != 0 && block[tempRotation][i-tempY][j-tempX] != 0) {
				valid = false;
			    }
			}
		    }
		}
		//if it is valid, rotate the block and redraw landed        
		if (valid) {
		    rotation++;
		    rotation%=4;        
		    height = block[rotation].length;
		    width = block[rotation][0].length;
		    while(blockX + width > 10) {
			blockX--;
		    } 
		}
		drawLanded();
		drawCurrentTetromino();
	    }
	}
    }
} // KeyPress class
