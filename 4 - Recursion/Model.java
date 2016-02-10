import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

class Model
{
	// Sprite stuff!
	LinkedList<Sprite> sprite_list;
	int add_tube_count = 0;
	
	// Player's bird
	Bird pigeon;
	
	// Other
	Random rng;
	int recursion_cutoff;
	int k;
	boolean game_is_over; // Model should handle whether the actual game is over, right??

	Model() throws IOException {
		// Initialize sprite_list and the random num generator
		this.sprite_list = new LinkedList<Sprite>();
		this.rng = new Random();
		this.recursion_cutoff = 50;
		this.k = 5;
		this.game_is_over = false;
		
		// Add a bird into the sprite list!
		// It's the first thing added, so index 0
		pigeon = new Bird(300,100,sprite_list);
		sprite_list.add(pigeon);	
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
			if (pigeon.is_dead) game_is_over = true;
		}

		// Whether game_over or not, bring out yer dead
		// (no sprite will update if game_is_over because
		// the sprites will all be dead :D)
		for (Sprite current_sprite : sprite_list) {
			// Still update everything
			current_sprite.update();	
			
			if(current_sprite.is_dead) {
				if (!current_sprite.equals(pigeon)) {				// EXCEPT FOR THE DEAD BIRD WHICH STAYS,
					if (sprite_list.remove(current_sprite)) break;	// delete sprites that are dead
				}
			}
		}
	}
	
	public void onClick() throws IOException {
		if (!game_is_over) {
			pigeon.flap();
		}
	}
	
	public void onRightClick() throws IOException {
		if (!game_is_over) {
			sprite_list.add(new Pie(pigeon.x, pigeon.y, sprite_list));
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
	
	public int evaluateAction(Bird.Action act, int depth) throws IOException {
		// This function is recursive. I have to figure out how.
		// Like, seriously, what the fuck. What is this thing even
		// supposed to do. What.
		
		// Base case: 0 <= depth < recursion_case
		if (depth == recursion_cutoff) {
			if (pigeon.is_dead) return 0;
			else return 500 - (Math.abs(pigeon.y - 250));			
		} else {
			try {
				// Make a deep copy of Model
				Model new_model;
				new_model = new Model();
				
				// Perform act! PERFORM IT
				switch(act) {
					case DO_NOTHING:
						break;
						
					case FLAP:
						new_model.onClick();
						break;
						
					case FLAP_AND_THROW_PIE:
						new_model.onClick();
						new_model.onRightClick();
						break;
						
					case THROW_PIE:
						new_model.onRightClick();
						break;
						
					default:
						break;
				}
				
				// Update the model! :D (the new_model, i think)
				new_model.update();
			} catch (IOException e) {}
			
			// Now check if depth %  == 0
			if (depth % k == 0) {
				return evaluateAction(Bird.Action.DO_NOTHING, depth + 1);
			} else {
				int nothing 	= evaluateAction(Bird.Action.DO_NOTHING, depth + 1);
				int flap 		= evaluateAction(Bird.Action.FLAP, depth + 1);
				int flap_pie 	= evaluateAction(Bird.Action.FLAP_AND_THROW_PIE, depth + 1);
				int pie 		= evaluateAction(Bird.Action.THROW_PIE, depth + 1);
				
				return Math.max(Math.max(nothing, flap), Math.max(flap_pie, pie));
			}
		}
	}
}
