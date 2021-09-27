package Style;

import java.awt.Font;

import javax.swing.JButton;

public class ButtonStyle {
	Font font;
	public ButtonStyle() {
		// TODO Auto-generated constructor stub
		
	}
		
	public void setButton(JButton jButton) {
		font = new Font("맑은 고딕", Font.BOLD, 20);
		jButton.setBorderPainted(false);
		jButton.setContentAreaFilled(false);
		jButton.setFocusPainted(false);
		jButton.setFont(font);
	}
}
