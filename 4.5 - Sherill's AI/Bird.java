import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bird extends Sprite {

	int b_w, b_h;
	double vertical_velocity;
	static final double GRAVITY = 0.75; 
	static final double FLAP = -10;
	static final int BOUNCE = -15;
	int counter = 0;
	boolean isDead;
	boolean isACopy;

	static BufferedImage bird_image;
	static BufferedImage bird1_image;
	static BufferedImage deadBird;
	
	public static enum ACTIONS{
		do_nothing, flap, throw_pie, flap_and_throw;
	}	
	
	Bird() throws IOException{
		if (bird_image == null){
		bird_image = ImageIO.read(new File("bird1.png"));
		b_w = bird_image.getWidth();
		b_h = bird_image.getHeight();
		}
		
		if (bird1_image == null){
		bird1_image = ImageIO.read(new File("bird2.png"));
		b_w = bird1_image.getWidth();
		b_h = bird1_image.getHeight();
		}
		
		if (deadBird == null){
			deadBird = ImageIO.read(new File("feathers.png"));
			}
		
		isDead = false;
		isACopy = false;
		
	}
	
	Bird(Bird original){
		this.x = original.x;
		this.y = original.y;
		this.b_w = original.b_w;
		this.b_h = original.b_h;
		this.vertical_velocity = original.vertical_velocity;
		this.counter = original.counter;
		this.isDead = original.isDead;
		this.isACopy = true;
	}
	
	public boolean update() {
		// vertical velocity changing the bird's position
		vertical_velocity += GRAVITY;
		this.y += vertical_velocity; 
		counter++;
		
		if(isDead == true){ 
			if(this.y >= 450){
				this.y = 450;
				this.vertical_velocity = (int) BOUNCE;
				this.x += 100;
			}
			if(this.x >= 520){
				System.out.println("Game Over!");
				System.exit(0);
			}
		}
		
		return false;
		
	}
	
	public void flap(){
		// Makes the bird flap and go upward
		this.vertical_velocity = (int) FLAP;
		counter = 0;
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		// Draw the bird
		if(isDead == false){
		if(this.counter > 2)
		g.drawImage(this.bird_image, this.x, this.y, null);
				
		// Draw the bird, wings down 
		else
		g.drawImage(this.bird1_image, this.x, this.y, null);
		}
		else{
			g.drawImage(this.deadBird, this.x, this.y, null);
		}
		
	}

	Sprite replicate(Model m) {
		return new Bird(this);
	}
}
