import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;

class View extends JPanel {
	Model model;
	Image bird_image;

	View(Model m) throws IOException {
		this.model = m;
		this.bird_image = ImageIO.read(new File("bird-wingup.png"));
	}

	public void paintComponent(Graphics g) {

		// Draw the bird
		g.drawImage(this.bird_image, this.model.pigeon.bird_x, this.model.pigeon.bird_y, null);
	}
}
