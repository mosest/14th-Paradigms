import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class Tube {
	// Coordinates
	int x;
	int y;
	int v_x;
	int width;
	
	// Image stuff
	boolean facing_up;
	Image image;
	
	Tube(int x, int y) throws IOException {
		this.x = x;
		this.y = y;
		this.v_x = 3;
		this.width = 44;
		this.facing_up = true;

		this.image = ImageIO.read(new File("tubeup.png"));
	}
	
	public void update(int random1, int random2) throws IOException {
		// Move tube a little to the left
		x -= v_x;
		
		// If tube goes off-screen to the left,
		// put it a little off-screen to the right
		if (x < -44) { // tube sprite is ~44 pixels wide
			x = 501; 
			
			// Randomly decide whether the tube faces up or down
			if (random2 == 0) facing_up = true;
			else facing_up = false;
			
			// Reset the tube sprite if you need to
			if (facing_up) this.image = ImageIO.read(new File("tubeup.png"));
			else this.image = ImageIO.read(new File("tubedown.png"));
			
			// Create random height of tube
			y = random1; 
		}
	}
	
	public void gameOver() {
		x = -500;
		y = -500;
		
		v_x = 0;
	}
}