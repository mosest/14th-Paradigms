import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

class Model
{
	// Sprite stuff!
	LinkedList<Sprite> sprite_list;
	int add_tube_count = 0;
	
	// Other
	Random rng;
	boolean game_is_over; // Model should handle whether the actual game is over, right??

	Model() throws IOException {
		// Initialize sprite_list and the random num generator
		this.sprite_list = new LinkedList<Sprite>();
		this.rng = new Random();
		this.game_is_over = false;
		
		// Add a bird into the sprite list!
		// It's the first thing added, so index 0
		sprite_list.add(new Bird(300,100,sprite_list));
	}

	public void update() throws IOException {
		// If the game is over, then call game_over for every sprite
		if (game_is_over) {
			for (Sprite current_sprite : sprite_list) {
				current_sprite.game_over();
			}
		} else {
			// Add another tube to the screen after 60 frames
			add_tube_count++;
			
			if (add_tube_count == 60) {
				sprite_list.add(new Tube(500, RNG(100,300), RNG(0,1)));
				add_tube_count = 0;
			}
			
			// If the bird is dead, game over
			if (sprite_list.get(0).is_dead) game_is_over = true;
		}

		// Whether game_over or not, bring out yer dead
		// (no sprite will update if game_is_over because
		// the sprites will all be dead :D)
		for (Sprite current_sprite : sprite_list) {
			if(current_sprite.is_dead) {
				if (sprite_list.remove(current_sprite)) break;	// Delete tubes that go off-screen
			} else current_sprite.update();						// and update the others
		}
	}
	
	public void onClick() throws IOException {
		if (!game_is_over) {
			Bird current_bird = (Bird)sprite_list.get(0);
			current_bird.flap();
		}
	}
	
	public void onRightClick() throws IOException {
		if (!game_is_over) {
			Bird current_bird = (Bird)sprite_list.get(0);
			sprite_list.add(new Pie(current_bird.x, current_bird.y));
		}
	}
	
	// Returns an int in range [from, to]
	public int RNG(int from, int to) {
		return rng.nextInt(to - from + 1) + from;
	}
	
	public void game_over() {
		System.out.println("Game over!");
		for (Sprite current_sprite : sprite_list) current_sprite.game_over();
	}
}
