package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Style.ButtonStyle;

public class TALK_Title {
	private JPanel body, exit, title;
	private JLabel titleName;
	private JButton exitBut;
	private ButtonStyle buttonStyle;
	
	public TALK_Title(JFrame jFrame, String title_name) {
		buttonStyle = new ButtonStyle();
		Font font = new Font("궁서 맑음", Font.ITALIC, 20);
		body = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		body.setPreferredSize(new Dimension(300, 500));
		body.setBackground(Color.white);
		exit = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		title = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		title.setPreferredSize(new Dimension(300, 30));
		title.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
		title.setOpaque(false);
		body.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		exit.setPreferredSize(new Dimension(300, 40));
		exitBut = new JButton("x");
		buttonStyle.setButton(exitBut);
		exitBut.setBorderPainted(false);
		exitBut.setContentAreaFilled(false);
		exitBut.setFocusPainted(false);
		exitBut.setFont(font);
		exit.setOpaque(false);
		exit.add(exitBut);
		titleName = new JLabel(title_name);
		titleName.setFont(font);
		title.add(titleName);
		body.add(exit);
		body.add(title);
		
		exitBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jFrame.dispose();
			}
		});
		
	}

	public JPanel getBody() {
		return body;
	}

	public void setBody(JPanel body) {
		this.body = body;
	}

	public JLabel getTitleName() {
		return titleName;
	}

	public void setTitleName(JLabel titleName) {
		this.titleName = titleName;
	}

	public JButton getExitBut() {
		return exitBut;
	}

	public void setExitBut(JButton exitBut) {
		this.exitBut = exitBut;
	}
	
	
	
}
