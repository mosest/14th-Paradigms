import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

import javax.swing.JTextField;

public class AutoCompleteField extends JTextField implements KeyListener {
	// Member variables!
	TreeSet<String> lexicon;
	
	AutoCompleteField() {
		// Call JTextField's constructor
		super();
		
		// Call addKeyListener
		addKeyListener(this);
		
		// Fill in lexicon
		lexicon = new TreeSet<String>();
		Scanner s;
		try {
			s = new Scanner(new File("lexicon.txt"));
			while (s.hasNextLine()) {
				String word = s.nextLine();
				lexicon.add(word);
			}
		} catch (FileNotFoundException e) {}
	}

	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}