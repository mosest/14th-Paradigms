import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class Tube {
	// Coordinates
	int tube_x;
	int tube_y;
	
	// Image stuff
	boolean facing_up;
	Image tube_image;
	
	Tube(int x, int y) throws IOException {
		this.tube_x = x;
		this.tube_y = y;
		this.facing_up = true;

		this.tube_image = ImageIO.read(new File("tubeup.png"));
	}
	
	public void update(int random1, int random2) throws IOException {
		// Move tube a little to the left
		tube_x -= 5;
		
		// If tube goes off-screen to the left,
		// put it a little off-screen to the right
		if (tube_x < -44) { // tube sprite is ~44 pixels wide
			tube_x = 501; 
			
			// Randomly decide whether the tube faces up or down
			if (random2 == 0) facing_up = true;
			else facing_up = false;
			
			// Reset the tube sprite if you need to
			if (facing_up) this.tube_image = ImageIO.read(new File("tubeup.png"));
			else this.tube_image = ImageIO.read(new File("tubedown.png"));
			
			// Create random height of tube
			tube_y = random1;
			
			System.out.println("random1 = " + random1 + ", height = " + tube_y);
		}
		
		
	}
}