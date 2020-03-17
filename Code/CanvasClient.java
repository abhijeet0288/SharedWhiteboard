import java.awt.Color;
import java.awt.Graphics;
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

import javax.swing.*;

public class CanvasClient extends JPanel {

    private List<Shapes> shapeList = new ArrayList<>();
    private Shapes currShape;
    private String currType="l", currText;
    int initialX, initialY, currentX, currentY;
    public int currStroke;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    private Color currColor=Color.BLACK;
    private String clientName;
    
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

	public CanvasClient() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
//                if (currType == "c") {
//                    currShape = new Circle(e.getX(), e.getY(), e.getX(), e.getX(), currColor, currType);
//                    shapeList.add(currShape);
//                }
//                if (currType == "o") {
//                    currShape = new Oval(e.getX(), e.getY(), e.getX(), e.getX(), currColor, currType);
//                    shapeList.add(currShape);
//                } else if (currType == "l") {
//                    currShape = new Line(e.getX(), e.getY(), e.getX(), e.getY(), currColor, currType);
//                    shapeList.add(currShape);
//                } else if (currType == "r") {
//                    currShape = new Rectangle(e.getX(), e.getY(), e.getX(), e.getY(), currColor, currType);
//                    shapeList.add(currShape);
//                }
            	
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
					currShape = new Textpanel(initialX, initialY, e.getX(), e.getY(), currColor, currType, currStroke, text);
					finalShapeList.add(currShape);
					Client.sendDrawingToServer(new Drawing(finalShapeList,getClientName(),1));
					repaint();
					}
					
				}

            }
            @Override
            public void mouseReleased(MouseEvent e) {
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
                Client.sendDrawingToServer(new Drawing(finalShapeList,getClientName(),1));
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
//                currShape.setX2(e.getX());
//                currShape.setY2(e.getY());
//                Client.sendDrawingToServer(new Drawing(shapeList,getClientName()));
//                repaint();
                
                currentX = e.getX();
				currentY = e.getY();
				
				if (currType == "d") {
					currShape = new Draw(initialX, initialY, currentX, currentY, currColor, currType, currStroke, null);
					Client.sendDrawingToServer(new Drawing(getRecShapeList(),getClientName(), 0));
					initialX = currentX;
					initialY = currentY;
					recShapeList.add(currShape);
					repaint();
					
				} else if (currType == "e") {
					currShape = new Draw(initialX, initialY, currentX, currentY, Color.white, currType, currStroke, null);
					Client.sendDrawingToServer(new Drawing(getRecShapeList(),getClientName(), 0));
					initialX = currentX;
					initialY = currentY;
					recShapeList.add(currShape);
					repaint();
				}
				else {
					currShape.setX2(currentX);
					currShape.setY2(currentY);
					Client.sendDrawingToServer(new Drawing(getShapeList(),getClientName(), 0));
					repaint();
				}
            }
        });
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
        Iterator itr4 = recShapeList.iterator();
		
		while(itr4.hasNext()) {
			newShape = (Shapes) itr4.next();
			newShape.draw(g);
		}
		Iterator itr = shapeList.iterator();
        while (itr.hasNext()) {
            newShape = (Shapes) itr.next();
            newShape.draw(g);
        }

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
            oos.writeObject(shapeList);
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
            shapeList = (ArrayList<Shapes>) ois.readObject();
            repaint();
            // closing streams
            ois.close();
            fis.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    synchronized public void getManagerBoard(Drawing drawing){
    	this.finalShapeList = drawing.getShapesList();
    	//this.recShapeList.clear();
        this.cData.remove(drawing.getSender());
        //Client.sendDrawingToServer(drawing);
        repaint();
    }
    
    public void currDrawing(Drawing drawing) {
//      if (this.recShapeList.size() > 0) {
//          Shapes newShape;
//          Iterator itr3 = shapeList.iterator();
//          while (itr3.hasNext()) {
//              newShape = (Shapes) itr3.next();
//              this.recShapeList.add(newShape);
//          }
//
//      } else {
//          this.recShapeList = shapeList;
//      }
      cData.put(drawing.getSender(),drawing);
      repaint();
  }

    public void newFile() {
        if (shapeList.size() > 0) {
            String[] options = {"YES", "NO"};
            int x = JOptionPane.showOptionDialog(null, "Do you want to save the file?",
                    "Click a button",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            System.out.println(x);
        }
    }


    public void setShapeType(String c) {
        // TODO Auto-generated method stub
        currType = c;
    }

    public void setColor(Color c) {
        // TODO Auto-generated method stub
        currColor = c;
    }
    
    public void setStroke(int currStroke) {
        // TODO Auto-generated method stub
        this.currStroke = currStroke;
    }
    
    public void setText(String text) {
		// TODO Auto-generated method stub
		currText = text;
	}

}
