
class Model
{
	int dest_x;
	int dest_y;
	int turtle_x;
	int turtle_y;

	Model() {
	}

	public void update() {
		// Move the turtle
		if(this.turtle_x < this.dest_x)
			this.turtle_x += 1;
		else if(this.turtle_x > this.dest_x)
			this.turtle_x -= 1;
		if(this.turtle_y < this.dest_y)
			this.turtle_y += 1;
		else if(this.turtle_y > this.dest_y)
			this.turtle_y -= 1;
	}

	public void setDestination(int x, int y) {
		this.dest_x = x;
		this.dest_y = y;
	}
}
