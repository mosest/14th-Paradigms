import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;

class View extends JPanel {
	Model model;
	Image turtle_image;

	View(Model m) throws IOException {
		this.model = m;
		this.turtle_image = ImageIO.read(new File("bird-wingup.png"));
	}

	public void paintComponent(Graphics g) {

		// Draw the turtle
		g.drawImage(this.turtle_image, this.model.turtle_x, this.model.turtle_y, null);
	}
}
