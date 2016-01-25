
class Model
{
	int dest_x;
	int dest_y;
	int turtle_x;
	int turtle_y;
	int turtle_speed = 5;

	Model() {
	}

	public void update() {
		if(this.turtle_x > 200) {
			System.out.println("hi");
		}
		// Move the turtle
		if(this.turtle_x < this.dest_x)
			this.turtle_x += Math.min(turtle_speed, dest_x - turtle_x);
		else if(this.turtle_x > this.dest_x)
			this.turtle_x -= Math.min(turtle_speed, turtle_x - dest_x);
		if(this.turtle_y < this.dest_y)
			this.turtle_y += Math.min(turtle_speed, dest_y - turtle_y);
		else if(this.turtle_y > this.dest_y)
			this.turtle_y -= Math.min(turtle_speed, turtle_y - dest_y);
	}

	public void setDestination(int x, int y) {
		this.dest_x = x;
		this.dest_y = y;
	}
}
