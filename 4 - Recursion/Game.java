import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Game extends JFrame implements ActionListener {
	Model model;
	Model model_clone;
	Controller controller;
	Controller controller_clone;

	public Game() throws Exception {
		this.model = new Model();
		//this.model_clone = new Model(this.model);
		controller = new Controller(this.model);
		//controller_clone = new Controller(this.model_clone);
		View view = new View(this.model);
		view.addMouseListener(controller);
		//view.addMouseListener(controller_clone);
		this.setTitle("!! Snappy Bird !!");
		this.setSize(500, 500);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		new Timer(33, this).start(); // Indirectly calls actionPerformed at regular intervals
		
		// Print-out statements
		System.out.println("Original: " + model + " and " + model.pigeon);
		//System.out.println("Clone: " + model_clone + " and " + model_clone.pigeon);
	}

	public void actionPerformed(ActionEvent evt) {
		try {
			this.controller.update();
			//this.model_clone.update();
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
