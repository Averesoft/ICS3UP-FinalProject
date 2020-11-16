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
import javax.sound.sampled.*;
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
    BufferedImage red = null;
    BufferedImage yellow = null;
    BufferedImage grey = null;
    BufferedImage splashScreenBackground = null;
    int landed[] [] = new int [16] [10];
    Tetromino tets[] = new Tetromino[7];
    boolean mainMenuExit = false;
    boolean land = false;
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
	    splashScreenBackground = ImageIO.read (new File ("splashScreenBackground.png"));
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
	    try {
		for (int i = blockY; i < blockY + height; i++) {
		    for (int j = blockX; j < blockX + width; j++) {
			if (landed[i+1][j] != 0 && block[rotation][i-blockY][j-blockX] != 0) {
			    validity = true;
			}
		    }
		}
	    } catch (Exception e) {
		Console con = new Console();
		con.println("Please do not spam buttons, as it may cause problems.");
	    }
	     
	}
	
	return validity;
    }
    
    public void splashScreen()
    {
	boolean splashScreenDone = true;
	for(int i=0; i<18; i++){
	    
	    try{
	    
	    c.drawImage(splashScreenBackground, 0, 0, null);

	  if (i <= 6) {
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
	  } else if ( i > 6 ){
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
	  if (i <= 12) {
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
	  } else if (i>12) {
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
    
    public void mainMenu ()
    {
	c.drawImage (splashScreenBackground, 0, 0, null);
	c.setColor (Color.white);
	c.setFont (new Font ("Verdana", 0, 50));
	c.drawString ("Tetris", 162, diff + 50);
	c.setFont (new Font ("Verdana", 0, 16));
	c.drawString ("By: Jeremy Liang and Krish Patel", 122, diff + 80);

	int select = 0; //Method of getting Main Menu Input was given by the wonderful Bernie Chen
	char choice = 'x';
	c.setFont (new Font ("Verdana", 0, 24));
	while (choice != ' ') {
	    c.setColor (Color.white);
	    if (select == 0) {
		c.setColor (Color.yellow);
	    }
	    c.drawString ("Enter Game", 165, diff + 185);
	    c.setColor (Color.white);
	    if (select == 1) {
		c.setColor (Color.yellow);
	    }
	    c.drawString ("Instructions", 165, diff + 275);
	    c.setColor (Color.white);
	    if (select == 2) {
		c.setColor (Color.yellow);
	    }
	    c.drawString ("Exit", 215, diff + 365);
	    choice = c.getChar ();
	    if (choice == 's') {
		++select;
	    } else if (choice == 'w') {
		--select;
	    }
		
	    select = (select + 3) % 3;
	}
	if (select == 0) {
	    mainMenuExit = true;
	}
	else if (select == 1) {
	    instructions ();
	}
	else if (select == 2) {
	    goodbye ();
	}
    }
    public void goodbye() {
	c.drawImage(splashScreenBackground, 0 , 0, null);
	c.setColor(Color.white);
	c.drawString("Thanks for playing our game!" ,diff+50 ,diff+205 );
	c.drawString("Please press any key to exit." ,diff+50 ,diff+262 );
	c.getChar();
	System.exit(0);
    }
    public void lost() {
	c.drawImage(splashScreenBackground, 0 , 0, null);
	c.setColor(Color.white);
	c.setFont (new Font ("Verdana", 0, 16));
	c.drawString("You lost! You cleared " + linesCleared + " lines!",diff+125 ,diff+205 );
	c.drawString("Please press any key to proceed to exit screen." ,diff+50 ,diff+262 );
	c.getChar();
	c.setFont (new Font ("Verdana", 0, 24));
    }
    public void instructions() {
	c.drawImage(splashScreenBackground, 0 , 0, null);
	c.setFont(new Font("Verdana", 1, 12));
	c.drawString("The goal of Tetris is to stay alive as long as possible by getting as ", 20, 30);
	c.drawString("many rows of blocks as possible. You can press A, and D to move" ,20 ,60 );
	c.drawString("the piece left and right. You can press W to turn the block. " ,20 ,90 );
	c.drawString("Pressing C means you can hold a block, this essentially stores", 20, 120);
	c.drawString("a block to be used later. If there is a block in hold, they swap.", 20, 150);
	c.drawString("If you are incapable of placing anymore blocks the game ends.", 20, 210);
	c.drawString("Please press any key to continue back to main menu.", 20, 240);
	c.getChar();
    }
    
    
    public static void main (String[] args)
    {
	
	Tetris t = new Tetris ();
	//loading in the images
	t.loadTiles ();        
	//splash screen
	t.splashScreen();
	//main menu, will keep looping until it is asked to exit
	while(!t.mainMenuExit) {
	    t.mainMenu();
	}  
	//thread to move piece down
	MoveDown md = new MoveDown(c);
	//thread to move piece left and right and to rotate piece
	KeyPress kp = new KeyPress(c);
	//filling the landed array with 0s
	t.fillLanded ();
	//drawing the outline around the blocks
	t.drawOutline();
	//filling the 4d array with its respective blocks and rotations
	t.fillTetrominos();
	//starting the two threads when the games is started
	md.start();
	kp.start();
	
	
	//choosing the first block
	t.chooseBlock();
	//initializing values in the thread for the block
	kp.blockX = t.blockX;
	kp.blockY = t.blockY;
	kp.rotation = t.rotation;
	kp.height = t.height;
	kp.width = t.width;
	
	md.blockX = t.blockX;
	md.blockY = t.blockY;
	md.rotation = t.rotation;
	md.height = t.height;
	md.width = t.width;
	
	//initializing images and arrays in the two threads
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
	//making lines cleared the same
	md.linesCleared = t.linesCleared;
	//drawing the landed array and tetromino at the beginning
	t.drawLanded ();
	t.drawCurrentTetromino();
	//Tetris Running
	while(true) {
	    //Constantly making sure that the blockY variables will be the same, because moveDown will be moving down every 500-20*linesCleared seconds
	    kp.blockY = md.blockY;
	    //making sure all other values are the same too
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
	    md.rotation%=4;
	    t.blockY = md.blockY;
	    t.blockX = kp.blockX;
	    //checking if the current block has landed
	    t.land = hasLanded(t.blockX, t.blockY, t.height, t.width, t.landed, t.block, t.rotation );
	    //If the block has landed, then we must change the block by choosing a new one
	    if (t.land) {
		if (t.blockY == 1) {
		    md.stop();
		    kp.stop();
		    t.lost();
		    t.goodbye();
		}
		t.blockX = kp.blockX;
		t.blockY = md.blockY;
		//since the block has landed, we must add it to the landed array
		for (int i = t.blockY; i < t.blockY+t.height; i++) {
		    for (int j = t.blockX; j < t.blockX+t.width; j++) {
			if (t.block[t.rotation][i-t.blockY][j-t.blockX]!= 0) {
			    t.landed[i][j] = t.block[t.rotation][i-t.blockY][j-t.blockX];
			}
		    }
		}
		
		//choosing a new block and giving all respective thread variables the right values
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
	    //checking if a line has been filled
	    boolean checkLine;
	    int temp[][] = new int [16][10];
	    for (int i = 0; i < 16; i++) {
		checkLine = true;
		for (int j = 0; j < 10; j++) {
		    if (t.landed[i][j] == 0) {
			checkLine = false;
		    }
		}
		//if a line has been filled, clear the line and shift everything down
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
