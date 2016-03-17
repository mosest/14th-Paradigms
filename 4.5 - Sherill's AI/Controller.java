import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

class Controller implements MouseListener
{
	Model model;

	Controller(Model m) {
		this.model = m;
	}

	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) {  }
	public void mouseClicked(MouseEvent e) { 
		if(SwingUtilities.isRightMouseButton(e)){
			try {
				model.rightClick();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
			model.onClick();
	}
	public void update() throws Exception{		
		
		int a = model.evaluateAction(Bird.ACTIONS.do_nothing, 0);
		int b = model.evaluateAction(Bird.ACTIONS.flap, 0);
		int c = model.evaluateAction(Bird.ACTIONS.throw_pie, 0);
		int d = model.evaluateAction(Bird.ACTIONS.flap_and_throw, 0);
		
		//System.out.println(a + " " + b + " " + c + " " + d);
		int max = Math.max(Math.max(a, b), Math.max(d, c));
		
		if(max == a){
			System.out.println("nothing won!------------------------------------------");
		}
		else if(max == b) {
			System.out.println("flap won!---------------------------------------------");
			model.onClick();
		} else if(max == c) {
			System.out.println("pie won!----------------------------------------------");
			model.rightClick();
		} else if (max == d)
		{
			System.out.println("flap + pie won!---------------------------------------");
			model.onClick();
			model.rightClick();
		}
		
	}
	
}
