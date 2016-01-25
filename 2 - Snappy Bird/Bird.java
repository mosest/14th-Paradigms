class Bird {
	int dest_x;
	int dest_y;
	int bird_x;
	int bird_y;
	int bird_velocity = 5;
	
	Bird() {
		
	}

	public void update() {
		if(this.bird_x > 200) {
			System.out.println("hi");
		}
		// Move the bird
		if(this.bird_x < this.dest_x)
			this.bird_x += Math.min(bird_velocity, dest_x - bird_x);
		else if(this.bird_x > this.dest_x)
			this.bird_x -= Math.min(bird_velocity, bird_x - dest_x);
		if(this.bird_y < this.dest_y)
			this.bird_y += Math.min(bird_velocity, dest_y - bird_y);
		else if(this.bird_y > this.dest_y)
			this.bird_y -= Math.min(bird_velocity, bird_y - dest_y);
	}

	public void setDestination(int x, int y) {
		this.dest_x = x;
		this.dest_y = y;
	}
}