import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

class Model
{
	// Bird stuff
	Bird pigeon;
	
	// Tube stuff
	LinkedList<Tube> tube_list;
	int add_tube_count = 0;
	
	// Other
	Random rng;
	boolean collision = false;

	Model() throws IOException {
		Bird b = new Bird();
		this.pigeon = b;
		this.tube_list = new LinkedList<Tube>();
		this.rng = new Random();
	}

	public void update() throws IOException {
		// Call update() for bird and tubes!
		pigeon.update();
		
		for (Tube current_tube : tube_list) {
			if (!current_tube.is_dead) current_tube.update();
		}
		
		// Check whether bird hits tube
		//collisionDetection();
		//if (collision) gameOver();

		// Add another tube to the screen after 60 frames
		add_tube_count++;
		
		if (add_tube_count == 60) {
			tube_list.add(new Tube(500, RNG(100,300), RNG(0,1)));
			add_tube_count = 0;
		}
		
		// Delete tubes that go off-screen
		for (Tube current_tube : tube_list) {
			if (current_tube.is_dead) {
				if (tube_list.remove(current_tube)) break;
			}
		}
	}
	
	public void onClick() throws IOException {
		pigeon.flap();
	}
	
	// Returns an int in range [from, to]
	public int RNG(int from, int to) {
		return rng.nextInt(to - from + 1) + from;
	}
	
	public void gameOver() {
		System.out.println("Game over!");
		pigeon.gameOver();
		for (Tube current_tube : tube_list) current_tube.gameOver();
	}
	
	public void collisionDetection() {
		for (Tube current_tube : tube_list) {
			if (current_tube.facing_up) {
				if (pigeon.x + pigeon.width > current_tube.x && 		// bird's right edge is to the right of the tube's left edge
					pigeon.x < current_tube.x + current_tube.width && 	// bird's left edge is to the left of the tube's right edge
					pigeon.y + pigeon.height > current_tube.y) { 		// bird's bottom edge is below the tube's entrance
						collision = true;
					}
			} else {
				if (pigeon.x + pigeon.width > current_tube.x &&			// same as top
					pigeon.x < current_tube.x + current_tube.width &&	// same as top
					pigeon.y < current_tube.y) {						// bird's top edge is above the tube's entrance
						collision = true;
					}
			}
		}
		
	}
}