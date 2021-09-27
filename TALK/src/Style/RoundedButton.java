package Style;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class RoundedButton extends JButton {

	public RoundedButton(ImageIcon icon) {
	
		setButton(icon);
	}
	
	public void setButton(ImageIcon icon) {
		BufferedImage bi = new BufferedImage(147, 147, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		g.drawImage(icon.getImage(), 0, 0, 147, 147, null);
		ImageIcon newIco = new ImageIcon(bi);
		setIcon(newIco);
	}

	
}
