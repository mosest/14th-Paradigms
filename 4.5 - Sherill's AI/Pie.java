import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Pie extends Sprite {

	static BufferedImage pieImage;
	int x, y;
	int p_w, p_h;
	double vertical_velocity;
	static final double GRAVITY = 1.5; 
	LinkedList<Sprite> spriteList;
	
	Pie(int x, int y, LinkedList<Sprite> s) throws Exception{
		if (pieImage == null){
			pieImage = ImageIO.read(new File("pie.png"));
		}
		
		this.p_w = this.pieImage.getWidth();
		this.p_h = this.pieImage.getHeight();
		
		this.x = x;
		this.y = y;
		
		this.spriteList = s;
		
	}
	
	Pie (Pie original, LinkedList<Sprite> newList){
		this.x = original.x;
		this.y = original.y;
		this.p_w = original.p_w;
		this.p_h = original.p_h;
		this.vertical_velocity = original.vertical_velocity;
		this.spriteList = newList;
	}

	boolean update() {
		boolean die = false;
		
		// vertical velocity changing the bird's position
		vertical_velocity += GRAVITY;
		this.y += vertical_velocity; 
		this.x += 15;
		try {
			pieCollision();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if(this.y > 500 || pieCollision() == true){
				die = true;
			}
			else{
				die = false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return die;
	}

	void draw(Graphics g) {
		g.drawImage(this.pieImage, x, y, null);
		
	}
	
	public boolean pieCollision() throws IOException{
		
		boolean pieCollided = false;

		for (Sprite sprite: this.spriteList){

			if(sprite.getClass().equals(Tube.class)){

				Tube tube = (Tube) sprite;
				int x = tube.x - this.x;
				int y = tube.y - this.y;

				if(x >= 0 && x < this.p_w && y >=0 && y < this.p_h){
					Color c = new Color(this.pieImage.getRGB(x, y));
					int alphaChannel = c.getAlpha();
					if(alphaChannel > 128){
						tube.isRetracting = true;
						pieCollided = true;
					}
		}
		
		if(tube.directionUp == true){
			if((this.x + this.p_w > tube.x
					&& this.x < tube.x + tube.tube_w
					&& this.y + this.p_h > tube.y)
					|| this.y >= 500){
				tube.isRetracting = true;
				pieCollided = true;
			}
		
		}
		
		else if (tube.directionUp == false){
				if((this.x + this.p_w > tube.x
						&& this.x < tube.x + tube.tube_w
						&& this.y < tube.y + tube.tube_h)){ 
					tube.isRetracting = true;
					pieCollided = true;
					}
			}
		}
		}
		return pieCollided;
	}


	Sprite replicate(Model m) {
		return new Pie(this, m.sprites);
	}

}
