import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class Bird extends Sprite {
	// Coordinates and speed
	int width;
	int height;
	double v_y;
	double jump_power;
	
	// Image stuff (wingup + wingdown)
	Image image;
	int frame_count = 0;
	boolean just_jumped = false;
	Image bird_wingdown;
	Image bird_wingup;
	
	Bird(int x, int y) {
		super(x,y);
		this.width = 64;
		this.height = 57;
		this.x = 250 - (width / 2); // middle of the screen
		this.v_y = 10;
		this.jump_power = -15;

		this.image = bird_wingdown;
	}

	public void update() {
		// Simulate gravity (origin is in top-left of window!)
		v_y += 0.8;
		y += v_y;
		
		// If bird just tried to jump, start counting frames
		if (just_jumped) frame_count++;
		
		// After 4 frames, change bird image back
		if (frame_count == 4) {
			frame_count = 0;
			just_jumped = false;
			image = bird_wingdown;
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}
	
	public void flap() {
		// Make bird jump up a little
		v_y = jump_power; // jump_power < 0. going towards the top of the window
		
		// Change bird image to look like it's flapping
		image = bird_wingup;
		just_jumped = true;
	}
	
	public void gameOver() {
		jump_power = v_y;
	}
}