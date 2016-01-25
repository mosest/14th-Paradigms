import java.io.IOException;
import java.util.Random;

class Model
{
	Bird pigeon;
	Tube green_tube;
	Random rng;
	boolean collision = false;

	Model() throws IOException {
		Bird b = new Bird();
		this.pigeon = b;
		this.green_tube = new Tube(400,100);
		this.rng = new Random();
	}

	public void update() throws IOException {
		pigeon.update();
		green_tube.update(RNG(100, 300), RNG(0, 1));
		collisionDetection();
		if (collision) gameOver();
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
		green_tube.gameOver();
	}
	
	public void collisionDetection() {
		if (green_tube.facing_up) {
			if (pigeon.x + pigeon.width > green_tube.x && 		// bird's right edge is to the right of the tube's left edge
				pigeon.x < green_tube.x + green_tube.width && 	// bird's left edge is to the left of the tube's right edge
				pigeon.y + pigeon.height > green_tube.y) { 		// bird's bottom edge is below the tube's entrance
					collision = true;
				}
		} else {
			if (pigeon.x + pigeon.width > green_tube.x &&		// same as top
				pigeon.x < green_tube.x + green_tube.width &&	// same as top
				pigeon.y < green_tube.y) {						// bird's top edge is above the tube's entrance
					collision = true;
				}
		}
	}
}
