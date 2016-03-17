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
		String start = getText();
		
		// Replace shit		
		if (k.getKeyChar() != 8) {
			//System.out.println("Calling suggestAWord(" + start + ")!");
			setText(suggestAWord(start));		
		}	
		
		// Select extra non-typed part of text!
		select(start.length(), getText().length());	
	}

	// OKAY GASHLER I'LL USE YOUR METHOD >:(
	public void keyTyped(KeyEvent k) {	
		
	}
	
	public String suggestAWord(String start) {
		String suggestion;
		String closestWord = lexicon.ceiling(start);
		
		// THREE SCENARIOS:
		// (1) start has a suggestion and it starts with start!
		// (2) start has a suggestion but it doesn't start with start!
		// (3) start has no suggestion in lexicon :(
		
		if (!closestWord.equals("")) { 		
			if (closestWord.length() >= start.length() && !closestWord.substring(0, start.length()).equalsIgnoreCase(start)) 
				suggestion = start;			// 1
			else suggestion = closestWord;	// 2
		} else suggestion = start; 			// 3
		
		// Report and return!
		return suggestion;
	}
}