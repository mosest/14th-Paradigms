import java.io.IOException;

class Model
{
	Bird pigeon;
	Tube green_tube;

	Model() throws IOException {
		Bird b = new Bird();
		this.pigeon = b;
		this.green_tube = new Tube(400,100);
	}

	public void update() throws IOException {
		pigeon.update();
		green_tube.update();
	}
	
	public void onClick() throws IOException {
		pigeon.flap();
	}
}
