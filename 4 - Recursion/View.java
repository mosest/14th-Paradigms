import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Iterator;

class View extends JPanel {
	Model model;

	View(Model m) {
		this.model = m;
	}

	public void paintComponent(Graphics g) {
		// Draw the sprites
		Iterator<Sprite> iterator = this.model.sprite_list.iterator();
		while (iterator.hasNext()) {
			iterator.next().draw(g);
		}
	}
}
