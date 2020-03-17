import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Draw extends Shapes {

	public Draw(int x, int y, int x2, int y2, Color color, String type, int stroke, String text) {
		super(x, y, x2, y2, color, type, stroke, text);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		
		Graphics2D graphic = (Graphics2D) g;
		graphic.setStroke(new BasicStroke(getStroke()));
		graphic.setColor(getColor());
		graphic.drawLine(getX(), getY(), getX2(), getY2());
		graphic.setStroke(new BasicStroke(1));
		
	}

}
