import java.io.IOException;
import java.util.Random;

class Model
{
	Bird pigeon;
	Tube green_tube;
	Random rng;

	Model() throws IOException {
		Bird b = new Bird();
		this.pigeon = b;
		this.green_tube = new Tube(400,100);
		this.rng = new Random();
	}

	public void update() throws IOException {
		pigeon.update();
		green_tube.update(RNG(100, 300), RNG(0, 1));
	}
	
	public void onClick() throws IOException {
		pigeon.flap();
	}
	
	// Returns an int in range [from, to]
	public int RNG(int from, int to) {
		return rng.nextInt(to - from + 1) + from;
	}
}
