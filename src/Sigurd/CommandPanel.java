package Sigurd;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.Icon.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author Peter Major
 *	
 * @Summary contains a JPanel named panel that contains the command line
 * 
 */
public class CommandPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	JTextField commandLine;
	JButton enterButton;
	Controler controler;

	/**
	 * @Summary Constructor 
	 */
	public CommandPanel() {
		controler = Game.GetControlerInstance();
		
		AddCommandLine();
		AddEnterButton();
	}
	
	/**
	 * @Summary adds the command line to the main panel
	 */
	void AddCommandLine() {
		commandLine = new JTextField(20);
		
		//action listener for when user presses return key
		commandLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controler.ExicuteCommand(commandLine.getText());
				commandLine.setText("");
			}
		});
		
		//adding listener for when someone types something into the text box
		commandLine.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {}
			public void removeUpdate(DocumentEvent e) {}
			public void insertUpdate(DocumentEvent e) {}
		});
		
		add(commandLine);
	}
	
	/**
	 * @Summary adds the enter button to the main panel
	 */
	void AddEnterButton() {
		enterButton = new JButton("Enter");
		
		//when button is pressed
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controler.ExicuteCommand(commandLine.getText());
				commandLine.setText("");
			}
		});
		
		add(enterButton);
	}
	
	/**
	 * @Summary adds a box under the text feild when there are autocomplete options
	 */
	void AddAutoCompleatBox() {
		
	}
	
	/**
	 * testing main
	 * @param args
	 */
	public static void main(String[] args) {
		CommandPanel cpannel = new CommandPanel();
		
		JFrame frame = new JFrame();
		frame.add(cpannel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}