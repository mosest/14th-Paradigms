import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

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
	Image bird_wingdown = null;
	Image bird_wingup = null;
	Image bird_dead = null;
	
	// Other
	LinkedList<Sprite> sprites; // for collision detection
	
	Bird(int x, int y, LinkedList<Sprite> s) throws IOException {
		// Call super constructor
		super(x,y);
		
		// Initialize variables!
		this.width = 64;
		this.height = 57;
		this.x = 250 - (width / 2); // middle of the screen
		this.v_y = 10;
		this.jump_power = -15;
		this.sprites = s;

		// Initialize bird images
		bird_wingdown = ImageIO.read(new File("bird-wingdown.png"));
		bird_wingup = ImageIO.read(new File("bird-wingup.png"));
		bird_dead = ImageIO.read(new File("bird-dead.png"));
		
		// Set bird image
		this.image = bird_wingdown;
	}

	public void update() {
		// Simulate gravity (origin is in top-left of window!)
		v_y += 0.8;
		y += v_y;
		
		// Collision detection
		if (collision_detection()) {
			image = bird_dead;
			is_dead = true;
		}
		
		// If bird just tried to jump, start counting frames
		if (just_jumped) frame_count++;
		
		// After 4 frames, change bird image back
		if (frame_count == 4) {
			frame_count = 0;
			just_jumped = false;
			image = bird_wingdown;
		}
		
		// What happens when the bird explodes??
		if (is_dead) {
			x += 5;
			if (y >= 500 - height) v_y = jump_power;
			if (x >= 500 + width) System.exit(0); // close window if bird bounces/exits stage-left
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
	
	public boolean collision_detection() {
		for (Sprite current_sprite : sprites) {
			if (current_sprite.getClass().equals(Tube.class)) {
				Tube current_tube = (Tube)current_sprite;
				if (current_tube.facing_up) {
					if (x + width > current_tube.x && 				// bird's right edge is to the right of the tube's left edge
						x < current_tube.x + current_tube.width && 	// bird's left edge is to the left of the tube's right edge
						y + height > current_tube.y) { 				// bird's bottom edge is below the tube's entrance
							return true;
						}
				} else {
					if (x + width > current_tube.x &&				// same as top
						x < current_tube.x + current_tube.width &&	// same as top
						y < current_tube.y) {						// bird's top edge is above the tube's entrance
							return true;
						}
				}
			}
		}
		return false;
	}
	
	public void game_over() {
		is_dead = true;
	}
}