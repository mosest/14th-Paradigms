import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class Tube extends Sprite {
	// Coordinates
	int retraction_speed;
	
	// Image stuff
	boolean facing_up;
	boolean is_retracting;
	static Image tube_facingdown = null;
	static Image tube_facingup = null;

	// -----------------------------------------------------------------------------
	// Constructors
	// -----------------------------------------------------------------------------
	
	Tube(int x, int y, int random1) throws IOException {
		// Call super constructor
		super(x,y);
		if (random1 == 0) this.y = 500 - this.y;
		
		// Initialize variables!
		this.v_x = 3;
		this.width = 44;
		this.facing_up = true;
		this.is_retracting = false;
		this.retraction_speed = 10;

		// Initialize tube images
		if (tube_facingdown == null) tube_facingdown = ImageIO.read(new File("tubedown.png"));
		if (tube_facingup == null) tube_facingup = ImageIO.read(new File("tubeup.png"));
		
		// Randomly decide whether the tube faces up or down
		if (random1 == 0) facing_up = true;
		else facing_up = false;
		
		// Set tube image
		if (facing_up) this.image = tube_facingup;
		else this.image = tube_facingdown;	
	}
	
	Tube(Tube orig) throws IOException {
		// Call super constructor
		super(orig.x,orig.y);
		
		// Initialize variables!
		this.v_x = orig.v_x;
		this.width = orig.width;
		this.facing_up = orig.facing_up;
		this.is_retracting = orig.is_retracting;
		this.retraction_speed = orig.retraction_speed;
		this.is_dead = orig.is_dead;
		
		// Set tube image
		this.image = orig.image;
	}

	// -----------------------------------------------------------------------------
	// Functions
	// -----------------------------------------------------------------------------
	
	public void update() {
		// Move tube a little to the left
		x -= v_x;
		
		// Handle retracting
		if (is_retracting) {
			if (facing_up) y += retraction_speed;
			else y -= retraction_speed;
		}
		
		// If tube goes off-screen, it's functionally dead
		if (x < (-1 * (width + 10))) is_dead = true; // if tube goes to left too far		
		if (this.y < 0 || this.y > 500) this.is_dead = true; // if tube retracts too far
	}
	
	public void draw(Graphics g) {
		if (facing_up) g.drawImage(image, x, y, null);
		else g.drawImage(image, x, y - 400, null);
	}
	
	public void game_over() {
		is_dead = true;
	}
}