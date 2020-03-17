import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



//import whiteboardgui.PencileStroke;

public class Canvas1 extends JPanel {
	
	private List<Shapes> shapeList = new ArrayList<>();
	private Shapes currShape;
	String currType = "l";
	Color currColor = Color.black;
	int initialX, initialY,currentX, currentY;
	private String text1, currText;
	private int currStroke;
	private String text;
	
	private HashMap<String,Drawing> cData = new HashMap<>();
	private List<Shapes> finalShapeList = new ArrayList<>();
	private List<Shapes> recShapeList = new ArrayList<>();
	
	public List<Shapes> getFinalShapeList() {
		return finalShapeList;
	}

	public void setFinalShapeList(List<Shapes> finalShapeList) {
		this.finalShapeList = finalShapeList;
	}

	public List<Shapes> getRecShapeList() {
		return recShapeList;
	}

	public void setRecShapeList(List<Shapes> recShapeList) {
		this.recShapeList = recShapeList;
	}

	public Canvas1() {	
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				initialX = e.getX();
				initialY = e.getY();
				if(currType == "c") {
					currShape = new Circle(initialX, initialY, e.getX(), e.getY(), currColor, currType, currStroke, null);
					shapeList.add(currShape);
				}
				else if(currType=="l") {
					currShape = new Line(initialX, initialY, e.getX(), e.getY(), currColor, currType, currStroke, null);
					shapeList.add(currShape);
				}
				else if(currType=="r") {
					currShape = new Rectangle(initialX, initialY, e.getX(), e.getY(), currColor, currType, currStroke, null);
					shapeList.add(currShape);
				}
				else if(currType=="o") {
					currShape = new Oval(initialX, initialY, e.getX(), e.getY(), currColor, currType, currStroke, null);
					shapeList.add(currShape);
				}
				else if(currType=="ol") {
					currShape = new FillOval(initialX, initialY, e.getX(), e.getY(), currColor, currType, 1, null);
					shapeList.add(currShape);
				}
				else if(currType=="cl") {
					currShape = new FillCircle(initialX, initialY, e.getX(), e.getY(), currColor, currType, 1, null);
					shapeList.add(currShape);
				}
				else if(currType=="rl") {
					currShape = new FillRectangle(initialX, initialY, e.getX(), e.getY(), currColor, currType, 1, null);
					shapeList.add(currShape);
				}
				else if(currType=="text") {
					String text = JOptionPane.showInputDialog("Please input the text you want!");
					if(text == null) {
						System.out.println("\n");
					}else {
					currShape = new Textpanel(initialX, initialY, e.getX(), e.getY(), currColor, currType, 1, text);
					finalShapeList.add(currShape);
					whiteboardSwing.broadcastDraw(new Drawing(finalShapeList,"Manager",1));
					repaint();
					}
				}
			}
				
	            public void mouseReleased (MouseEvent e) {
	                super.mouseReleased(e);
	                finalShapeList.add(currShape);
	                Shapes newShape;
	                if(recShapeList.size()>0) {
	                	Iterator itr = recShapeList.iterator();
	    				
	            		while(itr.hasNext()) {
	            			newShape = (Shapes) itr.next();
	            			finalShapeList.add(newShape);
	            		}
	                }
	                shapeList.clear();
	                recShapeList.clear();
	                //System.out.println("chodne pe chala" + finalShapeList.size() + " " + shapeList.size());
	                whiteboardSwing.broadcastDraw(new Drawing(finalShapeList, "Manager", 1));
	                repaint();
	            }
			
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				currentX = e.getX(); 
				currentY = e.getY();
				
				if (currType == "d") {
				
					currShape = new Draw(initialX, initialY, currentX, currentY, currColor, currType, currStroke, null);
					whiteboardSwing.broadcastDraw(new Drawing(getRecShapeList(),"Manager", 0));
					
					initialX = currentX;
					initialY = currentY;
					recShapeList.add(currShape);
					repaint();
					
				} else if (currType == "e") {
					currShape = new Draw(initialX, initialY, currentX, currentY, Color.white, currType, currStroke, null);
					whiteboardSwing.broadcastDraw(new Drawing(getRecShapeList(),"Manager", 0));
					
					initialX = currentX;
					initialY = currentY;
					recShapeList.add(currShape);
					repaint();

				} 
				else {
					currShape.setX2(currentX);
					currShape.setY2(currentY);
					whiteboardSwing.broadcastDraw(new Drawing(getShapeList(),"Manager", 0));
					repaint();
				}
			}
		});
	}
	
	public List<Shapes> getShapeList() {
        return this.shapeList;
    }

    public void setShapeList(List<Shapes> shapeList) {
        this.shapeList = shapeList;
    }
	
	public void saveFile(String filePath) {
	    try {
	        FileOutputStream fos = new FileOutputStream(filePath);
	        ObjectOutputStream oos;
	        oos = new ObjectOutputStream(fos);
	        oos.writeObject(finalShapeList);
	        // closing streams
	        oos.close();
	        fos.close();
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
	}

	public void openFile(String filePath) {
	    try {
	        FileInputStream fis = new FileInputStream(filePath);
	        ObjectInputStream ois = new ObjectInputStream(fis);
	        finalShapeList = (ArrayList<Shapes>) ois.readObject();
	        whiteboardSwing.broadcastDraw(new Drawing(getFinalShapeList(),"Manager", 1));
	        repaint();
	        // closing streams
	        ois.close();
	        fis.close();
	    } catch (Exception e) {
	        System.out.println(e);
	    }
	}
	
	synchronized public void getClientDrawing(Drawing drawing){
		this.finalShapeList = (drawing.getShapesList());
        this.recShapeList.clear();
        cData.remove(drawing.getSender());
        whiteboardSwing.broadcastDraw(drawing);
        repaint();
    }
	
	 synchronized public void getTempDrawing(Drawing drawing) {
	        cData.put(drawing.getSender(),drawing);
	        whiteboardSwing.broadcastDraw(drawing);
	        repaint();
	 }

	public void newFile() {
	    finalShapeList.clear();
	    shapeList.clear();
	    recShapeList.clear();
	    
	    repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Shapes newShape;
		
		
		Iterator itr2 = finalShapeList.iterator();
        while (itr2.hasNext()) {
            newShape = (Shapes) itr2.next();
            newShape.draw(g);

        }
        for(Drawing drawing:cData.values()){
            Iterator itr3 = drawing.getShapesList().iterator();
            while (itr3.hasNext()) {
                newShape = (Shapes) itr3.next();
                newShape.draw(g);

            }
        }
        Iterator itr = shapeList.iterator();
		
		while(itr.hasNext()) {
			newShape = (Shapes) itr.next();
			newShape.draw(g);
		}
        Iterator itr4 = recShapeList.iterator();
		
		while(itr4.hasNext()) {
			newShape = (Shapes) itr4.next();
			newShape.draw(g);
		}
	}
	

	public void setShapeType(String c) {
		// TODO Auto-generated method stub
		currType = c;
	}
	
	public void setColor(Color c) {
		currColor = c;
	}
	

	public void setStroke(int currStroke) {
        this.currStroke = currStroke;
//        repaint();
    }
	
	public void setText(String text) {
		// TODO Auto-generated method stub
		currText = text;
	}
	
	
	

}
