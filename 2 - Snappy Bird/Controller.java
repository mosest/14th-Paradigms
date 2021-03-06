import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.event.MouseEvent;

class Controller implements MouseListener
{
	Model model;

	Controller(Model m) {
		this.model = m;
	}

	public void mousePressed(MouseEvent e) {
		try {
			model.onClick();
		} catch (IOException e1) {
			System.out.println("at Controller.java -> mousePressed, IOException");
		}
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }

}
