import java.awt.Image;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

class Model
{
	// -----------------------------------------------------------------------------
	// Member Variables
	// -----------------------------------------------------------------------------
	
	// Sprite stuff!
	LinkedList<Sprite> sprite_list;
	int add_tube_count = 50;
	
	// Player's bird
	Bird pigeon;
	
	// Counts!
	int numPies = 0;
	int numTubes = 0;
	
	// Other
	Random rng;
	long seed = 34;
	static int recursion_cutoff = 20;
	static int k = 5;
	boolean is_a_copy;
	boolean game_is_over; // Model should handle whether the actual game is over, right??

	// -----------------------------------------------------------------------------
	// Constructors
	// -----------------------------------------------------------------------------
	
	Model() throws IOException {
		// Initialize sprite_list and the random num generator
		this.sprite_list = new LinkedList<Sprite>();
		this.rng = new Random(seed);
		this.game_is_over = false;
		
		// Add a bird into the sprite list!
		// It's the first thing added, so index 0
		pigeon = new Bird(300,100,sprite_list);
		//System.out.println(this + ": pigeon is " + pigeon);
		sprite_list.add(pigeon);	
		this.is_a_copy = false;
	}
	
	Model(Model orig) throws IOException {
		// Initialize sprite_list and the random num generator
		this.sprite_list = new LinkedList<Sprite>();
		this.rng = new Random(orig.rng);
		this.game_is_over = orig.game_is_over;
		this.is_a_copy = true;
		this.add_tube_count = orig.add_tube_count;
		
		// Add bird into sprite list!
		this.pigeon = new Bird(orig.pigeon,this);
		this.sprite_list.add(pigeon);
		
		// Copy everything else from the original sprite list into the new sprite list!
		Iterator<Sprite> origIterator = orig.sprite_list.iterator();
		
		while (origIterator.hasNext()) {
			Sprite current_sprite = origIterator.next();
			
			if (current_sprite.getClass().equals(Tube.class)) {
				Tube new_sprite = new Tube((Tube)current_sprite);
				sprite_list.add(new_sprite);				
			} else if (current_sprite.getClass().equals(Pie.class)) {
				Pie new_sprite = new Pie((Pie)current_sprite,this);
				sprite_list.add(new_sprite);				
			}
		}
		
		//System.out.println(this + " is a copy of " + orig);
		//System.out.println(this.pigeon + " is a copy of " + orig.pigeon);
	}

	// -----------------------------------------------------------------------------
	// Functions
	// -----------------------------------------------------------------------------

	public void update() throws IOException {		
		// If the game is over, then call game_over for every sprite
		if (game_is_over) {
			Iterator<Sprite> iterator = sprite_list.iterator();
			
			while (iterator.hasNext()) {
				iterator.next().game_over();
			}
		} else {
			
			// Add another tube to the screen after 60 frames
			add_tube_count++;
			
			if (add_tube_count == 60) {
				Tube new_tube = new Tube(500, RNG(100,250), RNG(0,1));
				sprite_list.add(new_tube);
				//System.out.println("tube " + this.tube_total++ + " added (" + new_tube.facing_up + ", " + new_tube.y + ")");
				
				add_tube_count = 0;
			}
			
			// If the bird is dead, game over
			if (pigeon.is_dead) game_is_over = true;
		}

		// Whether game_over or not, bring out yer dead
		// (no sprite will update if game_is_over because
		// the sprites will all be dead :D)
		Iterator<Sprite> iterator = sprite_list.iterator();
		
		while (iterator.hasNext()) {
			Sprite current_sprite = iterator.next();
			
			// Still update everything
			current_sprite.update();
			
			if(current_sprite.is_dead) {
				if (!current_sprite.equals(pigeon))						// EXCEPT FOR THE DEAD BIRD WHICH STAYS,
					if (sprite_list.remove(current_sprite)) break;		// delete sprites that are dead
			}
		}
		
		// Check if any copy bird is dying (oh shit what if it's Sherill pt. 2)
		//if (this.pigeon.is_dead && this.is_a_copy) System.out.println("A copy bird is dead!");
		
	}
	
	public void onClick() throws IOException {
		if (!this.game_is_over) {
			pigeon.flap();
		}
	}
	
	public void onRightClick() throws IOException {
		if (!this.game_is_over) {
			Pie new_pie = new Pie(this.pigeon.x, this.pigeon.y, this.sprite_list);
			this.sprite_list.add(new_pie);
		}
	}
	
	// Returns an int in range [from, to]
	public int RNG(int from, int to) {
		return rng.nextInt(to - from + 1) + from;
	}
	
	public void game_over() {
		Iterator<Sprite> iterator = sprite_list.iterator();
		
		System.out.println("Game over!");		
		while (iterator.hasNext()) iterator.next().game_over();
	}
	
	public int evaluateAction(Bird.Action act, int depth) {
		
		// Base case: basically if you get to a certain
		// point without returning something, we do this:
		
		if (depth == recursion_cutoff) {
			if (this.pigeon.is_dead) {
				//System.out.println("A cloned bird saw the future, and in it, its own death.");
				return 0;
			}
			else {
				int num = 500 - (Math.abs(this.pigeon.y - 250));
				return num;			
			}
		} else {
			try {
				// Make a deep copy of Model
				Model new_model = new Model(this);
				
				// Perform act! PERFORM IT
				if (act == Bird.Action.FLAP) {
					new_model.onClick();
					//System.out.println(depth + ": " + new_model + " is flappin'");
				} else if (act == Bird.Action.FLAP_AND_THROW_PIE) {
					new_model.onClick();
					new_model.onRightClick();
					//System.out.println(depth + ": " + new_model + " is flappin and throwin'");
				} else if (act == Bird.Action.THROW_PIE) {
					new_model.onRightClick();
					//System.out.println(depth + ": " + new_model + " is throwin'");
				} else {
					//System.out.println(depth + ": " + new_model + " is doin' nothin'");
				}
				
				// Update the model! :D (the new_model, i think)
				new_model.update();
			
				// Now check if depth % k == 0
				if (depth % k != 0) {
					return new_model.evaluateAction(Bird.Action.DO_NOTHING, depth + 1);
				} else {
					int nothing 	= new_model.evaluateAction(Bird.Action.DO_NOTHING, depth + 1);
					int flap 		= new_model.evaluateAction(Bird.Action.FLAP, depth + 1);
					int flap_pie 	= new_model.evaluateAction(Bird.Action.FLAP_AND_THROW_PIE, depth + 1);
					int pie 		= new_model.evaluateAction(Bird.Action.THROW_PIE, depth + 1);
					
					// Print out!!
					//System.out.println(depth + ": " + nothing + " " + flap + " " + flap_pie + " " + pie);
					
					int big = Math.max(Math.max(nothing, flap), Math.max(flap_pie, pie));
					return big;
				}
			} catch (IOException e) {}
		}
		return -1;
	}
}
