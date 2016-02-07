import java.awt.Graphics;

abstract class Sprite {
	// Variables
	int x;
	int y;
	String image_name1;
	String image_name2;
	boolean is_dead;
	
	Sprite(int x, int y) {
		// Initialize variables
		this.x = x;
		this.y = y;
		is_dead = false; // All sprites start out not dead :D
	}
	
	public abstract void update();
	
	public abstract void draw(Graphics g);
	
	public abstract void game_over();
}