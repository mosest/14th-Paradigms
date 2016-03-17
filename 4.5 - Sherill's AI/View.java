import javax.swing.JPanel;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Iterator;

class View extends JPanel {
	Model model;
	int height, width;

	View(Model m) throws IOException {
		this.model = m;
	}

	public void paintComponent(Graphics g) {
		
		// Draw all the sprites
		Iterator<Sprite> it = this.model.sprites.iterator();
		while(it.hasNext()){
			Sprite s = it.next();
			s.draw(g);
		}		
	}
	
}
