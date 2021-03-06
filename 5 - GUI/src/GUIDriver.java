import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class GUIDriver {
	public static void main(String[] args) {
		// Step 1. Frame! ----------------------------------------------------
		// Here's the frame itself!
		JFrame newFrame = new JFrame("Frame Time");
		newFrame.setSize(400,200);
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Let's set up the layout (Grid)
		newFrame.setLayout(new GridLayout(1,3,15,15));
		
		// Step 2. Components! -----------------------------------------------
		// Here's the static text field!
		JLabel label = new JLabel("Static text field!");
		newFrame.add(label);
		
		// Here's the button!
		JButton button = new JButton("CLICK ME");
		//button.setSize(50,50);
		newFrame.add(button);
		
		// Make the button do something...
		MyListener myEar = new MyListener();
		button.addActionListener(myEar);
		
		// Here's the JTextField!
		
		//JTextField textField = new JTextField("Yeah!");
		//textField.setSize(50,50);
		
		AutoCompleteField lexField = new AutoCompleteField();
		
		newFrame.add(lexField);
		
		// Step 3. MAKE SHIT VISIBLE ----------------------------------------
		// Let's show the frame now :^)
		//newFrame.pack();			// Make sure the frame fits the contents!
		newFrame.setVisible(true);
	}
}