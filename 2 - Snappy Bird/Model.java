import java.io.IOException;

class Model
{
	Bird pigeon;

	Model() throws IOException {
		Bird b = new Bird();
		this.pigeon = b;
	}

	public void update() throws IOException {
		pigeon.update();
	}
	
	public void onClick() throws IOException {
		pigeon.flap();
	}
}
