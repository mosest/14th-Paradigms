import java.awt.event.MouseListener;
import java.io.IOException;

import java.awt.event.MouseEvent;

class Controller implements MouseListener
{
	Model model;

	Controller(Model m) {
		this.model = m;
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {			// Left click!
			try {
				model.onClick();
			} catch (IOException e1) {
				System.out.println("at Controller.java -> mousePressed, IOException");
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) { 	// Right click!
			try {
				model.onRightClick();
			} catch (IOException e1) {
				System.out.println("at Controller.java -> mousePressed, IOException");
			}
		}
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }

}
