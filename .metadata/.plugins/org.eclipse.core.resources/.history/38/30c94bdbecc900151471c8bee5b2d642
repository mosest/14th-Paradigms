import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;

class View extends JPanel {
	Model model;

	View(Model m) throws IOException {
		this.model = m;
	}

	public void paintComponent(Graphics g) {
		// Draw the bird
		g.drawImage(this.model.pigeon.image, this.model.pigeon.x, this.model.pigeon.y, null);
		
		// Draw the tubes
		for (Tube current_tube : this.model.tube_list) {
			if (current_tube.facing_up) g.drawImage(current_tube.image, current_tube.x, current_tube.y, null);
			else g.drawImage(current_tube.image, current_tube.x, current_tube.y - 400, null);
		}
	}
}
