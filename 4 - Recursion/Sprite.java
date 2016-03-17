import java.awt.Graphics;
import java.awt.Image;

abstract class Sprite {
	// All sprites have a position!
	int x;
	int y;
	float v_x;
	float v_y;
	
	// All sprites have an image!
	int width;
	int height;
	Image image;
	
	// All sprites are susceptible to death
	boolean is_dead;
	
	Sprite(int x, int y) {
		// Initialize coordinates
		this.x = x;
		this.y = y;
		this.v_x = 0;
		this.v_y = 0;
		
		// Initialize width, height
		this.width = 0;
		this.height = 0;
		
		// All sprites start out not dead :D
		is_dead = false; 
	}
	
	public abstract void update();
	
	public abstract void draw(Graphics g);
	
	public abstract void game_over();
}