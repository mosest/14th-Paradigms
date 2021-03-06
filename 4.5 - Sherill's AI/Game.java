import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

public class Game extends JFrame implements ActionListener {
	Model model; 
	//Model new_model;
	Controller controller;

	public Game() throws Exception {
		this.model = new Model();
		//this.new_model = new Model(this.model);
		controller = new Controller(this.model);
		View view = new View(this.model);
		view.addMouseListener(controller);
		this.setTitle("Hit Me Not!");
		this.setSize(500, 500);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		new Timer(50, this).start(); // Indirectly calls actionPerformed at regular intervals
		
		// Printouts! Testing deep copy
		System.out.println("Original Model: " + this.model);
		System.out.println("Original Bird: " + this.model.bird);
		System.out.println("Original Sprite List: " + this.model.sprites);

		/*System.out.println("\nCopy Model: " + this.new_model);
		System.out.println("Copy Bird: " + this.new_model.bird);
		System.out.println("Copy Sprite List: " + this.new_model.sprites);*/
		System.out.println();
	}

	public void actionPerformed(ActionEvent evt) {
		try {
			this.controller.update();
			this.model.update();	
			//this.new_model.update();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		repaint(); // Indirectly calls View.paintComponent
	}

	public static void main(String[] args) throws Exception {
		new Game();
	}
}
