
class Model
{
	Bird pigeon;

	Model() {
		Bird b = new Bird();
		pigeon = b;
	}

	public void update() {
		pigeon.update();
	}

	public void setDestination(int x, int y) {
		pigeon.setDestination(x, y);
	}
}
