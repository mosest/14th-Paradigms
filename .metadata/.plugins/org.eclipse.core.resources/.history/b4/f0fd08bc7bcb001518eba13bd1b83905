import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;

class View extends JPanel {
	Model model;

	View(Model m) throws IOException {
		this.model = m;
		
		// Bird images
		this.model.pigeon.bird_wingdown = ImageIO.read(new File("bird-wingdown.png"));
		this.model.pigeon.bird_wingup = ImageIO.read(new File("bird-wingup.png"));
		
		// Tube images
		for (Tube current_tube : this.model.tube_list) {
			current_tube.tube_facingdown = ImageIO.read(new File("tubedown.png"));
			current_tube.tube_facingup = ImageIO.read(new File("tubeup.png"));
		}
	}

	public void paintComponent(Graphics g) {
		// Draw the bird
		this.model.pigeon.draw(g);
		
		// Draw the tubes
		for (Tube current_tube : this.model.tube_list) {
			current_tube.draw(g);
		}
	}
}
