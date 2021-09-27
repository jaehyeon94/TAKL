package GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TALK_Diglog extends JFrame {

	JLabel jLabel = new JLabel(); 
	JButton okButton = new JButton("OK");
	
	public  TALK_Diglog(String String) {
		setLayout(new FlowLayout());
		jLabel.setText(String);
		add(jLabel);
		add(okButton);
		setSize(200,100);
		setLocation(700, 300);
		
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
		
	}
}
