// The "CheckInput" class.
import java.awt.*;
import hsa.Console;

public class CheckInput extends Thread
{
    Console c;           // The output console
    boolean rotated = false;
    public void input() {
	char ch = c.getChar();
	if (ch == 'w') {
	    rotated = true;
	    ch = ' ';
	} else {
	    rotated = false;
	}
    }
    public CheckInput(Console con) {
	c = con;
    }
    public boolean returnRotated() {
	return rotated;
    }
    public void run() {
	input();
    }
    
} // CheckInput class
