import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Tube extends Sprite implements Cloneable{
	
	Random rand;
	int tube_w, tube_h;
	static BufferedImage tube_up;
	static BufferedImage tube_down;
	boolean isRetracting = false;
	boolean directionUp; 
	int count;
	
	Tube(Random r) throws Exception {
		this.rand = r;
		
		if (this.tube_up == null){
		this.tube_up = ImageIO.read(new File("tube_up.png"));
		}
		
		if (this.tube_down == null){
		this.tube_down = ImageIO.read(new File("tube_down.png"));
		}
		
		tube_w = this.tube_up.getWidth();
		tube_h = this.tube_up.getHeight();
		
		this.directionUp = r.nextBoolean();
		this.x = r.nextInt((500 - 350) + 1) + 350;
		
		if (directionUp == true){
			this.y = r.nextInt((400 - 100) + 1) + 100;
		}
		else{
			this.y = (((r.nextInt(350 - 100) + 1) + 100) * -1);
		}
		
	}
	
	Tube(Tube original, Model newModel){
		this.x = original.x;
		this.y = original.y;
		this.tube_w = original.tube_w;
		this.tube_h = original.tube_h;
		this.isRetracting = original.isRetracting;
		this.directionUp = original.directionUp;
		this.count = original.count;
		this.rand = newModel.rand;
	}
	
	public boolean update(){
	
		boolean die = false;
		
		// moves the tubes to the left
		if (x >= -55){
		this.x = x - 5;
		die = false;
		}
		
		// if tube is out of screen
		if (x < -55){
			x = 450;
			position(rand);
			die = true;
		}
		
		if (isRetracting == true){
			retract();
		}
		
		return die;
	}
	
	public void position(Random r){
		directionUp = r.nextBoolean();
		if (directionUp == true){
			this.y = r.nextInt((400 - 100) + 1) + 100;
		}
		else{
			this.y = ((r.nextInt(300) + 1) * -1);
		}
	}
	
	public void retract(){
		if (directionUp == true){
			if(this.y != 505){
			this.y = (int) (this.y + 5);
			}
		}
		else{
			if(this.y != -400){
			this.y = (int) (this.y - 5);
			}
		}
	}
	
	public void draw(Graphics g) {
		// Draw the tube
		if (this.directionUp == true ){
			g.drawImage(this.tube_up, this.x, this.y, null);
		} else {
			g.drawImage(this.tube_down, this.x, this.y, null);
		}			
	}
	
	 Sprite replicate(Model m) throws Exception{
		return new Tube(this, m);
	}

}
