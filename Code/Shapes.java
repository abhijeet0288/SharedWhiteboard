import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shapes implements Serializable {
	
	private int x,y;
	private int x2;
	private int y2;
	private int y3;
	private int z;
	private Color color;
	private String type, text;
	private int stroke;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public int getStroke() {
		return stroke;
	}

	public void setStroke(int stroke) {
		this.stroke = stroke;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	


	public Shapes(int x, int y, int x2, int y2, Color color, String type, int stroke, String text) {
		setX(x);
		setY(y);
		setX2(x2);
		setY2(y2);
		setColor(color);
		setType(type);
		setStroke(stroke);
		setText(text);
	}

//	public Shapes(String x3, int y3, int z) {
//		// TODO Auto-generated constructor stub
//		setText(x3);
//		setY3(y3);
//		setZ(z);
//		
//		
//	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	public int getY3() {
		return y3;
	}
	
	public void setY3(int y3) {
		this.y3 = y3;
	}
	
	public int getZ() {
		return z;
	}
	
	public void setZ(int z) {
		this.z = z;
	}

	public abstract void draw(Graphics g);

}
