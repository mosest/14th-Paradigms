import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class Bird {
	// Coordinates and speed
	int bird_y;	
	int bird_x;
	double velocity_y;
	
	// Image stuff (wingup + wingdown)
	Image bird_image;
	int frame_count = 0;
	boolean just_jumped = false;
	
	Bird() throws IOException {
		this.bird_x = 225; // middle of the screen
		this.velocity_y = 10;

		this.bird_image = ImageIO.read(new File("bird-wingdown.png"));
	}

	public void update() throws IOException {
		// Simulate gravity (origin is in top-left of window!)
		velocity_y += 0.8;
		bird_y += velocity_y;
		
		// If bird just tried to jump, start counting frames
		if (just_jumped) frame_count++;
		
		// Change bird image to wingdown after 4 frames
		if (frame_count == 4) {
			bird_image = ImageIO.read(new File("bird-wingdown.png"));
			frame_count = 0;
			just_jumped = false;
		}
	}
	
	public void flap() throws IOException {
		// Make bird jump up a little
		velocity_y = -15; // going towards the top of the window (jumping)
		
		// Change bird image to look like it's flapping
		bird_image = ImageIO.read(new File("bird-wingup.png"));
		just_jumped = true;
	}
}