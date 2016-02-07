import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class Tube extends Sprite {
	// Coordinates
	int v_x;
	int width;
	
	// Image stuff
	boolean facing_up;
	Image image;
	Image tube_facingdown;
	Image tube_facingup;
	
	Tube(int x, int y, int random1) throws IOException {
		// Call super constructor
		super(x,y);
		
		// Initialize variables!
		this.v_x = 3;
		this.width = 44;
		this.facing_up = true;

		// Initialize tube images
		tube_facingdown = ImageIO.read(new File("tubedown.png"));
		tube_facingup = ImageIO.read(new File("tubeup.png"));
		
		// Randomly decide whether the tube faces up or down
		if (random1 == 0) facing_up = true;
		else facing_up = false;
		
		// Set tube image
		if (facing_up) this.image = tube_facingup;
		else this.image = tube_facingdown;	
	}
	
	public void update() {
		// Move tube a little to the left
		x -= v_x;
		
		// If tube goes off-screen to the left,
		// GET RID OF IT
		if (x < -44) { // tube sprite is 44 pixels wide
			is_dead = true; 
		}
	}
	
	public void draw(Graphics g) {
		if (facing_up) g.drawImage(image, x, y, null);
		else g.drawImage(image, x, y - 400, null);
	}
	
	public void game_over() {
		is_dead = true;
	}
}