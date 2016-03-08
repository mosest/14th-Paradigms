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
	String word = "";
	
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
		} catch (FileNotFoundException e) {
			System.out.println("houston we have a lexicon that wont open");
		}
	}

	public void keyPressed(KeyEvent k) {
		
	}

	public void keyReleased(KeyEvent k) {
		if (k.getKeyChar() != 8) setText(suggestAWord(getText()));
	}

	// OKAY GASHLER I'LL USE YOUR FUCKIN METHOD
	public void keyTyped(KeyEvent k) {
		
	}
	
	public String suggestAWord(String start) {
		String suggestion = start;
		
		// Calculate the actual suggestion using lexicon
		if (!lexicon.isEmpty()) suggestion = lexicon.higher(start);
		
		// Report and return!
		return suggestion;
	}
}