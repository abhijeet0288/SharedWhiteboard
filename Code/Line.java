import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Line extends Shapes {

	public Line(int x, int y, int x2, int y2, Color color, String type, int stroke, String text) {
		super(x, y, x2, y2, color, type, stroke, text);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		int strokeWidth = getStroke();
		Graphics2D g2 = (Graphics2D) g;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(strokeWidth));
		g.drawLine(getX(), getY(), getX2(), getY2());
		g2.setStroke(oldStroke);
		
		
	}

}
