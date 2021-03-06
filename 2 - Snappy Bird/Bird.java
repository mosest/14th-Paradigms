import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class Bird {
	// Coordinates and speed
	int x;	
	int y;
	int width;
	int height;
	double v_y;
	double jump_power;
	
	// Image stuff (wingup + wingdown)
	Image image;
	int frame_count = 0;
	boolean just_jumped = false;
	
	Bird() throws IOException {
		this.width = 64;
		this.height = 57;
		this.x = 250 - (width / 2); // middle of the screen
		this.v_y = 10;
		this.jump_power = -15;

		this.image = ImageIO.read(new File("bird-wingdown.png"));
	}

	public void update() throws IOException {
		// Simulate gravity (origin is in top-left of window!)
		v_y += 0.8;
		y += v_y;
		
		// If bird just tried to jump, start counting frames
		if (just_jumped) frame_count++;
		
		// Change bird image to wingdown after 4 frames
		if (frame_count == 4) {
			image = ImageIO.read(new File("bird-wingdown.png"));
			frame_count = 0;
			just_jumped = false;
		}
	}
	
	public void flap() throws IOException {
		// Make bird jump up a little
		v_y = jump_power; // jump_power < 0. going towards the top of the window
		
		// Change bird image to look like it's flapping
		image = ImageIO.read(new File("bird-wingup.png"));
		just_jumped = true;
	}
	
	public void gameOver() {
		jump_power = v_y;
	}
}