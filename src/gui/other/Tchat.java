package gui.other;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Tchat extends JPanel {
	
	JTextArea textArea;
	JTextField textField;
	JButton send;
	String pseudo;
	
	public Tchat(String pseudo) {
		this.pseudo = pseudo + " : ";
		createElements();
		generateTchat();
	}
	
	private void createElements() {
		textArea = new JTextArea();
		textArea.setEditable(true);
		textArea.setBorder(BorderFactory.createEtchedBorder());
		textArea.setLineWrap(true);
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(400, 30));
		send = new JButton("Envoyer");
		
	}

	private void generateTchat() {
		setLayout(new BorderLayout());
		
		JScrollPane sp = new JScrollPane(textArea);
		sp.setPreferredSize(new Dimension(400, 300));
		add(sp, BorderLayout.CENTER);
		
		JPanel p = new JPanel(); {
			p.add(new JLabel(this.pseudo));
			p.add(textField);
			p.add(send);
		}
		add(p, BorderLayout.SOUTH);
	}
	
	private void createController() {
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.append(textField.getText());
				textField.setText("");
			}
		});
	}
}
