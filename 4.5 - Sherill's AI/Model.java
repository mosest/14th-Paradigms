import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

class Model {

	Random rand;
	Bird bird;
	LinkedList<Sprite> sprites;
	int count;

	Model() throws Exception {
		rand = new Random(0);
		sprites = new LinkedList<Sprite>();
		bird = new Bird();
		Tube tube = new Tube(rand);
		sprites.add(bird);
		sprites.add(tube);
		count = 0;
	}

	Model(Model original) throws Exception {
		
		this.rand = new Random(original.rand);
		this.count = original.count;
		this.sprites = new LinkedList<Sprite>();

		Iterator<Sprite> it = original.sprites.iterator();

		while (it.hasNext()) {
			Sprite s = it.next();
			Sprite sCopy = s.replicate(this);
			this.sprites.add(sCopy);
		}

		Sprite first = sprites.getFirst();
		if (first instanceof Bird) {
			bird = (Bird) first;
		} else
			throw new IllegalArgumentException("uhoh");
		
		//System.out.println("The bird for " + this + " is " + bird + "/n");
	}

	// Updates position of bird and tubes
	public void update() throws Exception {
		if (this.bird != sprites.peekFirst())
			throw new IllegalArgumentException("Expected this.bird to be the first item in the linked list");

		count++;
		
		// Try for collision
		if (collision() == true){
			bird.isDead = true;
		}

		if (count == 50) {
			Tube newTube = new Tube(rand);
			sprites.add(newTube);
			count = 0;
		}

		// Print something when the bird dies! Maybe the deep copy bird can't die?
		//if (bird.isDead && bird.isACopy) System.out.println(bird + " died!");
		
		// Now print something if a non-copy (ie, the ORIGINAL) dies! thats the
		// ONLY TIME the game should game-over........ >:/
		//if (bird.isDead && !bird.isACopy) System.out.println(bird + " died!");
		
		Iterator<Sprite> it = sprites.iterator();
		while (it.hasNext()) {
			Sprite s = it.next();
			if (s.update() == true) {
				it.remove();
			}
		}

	}

	// Makes the bird flap when mouse is clicked
	public void onClick() {
		if (bird.isDead == false) {
			bird.flap();
		}
	}

	// Makes the bird throw pie on right click
	public void rightClick() throws Exception {
		if (bird.isDead == false) {
			Pie pie;
			pie = new Pie(bird.x, bird.y, sprites);
			sprites.add(pie);
		}
	}

	// Detects collision
	public boolean collision() throws IOException {
		boolean collision = false;

		for (Sprite sprite : this.sprites) {

			if (sprite.getClass().equals(Tube.class)) {

				Tube tubeC = (Tube) sprite;
				int x = tubeC.x - this.bird.x;
				int y = tubeC.y - this.bird.y;

				if (x >= 0 && x < this.bird.b_w && y >= 0 && y < this.bird.b_h) {
					Color c = new Color(this.bird.bird_image.getRGB(x, y));
					int alphaChannel = c.getAlpha();
					if (alphaChannel > 128)
						collision = true;
				}

				if (tubeC.directionUp == true) {
					if ((this.bird.x + this.bird.b_w > tubeC.x
							&& this.bird.x < tubeC.x + tubeC.tube_w
							&& this.bird.y + this.bird.b_h > tubeC.y)) {
						collision = true;
					} else if (this.bird.y >= 550) {
						collision = true;
					}

				}

				else if (tubeC.directionUp == false) {
					if ((this.bird.x + this.bird.b_w > tubeC.x
							&& this.bird.x < tubeC.x + tubeC.tube_w
							&& this.bird.y < tubeC.y + tubeC.tube_h)
							|| this.bird.y + this.bird.b_h <= 0) {
						collision = true;
					}
				}

				else {
					collision = false;
				}
			}
		}
		return collision;
	}

	public int evaluateAction(Bird.ACTIONS actions, int currentDepth) throws Exception {
		
		int k = 5;
		int finalDepth = 20; 
		
		// Base case
		if (currentDepth == finalDepth) {
			if (this.bird.isDead) {
				//System.out.println("tried to " + actions + ", but died :( ~~~~~~~~~~~~~~~~~~~~~~~~~");
				return 0;
			} else {
				return 500 - Math.abs(bird.y - 250);
			}
		}
		else {	
		
			// Recursive case	
			Model model1 = new Model(this);
			if(actions == actions.flap){
				model1.onClick();
			} else if(actions == actions.flap_and_throw){
				model1.onClick();
				model1.rightClick();
			} else if (actions == actions.throw_pie){
				model1.rightClick();
			} 

			model1.update();
			
			if (currentDepth % k != 0) {
				return model1.evaluateAction(Bird.ACTIONS.do_nothing, currentDepth +1);
			} else {
				int a = model1.evaluateAction(Bird.ACTIONS.do_nothing, currentDepth+1);
				int b = model1.evaluateAction(Bird.ACTIONS.flap, currentDepth+1);
				int c = model1.evaluateAction(Bird.ACTIONS.throw_pie, currentDepth+1);
				int d = model1.evaluateAction(Bird.ACTIONS.flap_and_throw, currentDepth+1);

				//model1.update();

				//System.out.println("depth(" + currentDepth + "): " + a + " " + b + " " + c + " " + d);
				int max = Math.max(Math.max(a, b), Math.max(d, c));
				
				//System.out.println(currentDepth + ": returning max of " + a + " " + b + " " + c + " " + d);
				
				return max;
			}

		}
		
	}

}
