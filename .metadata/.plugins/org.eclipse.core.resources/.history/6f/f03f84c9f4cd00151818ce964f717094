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
