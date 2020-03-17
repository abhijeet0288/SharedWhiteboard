import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Rectangle extends Shapes {

	public Rectangle(int x, int y, int x2, int y2, Color color, String type, int stroke, String text) {
		super(x, y, x2, y2, color, type, stroke, text);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		int cornerX = Math.min(getX(), getX2());
		int cornerY = Math.min(getY(), getY2());
		int width = Math.abs(getX() - getX2());
		int height = Math.abs(getY() - getY2());
		String type = getType();
		
		g.setColor(getColor());
		
		int strokeWidth = getStroke();
		Graphics2D g2 = (Graphics2D) g;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(strokeWidth));
		g2.drawRect(cornerX, cornerY, width, height);
		g2.setStroke(oldStroke);
		
	}

}
