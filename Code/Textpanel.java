import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;


public class Textpanel extends Shapes {
	
	private String text;
  
	public Textpanel(int x, int y, int x2, int y2, Color color, String type, int stroke, String text) {
		super(x, y, x2, y2, color, type, stroke, text);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		//String string1 = JOptionPane.showInputDialog("Please input the text you want!");
		String text = getText().trim();
		if(!text.equals(null)) {
		g.setColor(getColor());
		g.drawString(getText(), getX2(),getY2());
		}
	}
	
}