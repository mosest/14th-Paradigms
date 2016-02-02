import java.awt.Image;
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
	boolean is_dead;
	
	Tube(int x, int y, int random1) throws IOException {
		super(x,y);
		this.v_x = 3;
		this.width = 44;
		this.facing_up = true;
		this.is_dead = false;
		
		// Randomly decide whether the tube faces up or down
		if (random1 == 0) facing_up = true;
		else facing_up = false;
		
		// Reset the tube sprite if you need to
		if (facing_up) this.image = ImageIO.read(new File("tubeup.png"));
		else this.image = ImageIO.read(new File("tubedown.png"));	
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
	
	public void gameOver() {
		x = -500;
		y = -500;
		
		v_x = 0;
	}
}