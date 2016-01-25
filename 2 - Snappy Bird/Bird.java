class Bird {
	// Member variables
	int bird_y;	
	int bird_x;
	double velocity_y;
	
	Bird() {
		bird_x = 250; // middle of the screen
		velocity_y = 10;
	}

	public void update() {
		// Simulate gravity (origin is in top-left of window!)
		velocity_y += 2;
		bird_y += velocity_y;
	}
	
	public void flap() {
		velocity_y = -25; // going towards the top of the window (jumping)
	}
}