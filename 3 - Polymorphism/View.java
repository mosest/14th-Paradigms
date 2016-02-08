import javax.swing.JPanel;
import java.awt.Graphics;

class View extends JPanel {
	Model model;

	View(Model m) {
		this.model = m;
	}

	public void paintComponent(Graphics g) {
		// Draw the sprites
		for (Sprite current_sprite : this.model.sprite_list) {
			current_sprite.draw(g);
		}
	}
}
