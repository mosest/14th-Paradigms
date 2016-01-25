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
		
		if (facing_up) this.tube_image = ImageIO.read(new File("tubeup.png"));
		else this.tube_image = ImageIO.read(new File("tubedown.png"));
	}
	
	public void update() {
		// Move tube a little to the left
		tube_x -= 5;
		
		// If tube goes off-screen to the left,
		// put it a little off-screen to the right
		if (tube_x < -44) tube_x = 501;
	}
}