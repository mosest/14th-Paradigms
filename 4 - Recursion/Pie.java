import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageIO;

class Pie extends Sprite {
	// Image variables
	Image pie_slice = null;
	
	// Other
	LinkedList<Sprite> sprites; // for collision detection
	boolean already_hit_something;

	// -----------------------------------------------------------------------------
	// Constructors
	// -----------------------------------------------------------------------------
	
	Pie(int x, int y, LinkedList<Sprite> s) throws IOException {
		// Call super constructor
		super(x,y);// Initialize coordinates
		this.v_x = 12;
		this.v_y = -12;
		this.height = 28;
		this.width = 40;
		this.sprites = s;
		
		// Initialize images!
		this.pie_slice = ImageIO.read(new File("pie.png"));
		
		// Set pie image
		image = pie_slice;
	}
	
	Pie(Pie orig, Model copyModel) throws IOException {
		super(orig.x,orig.y);
		this.v_x = orig.v_x;
		this.v_y = orig.v_y;
		this.height = orig.height;
		this.width = orig.width;
		this.sprites = copyModel.sprite_list;
		
		// Initialize images!
		this.pie_slice = orig.pie_slice;
		
		// Set pie image
		image = orig.image;
	}

	// -----------------------------------------------------------------------------
	// Functions
	// -----------------------------------------------------------------------------
	
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}
	
	public void update() {
		// Move the pie!
		x += v_x;
		y += v_y;
		
		// Make pie fall down
		v_y += 1.3;
		
		// If pie hits bottom of screen, get rid of it
		if (y > 500 + height) is_dead = true;
				
		// Collision detection
		if (!already_hit_something) collision_detection();
	}
	
	public void collision_detection() {
		Iterator<Sprite> iterator = sprites.iterator();
		
		while (iterator.hasNext()) {
			Sprite current_sprite = iterator.next();
			
			if (current_sprite.getClass().equals(Tube.class)) {
				
				// Create a variable that iterates over each tube_sprite!
				Tube current_tube = (Tube)current_sprite;
				
				// Check if pie and tube overlap!
				if (current_tube.facing_up) {
					if (x + width > current_tube.x && 				// pie's right edge is to the right of the tube's left edge
						x < current_tube.x + current_tube.width && 	// pie's left edge is to the left of the tube's right edge
						y + height > current_tube.y) { 				// pie's bottom edge is below the tube's entrance
							current_tube.is_retracting = true;
							this.is_dead = true;
						}
				} else {
					if (x + width > current_tube.x &&				// same as top
						x < current_tube.x + current_tube.width &&	// same as top
						y < current_tube.y) {						// pie's top edge is above the tube's entrance
							current_tube.is_retracting = true;
							this.is_dead = true;
						}
				}
			}
		}
	}
	
	public void game_over() {
		is_dead = true;
	}
}