import java.awt.Color;
import java.awt.Graphics;

public abstract class Sprite{
	
	int x;
	int y;
	
	abstract boolean update();
	
	abstract void draw(Graphics g);
	
	abstract Sprite replicate(Model m) throws Exception;

}
