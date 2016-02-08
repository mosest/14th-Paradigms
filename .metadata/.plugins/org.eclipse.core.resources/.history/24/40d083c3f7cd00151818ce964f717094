import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class Pie extends Sprite {
	// Coordinate variables
	int v_x;
	float v_y;
	int height;
	
	// Image variables
	Image image;
	Image pie_slice = null;
	
	Pie(int x, int y) throws IOException {
		// Call super constructor
		super(x,y);
		
		// Initialize things!
		this.pie_slice = ImageIO.read(new File("pie.png"));
		this.v_x = 12;
		this.v_y = -12;
		this.height = 28;
		
		// Set pie image
		image = pie_slice;
	}
	
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
	}
	
	public void game_over() {
		is_dead = true;
	}
}