//Jeremy Liang, Krish Patel
//11/02/20
//Mr. Guglielmi
//Tetris: Final ISP
import java.awt.*;
import java.awt.image.*;
import java.applet.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import hsa.Console;
public class Tetris
{
    static Console c;
    //The tiles
    int blockX = 4, blockY = 1;
    int random;
    int[][][] block;
    int rotation = 1;
    int height, width;
    int diff = 10;
    int linesCleared = 0;
    BufferedImage cyan = null;
    BufferedImage blue = null;
    BufferedImage green = null;
    BufferedImage orange = null;
    BufferedImage purple = null;
    static BufferedImage red = null;
    BufferedImage yellow = null;
    BufferedImage grey = null;
    int landed[] [] = new int [16] [10];
    Tetromino tets[] = new Tetromino[7];
    public Tetris () {
	c = new Console (25, 60);
    }
    public void fillLanded () {
	for (int i = 0 ; i < 16 ; i++)
	{
	    for (int j = 0 ; j < 10 ; j++)
	    {
		landed [i] [j] = 0;
	    }
	}
    }
    
    public void drawOutline() {
	c.setColor(new Color(74, 74, 74));
	c.fillRoundRect(0, 0, 470, 16*30+20, 10, 10);
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
    

    public void loadTiles ()
    {

	try
	{
	    cyan = ImageIO.read (new File ("Cyan_Tile.png"));
	    blue = ImageIO.read (new File ("Blue_Tile.png"));
	    green = ImageIO.read (new File ("Green_Tile.png"));
	    orange = ImageIO.read (new File ("Orange_Tile.png"));
	    purple = ImageIO.read (new File ("Purple_Tile.png"));
	    red = ImageIO.read (new File ("Red_Tile.png"));
	    yellow = ImageIO.read (new File ("Yellow_Tile.png"));
	    grey = ImageIO.read (new File ("Grey_Tile.png"));
	}
	catch (Exception e)
	{
	}
    }
    public void fillTetrominos() {
	tets[0] = new Tetromino(new int[][][] {
	    {{2, 2, 2, 2}},
	    {{2},
	     {2},
	     {2},
	     {2}},
	    {{2, 2, 2, 2}},
	    {{2},
	     {2},
	     {2},
	     {2}}
	});
	tets[1] = new Tetromino(new int[][][] {
	    {{1, 1, 1}, 
	     {0, 0, 1}},
	    {{0, 1},
	     {0, 1},
	     {1, 1}},
	    {{1, 0, 0}, 
	     {1, 1, 1}},
	    {{1, 1},
	     {1, 0},
	     {1, 0}}
	});
	tets[2] = new Tetromino(new int[][][] {
	    {{4, 4, 4}, 
	     {4, 0, 0}},
	    {{4, 4},
	     {0, 4},
	     {0, 4}},
	    {{0, 0, 4}, 
	     {4, 4, 4}},
	    {{4, 0},
	     {4, 0},
	     {4, 4}}
	});
	tets[3] = new Tetromino(new int[][][] {
	    {{7, 7},
	     {7, 7}},
	    {{7, 7},
	     {7, 7}},
	    {{7, 7},
	     {7, 7}},
	    {{7, 7},
	     {7, 7}}
	});
	tets[4] = new Tetromino(new int[][][] {
	    {{0, 3, 3},
	     {3, 3, 0}},
	    {{3, 0},
	     {3, 3},
	     {0, 3}},
	    {{0, 3, 3},
	     {3, 3, 0}},
	    {{3, 0},
	     {3, 3},
	     {0, 3}}
	});
	tets[5] = new Tetromino(new int[][][] {
	    {{0, 5, 0},
	    {5, 5, 5}},
	    {{5, 0},
	     {5, 5},
	     {5, 0}},
	    {{5, 5, 5},
	     {0, 5, 0}},
	    {{0, 5},
	     {5, 5},
	     {0, 5}}
	});
	tets[6] = new Tetromino(new int[][][] {
	    {{6, 6, 0},
	    {0, 6, 6}},
	    {{0, 6},
	     {6, 6},
	     {6, 0}},
	    {{6, 6, 0},
	    {0, 6, 6}},
	    {{0, 6},
	     {6, 6},
	     {6, 0}}
	});
    }
    
    public void chooseBlock() {
	random = (int)Math.floor(Math.random()*7);
	block = tets[random].getArray();
	height = tets[random].getHeight(rotation);
	width = tets[random].getWidth(rotation);
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
    
    public static boolean hasLanded(int blockX, int blockY, int height, int width, int landed[][], int block[][][], int rotation) {
	boolean validity = false;
	if (blockY + height > 15) {
	    validity = true;    
	} else {
	    for (int i = blockY; i < blockY + height; i++) {
		for (int j = blockX; j < blockX + width; j++) {
		    if (landed[i+1][j] != 0 && block[rotation][i-blockY][j-blockX] != 0) {
			validity = true;
		    }
		}
	    }
	}
	
	return validity;
    }
    
    public void splashScreen()
    {
	boolean splashScreenDone = true;
	for(int i=0; i<18; i++){
	    c.clear();
	    try{ 
	    c.setColor(new Color(74, 74, 74));
	    c.fillRoundRect(0, 0, 500, 500, 10, 10);
	
	 for (int h = 0 ; h < 16 ; h++)
	 {
	     for (int j = 0 ; j < 15 ; j++)
		{
		c.drawImage (grey, j * 30 + diff, h * 30 + diff, null);
		}
	  }

	  if(i<=6){
	  //Drawing the R
	  c.drawImage(purple,(diff+90), (diff+30*(i+1)), null);
	  c.drawImage(purple,(diff+90), (diff+30*(i+2)), null);
	  c.drawImage(purple,(diff+90), (diff+30*(i+3)), null);
	  c.drawImage(purple,(diff+90), (diff+30*(i+4)), null);
	  c.drawImage(purple,(diff+90), (diff+30*(i+5)), null);
	  c.drawImage(purple,(diff+120), (diff+30*(i+1)), null);
	  c.drawImage(purple,(diff+120), (diff+30*(i+3)), null);
	  c.drawImage(purple,(diff+150), (diff+30*(i+2)), null);
	  c.drawImage(purple,(diff+150), (diff+30*(i+4)), null);
	  c.drawImage(purple,(diff+150), (diff+30*(i+5)), null);
	  //Drawing the I
	  c.drawImage(yellow,(diff+180), (diff+30*(i+5)), null);
	  c.drawImage(yellow,(diff+210), (diff+30*(i+5)), null);
	  c.drawImage(yellow,(diff+240), (diff+30*(i+5)), null);
	  c.drawImage(yellow,(diff+210), (diff+30*(i+4)), null);
	  c.drawImage(yellow,(diff+210), (diff+30*(i+3)), null);
	  c.drawImage(yellow,(diff+210), (diff+30*(i+2)), null);
	  c.drawImage(yellow,(diff+210), (diff+30*(i+1)), null);
	  c.drawImage(yellow,(diff+180), (diff+30*(i+1)), null);
	  c.drawImage(yellow,(diff+240), (diff+30*(i+1)), null);
	  //Drawing the S
	  c.drawImage(green,(diff+270), (diff+30*(i+1)), null);
	  c.drawImage(green,(diff+300), (diff+30*(i+1)), null);
	  c.drawImage(green,(diff+330), (diff+30*(i+1)), null);
	  c.drawImage(green,(diff+270), (diff+30*(i+2)), null);
	  c.drawImage(green,(diff+270), (diff+30*(i+3)), null);
	  c.drawImage(green,(diff+300), (diff+30*(i+3)), null);
	  c.drawImage(green,(diff+330), (diff+30*(i+3)), null);
	  c.drawImage(green,(diff+330), (diff+30*(i+4)), null);
	  c.drawImage(green,(diff+330), (diff+30*(i+5)), null);
	  c.drawImage(green,(diff+300), (diff+30*(i+5)), null);
	  c.drawImage(green,(diff+270), (diff+30*(i+5)), null); 
	  } else if (i>6){
	  //Drawing the R
	  c.drawImage(purple,(diff+90), (diff+30*(6+1)), null);
	  c.drawImage(purple,(diff+90), (diff+30*(6+2)), null);
	  c.drawImage(purple,(diff+90), (diff+30*(6+3)), null);
	  c.drawImage(purple,(diff+90), (diff+30*(6+4)), null);
	  c.drawImage(purple,(diff+90), (diff+30*(6+5)), null);
	  c.drawImage(purple,(diff+120), (diff+30*(6+1)), null);
	  c.drawImage(purple,(diff+120), (diff+30*(6+3)), null);
	  c.drawImage(purple,(diff+150), (diff+30*(6+2)), null);
	  c.drawImage(purple,(diff+150), (diff+30*(6+4)), null);
	  c.drawImage(purple,(diff+150), (diff+30*(6+5)), null);
	  //Drawing the I
	  c.drawImage(yellow,(diff+180), (diff+30*(6+5)), null);
	  c.drawImage(yellow,(diff+210), (diff+30*(6+5)), null);
	  c.drawImage(yellow,(diff+240), (diff+30*(6+5)), null);
	  c.drawImage(yellow,(diff+210), (diff+30*(6+4)), null);
	  c.drawImage(yellow,(diff+210), (diff+30*(6+3)), null);
	  c.drawImage(yellow,(diff+210), (diff+30*(6+2)), null);
	  c.drawImage(yellow,(diff+210), (diff+30*(6+1)), null);
	  c.drawImage(yellow,(diff+180), (diff+30*(6+1)), null);
	  c.drawImage(yellow,(diff+240), (diff+30*(6+1)), null);
	  //Drawing the S
	  c.drawImage(green,(diff+270), (diff+30*(6+1)), null);
	  c.drawImage(green,(diff+300), (diff+30*(6+1)), null);
	  c.drawImage(green,(diff+330), (diff+30*(6+1)), null);
	  c.drawImage(green,(diff+270), (diff+30*(6+2)), null);
	  c.drawImage(green,(diff+270), (diff+30*(6+3)), null);
	  c.drawImage(green,(diff+300), (diff+30*(6+3)), null);
	  c.drawImage(green,(diff+330), (diff+30*(6+3)), null);
	  c.drawImage(green,(diff+330), (diff+30*(6+4)), null);
	  c.drawImage(green,(diff+330), (diff+30*(6+5)), null);
	  c.drawImage(green,(diff+300), (diff+30*(6+5)), null);
	  c.drawImage(green,(diff+270), (diff+30*(6+5)), null);
	  }
	  if(i<=12){
	  //Drawing the T
	  c.drawImage(blue, (diff+90), (diff+30*(i-10)), null);
	  c.drawImage(blue, (diff+30+90), (diff+30*(i-10)), null);
	  c.drawImage(blue, (diff+60+90), (diff+30*(i-10)), null);
	  c.drawImage(blue, (diff+30+90), (diff+(30*((i-10)+1))),null);
	  c.drawImage(blue, (diff+30+90), (diff+(30*((i-10)+2))),null);
	  c.drawImage(blue, (diff+30+90), (diff+(30*((i-10)+3))),null);
	  c.drawImage(blue, (diff+30+90), (diff+(30*((i-10)+4))),null);
	  //Drawing the E
	  c.drawImage(orange, (diff+90+90), (diff+30*(i-10)), null);
	  c.drawImage(orange, (diff+120+90), (diff+30*(i-10)), null);
	  c.drawImage(orange, (diff+150+90), (diff+30*(i-10)), null);
	  c.drawImage(orange, (diff+90+90), (diff+(30*((i-10)+1))),null);
	  c.drawImage(orange, (diff+90+90), (diff+(30*((i-10)+2))),null);
	  c.drawImage(orange, (diff+90+90), (diff+(30*((i-10)+3))),null);
	  c.drawImage(orange, (diff+90+90), (diff+(30*((i-10)+4))),null);
	  c.drawImage(orange, (diff+120+90), (diff+(30*((i-10)+2))),null);
	  c.drawImage(orange, (diff+120+90), (diff+(30*((i-10)+4))),null);
	  c.drawImage(orange, (diff+150+90), (diff+(30*((i-10)+4))),null);
	  //Drawing the 2nd T
	  c.drawImage(red, (diff+270), (diff+30*(i-10)), null);
	  c.drawImage(red, (diff+300), (diff+30*(i-10)), null);
	  c.drawImage(red, (diff+330), (diff+30*(i-10)), null);
	  c.drawImage(red, (diff+300), (diff+(30*((i-10)+1))),null);
	  c.drawImage(red, (diff+300), (diff+(30*((i-10)+2))),null);
	  c.drawImage(red, (diff+300), (diff+(30*((i-10)+3))),null);
	  c.drawImage(red, (diff+300), (diff+(30*((i-10)+4))),null);
	  } else if (i>12)
	  {
	  c.drawImage(blue, (diff+90), (diff+30*2), null);
	  c.drawImage(blue, (diff+30+90), (diff+30*2), null);
	  c.drawImage(blue, (diff+60+90), (diff+30*2), null);
	  c.drawImage(blue, (diff+30+90), (diff+(30*(2+1))),null);
	  c.drawImage(blue, (diff+30+90), (diff+(30*(2+2))),null);
	  c.drawImage(blue, (diff+30+90), (diff+(30*(2+3))),null);
	  c.drawImage(blue, (diff+30+90), (diff+(30*(2+4))),null);
	  //Drawing the E
	  c.drawImage(orange, (diff+90+90), (diff+30*2), null);
	  c.drawImage(orange, (diff+120+90), (diff+30*2), null);
	  c.drawImage(orange, (diff+150+90), (diff+30*2), null);
	  c.drawImage(orange, (diff+90+90), (diff+(30*(2+1))),null);
	  c.drawImage(orange, (diff+90+90), (diff+(30*(2+2))),null);
	  c.drawImage(orange, (diff+90+90), (diff+(30*(2+3))),null);
	  c.drawImage(orange, (diff+90+90), (diff+(30*(2+4))),null);
	  c.drawImage(orange, (diff+120+90), (diff+(30*(2+2))),null);
	  c.drawImage(orange, (diff+120+90), (diff+(30*(2+4))),null);
	  c.drawImage(orange, (diff+150+90), (diff+(30*(2+4))),null);
	  //Drawing the 2nd T
	  c.drawImage(red, (diff+270), (diff+30*2), null);
	  c.drawImage(red, (diff+300), (diff+30*2), null);
	  c.drawImage(red, (diff+330), (diff+30*2), null);
	  c.drawImage(red, (diff+300), (diff+(30*(2+1))),null);
	  c.drawImage(red, (diff+300), (diff+(30*(2+2))),null);
	  c.drawImage(red, (diff+300), (diff+(30*(2+3))),null);
	  c.drawImage(red, (diff+300), (diff+(30*(2+4))),null);
	  }
	  c.setColor(new Color(255,255,255));
	  c.drawString("Tetris, By: Jeremy Liang and Krish Patel, Date: November 16, 2020",70,390);
	  
	Thread.sleep(500);        
	}
	catch(Exception e){}
	}
    }
    
    public static void main (String[] args)
    {
	
	Tetris t = new Tetris ();
	t.loadTiles ();        
	//t.splashScreen();  
	MoveDown md = new MoveDown(c);
	KeyPress kp = new KeyPress(c);
	t.loadTiles ();
	t.fillLanded ();
	t.drawOutline();
	t.fillTetrominos();
	md.start();
	kp.start();
	boolean land = false;
	t.chooseBlock();
	kp.blockX = t.blockX;
	kp.blockY = t.blockY;
	kp.rotation = t.rotation;
	kp.height = t.height;
	kp.width = t.width;
	kp.cyan = t.cyan;
	kp.blue = t.blue;
	kp.green = t.green;
	kp.orange = t.orange;
	kp.purple = t.purple;
	kp.red = t.red;
	kp.yellow = t.yellow;
	kp.grey = t.grey;
	kp.block = t.block;
	kp.landed = t.landed;
	
	md.blockX = t.blockX;
	md.blockY = t.blockY;
	md.rotation = t.rotation;
	md.height = t.height;
	md.width = t.width;
	md.cyan = t.cyan;
	md.blue = t.blue;
	md.green = t.green;
	md.orange = t.orange;
	md.purple = t.purple;
	md.red = t.red;
	md.yellow = t.yellow;
	md.grey = t.grey;
	md.block = t.block;
	md.landed = t.landed;
	md.linesCleared = t.linesCleared;
	t.drawLanded ();
	t.drawCurrentTetromino();
	while(true) {
	    
	    
	    kp.blockY = md.blockY;
	    t.rotation = kp.rotation;
	    t.rotation%=4;
	    t.height = t.tets[t.random].getHeight(t.rotation);
	    t.width = t.tets[t.random].getWidth(t.rotation);
	    t.height = kp.height;
	    t.width = kp.width;
	    md.blockX = kp.blockX;
	    md.rotation = kp.rotation;
	    md.height = kp.height;
	    md.width = kp.width;
	    t.blockY = md.blockY;
	    t.blockX = kp.blockX;
	    land = hasLanded(t.blockX, t.blockY, t.height, t.width, t.landed, t.block, t.rotation );
	    if (land) {
		if (t.blockY == 1) {
		    System.exit(0);
		}
		t.blockX = kp.blockX;
		t.blockY = md.blockY;
		for (int i = t.blockY; i < t.blockY+t.height; i++) {
		    for (int j = t.blockX; j < t.blockX+t.width; j++) {
			if (t.block[t.rotation][i-t.blockY][j-t.blockX]!= 0) {
			    t.landed[i][j] = t.block[t.rotation][i-t.blockY][j-t.blockX];
			}
		    }
		}
		t.chooseBlock();
		t.blockX = 4; 
		t.blockY = 1;
		kp.block = t.block;
		kp.blockX = t.blockX;
		kp.blockY = t.blockY;
		kp.rotation = t.rotation;
		kp.height = t.height;
		kp.width = t.width;
		kp.landed = t.landed;
		
		md.block = t.block;
		md.blockX = t.blockX;
		md.blockY = t.blockY;
		md.rotation = t.rotation;
		md.height = t.height;
		md.width = t.width;
		md.landed = t.landed;
	    }
	    
	    md.landed = t.landed;
	    kp.landed = t.landed;
	    boolean checkLine;
	    int temp[][] = new int [16][10];
	    for (int i = 0; i < 16; i++) {
		checkLine = true;
		for (int j = 0; j < 10; j++) {
		    if (t.landed[i][j] == 0) {
			checkLine = false;
		    }
		}
		if(checkLine) {
		    for (int j = 0; j < 10; j++) {
			temp[0][j] = 0;
		    }
		    for (int b = 1; b <= i; b++) { 
			for (int j = 0; j < 10; j++) {
			    temp[b][j] = t.landed[b-1][j];
			}
		    } 
		    for (int b = 1; b <= i; b++) {
			for (int j = 0; j < 10; j++) {
			    t.landed[b][j] = temp[b][j];
			}
		    }
		    t.linesCleared++; 
		   
		}
	    }
	    md.linesCleared = t.linesCleared;
	    
	}
	// Place your program here.  'c' is the output console
    } // main method
} // Tetris class
