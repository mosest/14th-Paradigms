import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Game extends JFrame implements ActionListener {
	Model model;
	Model model_clone;
	Controller controller;
	Controller controller_clone;
	
	LinkedList<Model> model_list;

	public Game() throws Exception {
		this.model = new Model();
		View view = new View(this.model);
		this.setTitle("!! Snappy Bird !!");
		this.setSize(500, 500);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		new Timer(33, this).start(); // Indirectly calls actionPerformed at regular intervals

		// New things
		/*this.model_clone = new Model(this.model);
		
		model_list = new LinkedList<Model>();
		model_list.add(model);
		model_list.add(model_clone);	*/

		controller = new Controller(this.model);
		view.addMouseListener(controller);
		
		// Print-out statements
		System.out.println("Original: " + model + " and " + model.pigeon);
	}

	public void actionPerformed(ActionEvent evt) {
		try {
			this.controller.update();
			this.model.update();
		} catch (IOException e) {
			System.out.println("at Game.java -> actionPerformed, IOException");
		}
		repaint(); // Indirectly calls View.paintComponent
	}

	public static void main(String[] args) throws Exception {
		new Game();
	}
}
