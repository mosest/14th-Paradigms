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
		g.drawImage(this.model.pigeon.bird_image, this.model.pigeon.bird_x, this.model.pigeon.bird_y, null);
		
		// Draw the tubes
		if (model.green_tube.facing_up) g.drawImage(this.model.green_tube.tube_image, this.model.green_tube.tube_x, this.model.green_tube.tube_y, null);
		else g.drawImage(this.model.green_tube.tube_image, this.model.green_tube.tube_x, this.model.green_tube.tube_y - 400, null);
	}
}
