import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Iterator;

import javax.imageio.ImageIO;

class Bird extends Sprite {
	// Actions
	public static enum Action {
		DO_NOTHING, FLAP, FLAP_AND_THROW_PIE, THROW_PIE
	}
	
	Action current_action;
	
	// Coordinates and speed
	float jump_power;
	
	// Image stuff (wingup + wingdown)
	int frame_count = 0;
	boolean just_jumped = false;
	static Image bird_wingdown = null;
	static Image bird_wingup = null;
	static Image bird_dead = null;
	
	// Other
	LinkedList<Sprite> sprites; // for collision detection
	boolean is_a_clone;

	// -----------------------------------------------------------------------------
	// Constructors
	// -----------------------------------------------------------------------------
	
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
		this.is_a_clone = false;

		// Initialize bird images
		if (bird_wingdown == null) bird_wingdown = ImageIO.read(new File("bird-wingdown.png"));
		if (bird_wingup == null) bird_wingup = ImageIO.read(new File("bird-wingup.png"));
		if (bird_dead == null) bird_dead = ImageIO.read(new File("bird-dead.png"));
		
		// Set bird image
		this.image = bird_wingdown;
		
		// Set current_action to do_nothing
		this.current_action = Action.DO_NOTHING;
	}
	
	Bird(Bird orig, Model copyModel) throws IOException {
		
		// Call super constructor
		super(orig.x,orig.y);
		
		// Initialize variables!
		this.width = orig.width;
		this.height = orig.height;
		this.x = orig.x;
		this.v_y = orig.v_y;
		this.jump_power = orig.jump_power;
		this.sprites = copyModel.sprite_list;
		this.is_a_clone = true;
		this.is_dead = orig.is_dead;
		
		// Set bird image
		this.image = orig.image;
		
		// Set current_action
		this.current_action = orig.current_action;
	}

	// -----------------------------------------------------------------------------
	// Functions
	// -----------------------------------------------------------------------------

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
			if (x >= 500 + width) System.exit(0); // close window if bird exits stage-left
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
		Iterator<Sprite> iterator = sprites.iterator();
		
		while (iterator.hasNext()) {
			Sprite current_sprite = iterator.next();
			
			if (current_sprite.getClass().equals(Tube.class)) {
				Tube current_tube = (Tube)current_sprite;
				if (current_tube.facing_up) {
					if (!this.is_dead &&
						x + width > current_tube.x && 				// bird's right edge is to the right of the tube's left edge
						x < current_tube.x + current_tube.width && 	// bird's left edge is to the left of the tube's right edge
						y + height > current_tube.y) { 				// bird's bottom edge is below the tube's entrance
							return true;
						}
				} else {
					if (!this.is_dead &&
						x + width > current_tube.x &&				// same as top
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