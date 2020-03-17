import java.awt.Color;
import java.awt.Graphics;

public class FillOval extends Shapes {

	public FillOval(int x, int y, int x2, int y2, Color color, String type, int stroke, String text) {
		// TODO Auto-generated constructor stub
		super(x, y, x2, y2, color, type, stroke, text);
	}
	
	

	@Override
	public void draw(Graphics g) {
		int cornerX = Math.min(getX(), getX2());
		int cornerY = Math.min(getY(), getY2());
		int width = Math.abs(getX() - getX2());
		int height = Math.abs(getY() - getY2());
		String type = getType();
		
		g.setColor(getColor());
		g.fillOval(cornerX, cornerY, width, height);
		
	}

}

