import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
//import java.awt.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JTextArea;

import java.awt.Choice;
import java.awt.TextField;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Button;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Label;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.TextArea;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.List;

import java.awt.FontMetrics;
import java.awt.Frame;

import javax.swing.JSlider;
import java.awt.event.MouseMotionAdapter;

public class whiteboardSwing {

	private static JFrame frame;
	private List<Shapes> shapeList = new ArrayList<>();
	private Shapes currShape;
	private char currType;
	private String currentFilePath="";
	private Color currColor = Color.black;
	private static List<ClientModel> clients = new ArrayList<>();
	int initialX, initialY,currentX, currentY;
	public static Canvas1 canvas = new Canvas1();
	public static ServerSocket ss = null;
	public JButton sendButton, removeButton;
	public JPanel chatPanel;
	private int myStroke;
	public static JTextArea textBox, textArea, clientsArea;
	public static JLabel connectedClients;
	public static JTextField messageArea;
	public static boolean clientExists = false;
	public static int allow;
	public static JComboBox connectedClientsArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			
//			public void run() {
////				try {
//					whiteboardSwing window = new whiteboardSwing();
//					
//			}
//			
//		});
		
		try {
//			whiteboardSwing window = new whiteboardSwing();
			
			
			int socketNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter the port number"));
			
            ss = new ServerSocket(socketNumber);
//            whiteboardSwing window = new whiteboardSwing();
            EventQueue.invokeLater(new Runnable() {
    			
    			public void run() {
//    				try {
    					whiteboardSwing window = new whiteboardSwing();
    					
    			}
    			
    		});
            
            while (true) {
                Socket chatSocket = null;
                Socket drawSocket = null;
                try {

                    chatSocket = ss.accept();
                    drawSocket = ss.accept();


                    ObjectOutputStream chatOut = new ObjectOutputStream(new BufferedOutputStream(chatSocket.getOutputStream()));
                    chatOut.flush();
                    ObjectInputStream chatOis = new ObjectInputStream(new BufferedInputStream(chatSocket.getInputStream()));

                    Message msg=(Message) chatOis.readObject();


                  //  System.out.println(msg.getSender() +" wants to join");
                    clientExists = false;
                    for (ClientModel client: clients) {
                    	if (client.getName().equalsIgnoreCase(msg.getSender())) {
                    		clientExists = true;
                    	}
                    }
                    if (clientExists == false) {
                    	allow = JOptionPane.showConfirmDialog(frame, msg.getSender() + " wants to join");
                    
                    
	                    if (allow == JOptionPane.YES_OPTION) {
	                    	
//	                    	chatOut.writeUTF("Connection successful");
//	                    	chatOut.flush();
	                    	
	                    	ObjectOutputStream drawOut = new ObjectOutputStream(new BufferedOutputStream(drawSocket.getOutputStream()));
	                        drawOut.flush();
	                        ObjectInputStream drawOis = new ObjectInputStream(new BufferedInputStream(drawSocket.getInputStream()));
	
	                        ClientModel cm = new ClientModel(msg.getSender(), drawSocket, chatSocket, drawOis, chatOis, drawOut, chatOut);
	                        clients.add(cm);
	                        
	                        List<String> clientNames = new ArrayList<>();
	                        
	                        for (ClientModel client: clients) {
	                        	clientNames.add(client.getName());
	                        }
	                        
	                        Message msg2 = new Message("Manager", cm.getName() + " has joined", clientNames);
	                        broadcastChat(msg2);
	                        
	                        chatOut.writeObject(msg2);
	                        chatOut.flush();
	                        
	                        clientsArea.append(msg.getSender() + "\n");
	                        connectedClientsArea.addItem(msg.getSender());
	                        
	                        
	
	                        List<Shapes> sl = canvas.getFinalShapeList();
	                        String sender = "Manager";
	                        Drawing drawing = new Drawing(sl, sender, 1);
	                        drawOut.writeObject(drawing);
	                        drawOut.flush();
	
	                        ServerDrawListener sdl = new ServerDrawListener(canvas, drawOis,cm);
	                        sdl.start();
	
	                        ServerChatListener scl = new ServerChatListener(chatOis);
	                        scl.start();
	                    }
	                    else {
//	                    	chatOut.writeUTF("Connection request was refused by the manager");
//	                    	chatOut.flush();
	                    	Message msg2 = new Message("Manager", "Connection request was refused by the manager", null);
	                    	chatOut.writeObject(msg2);
	                    	chatOut.flush();
	                    }
                    }
                    
                    else {
//                    	chatOut.writeUTF("User Already Exists");
//                    	chatOut.flush();
                    	Message msg2 = new Message("Manager", "User Already Exists", null);
                    	chatOut.writeObject(msg2);
                    	chatOut.flush();
                    }
                   
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

        } catch (Exception e) {
            //System.out.println(e + "" + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Port error!", JOptionPane.PLAIN_MESSAGE);

        }
	}

	/**
	 * Create the application.
	 */
	public whiteboardSwing() {
		initialize();
	}
	
	public static void broadcastDraw(Drawing drawing) {
        try {

//            List<Shapes> shapeList = canvas.getShapeList();
            //System.out.println("Shapes are: "+shapeList);
            //System.out.println(bDraw.get(0));

            if (clients.size() > 0) {
                //System.out.println("Broadcasting drawings from: "+drawing.getSender());
                for (ClientModel c : clients) {
                    if (!c.getName().equals(drawing.getSender())) {
                        c.getDrawOut().reset();
                        c.getDrawOut().writeObject(drawing);
                        c.getDrawOut().flush();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
	
	

    public static void broadcastChat(Message msg){
        try {


            if (clients.size() > 0) {
            //    System.out.println("Broadcasting message from: "+msg.getSender());
                for (ClientModel c : clients) {
                    if (!c.getName().equals(msg.getSender())) {
                        c.getChatOut().reset();
                        c.getChatOut().writeObject(msg);
                        c.getChatOut().flush();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void kickClient(ClientModel cm){
      //  System.out.println("kicking out: "+cm.getName());
    	try {
    		if (clients.contains(cm)) {
        		clients.remove(cm);
        		cm.getcSocket().close();
        		cm.getdSocket().close();
                clientsArea.setText(null);
                List<String> clientNames = new ArrayList<>();
                for (ClientModel client: clients) {
                	clientsArea.append(client.getName() + "\n");
                	clientNames.add(client.getName());
                }
                connectedClientsArea.removeItem(cm.getName());
                Message msg = new Message("Manager", cm.getName() + " has left", clientNames);
                broadcastChat(msg);
        	}
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    	
        
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
//		Canvas1 canvas = new Canvas1();
	
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.BLACK);
		frame.getContentPane().setFont(new Font("Times New Roman", Font.BOLD, 20));
		frame.setBounds(100, 100, 1500, 728);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		
		panel.setBounds(0, 5, 325, 43);
		frame.getContentPane().add(panel);
        
        JPanel menuPanel = new JPanel();
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        
        connectedClients = new JLabel("Connected Clients");
        connectedClients.setBounds(1064, 330, 200, 30);
        
        clientsArea = new JTextArea();
        JScrollPane clientScrollBar = new JScrollPane(clientsArea);
        clientsArea.setBounds(980, 360, 300, 250);
        clientsArea.setEditable(false);
        
        List<String> clientNames = new ArrayList<>();
        for (ClientModel client: clients) {
        	clientNames.add(client.getName());
        }
        
        removeButton = new JButton("Kick Out");
        
        removeButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {

		        //canvas.setShapeType("r");
//                canvas.saveFile("abcd.txt");
//		    	List<String> clientNames = new ArrayList<>();
//		    	
//		    	for (ClientModel client: clients) {
//		    		clientNames.add(client.getName());
//		    	}
//		        String chatMessage = messageArea.getText();
//		        Message msg = new Message("Manager", chatMessage, clientNames);
//		        messageArea.setText(null);
////                textBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//		        textBox.append("You said: " + chatMessage + "\n");
		    	
		    	String selectedClient = (String) connectedClientsArea.getSelectedItem();
		    	ClientModel selectedClientModel = null;
		    	
		    	for (ClientModel client: clients) {
		    		if (client.getName().equals(selectedClient)) {
		    			selectedClientModel = client;
		    			break;
		    		}
		    	}
		    	
		    	if (selectedClientModel != null) {
		    		kickClient(selectedClientModel);
		    	}
//		        try {
//		    			broadcastChat(msg);
//		    		
//		            
//		        } catch (Exception ex) {
//		            System.out.println(ex);
//		        }
		    }

		});
        
        connectedClientsArea = new JComboBox();
        
        connectedClientsArea.setBounds(977, 614, 300, 50);
        removeButton.setBounds(850, 620, 100, 30);
        
        frame.getContentPane().add(connectedClients);
        frame.add(clientsArea);
        frame.add(connectedClientsArea);
        frame.add(removeButton);
        
		
		Button button_1 = new Button("New");
		panel.add(button_1);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				   String[] options = {"YES", "NO"};
			        int x = JOptionPane.showOptionDialog(null, "Do you want to save the current file?",
			                "Click a button",
			                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			        //System.out.println(x);
			        if(x==0 && currentFilePath.equals("")) {
			        	JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
						
						int rvalue = fc.showSaveDialog(null);
						
						if(rvalue == JFileChooser.APPROVE_OPTION) {
							File selectedFile = fc.getSelectedFile();
							//System.out.println(selectedFile.getAbsolutePath());
							currentFilePath=selectedFile.getAbsolutePath();
							canvas.saveFile(selectedFile.getAbsolutePath());
						}
						currentFilePath="";
			        	canvas.newFile();
			        	broadcastDraw(new Drawing(canvas.getFinalShapeList(), "Manager", 1));
			        }
			        else if(x==0 && (!currentFilePath.equals(""))) {
			        	canvas.saveFile(currentFilePath);
			        	currentFilePath="";
			        	canvas.newFile();
			        	broadcastDraw(new Drawing(canvas.getFinalShapeList(), "Manager", 1));
			        }
			        else {
			        	currentFilePath="";
			        	canvas.newFile();
			        	broadcastDraw(new Drawing(canvas.getFinalShapeList(), "Manager", 1));
			        }
				
			}
		});
		button_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		button_1.setBackground(Color.WHITE);
		
		Button button = new Button("Open");
		panel.add(button);
		button.setBackground(Color.WHITE);
		button.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		Button button_2 = new Button("Save");
		panel.add(button_2);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentFilePath.equals("")) {
				JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				
				int rvalue = fc.showSaveDialog(null);
				
				if(rvalue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fc.getSelectedFile();
					//System.out.println(selectedFile.getAbsolutePath());
					currentFilePath=selectedFile.getAbsolutePath();
					canvas.saveFile(selectedFile.getAbsolutePath());
				}
				}
				else {
					canvas.saveFile(currentFilePath);
				}
			}
		});
		button_2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		button_2.setBackground(Color.WHITE);
		
		Button button_3 = new Button("SaveAs");
		panel.add(button_3);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				
				int rvalue = fc.showSaveDialog(null);
				
				if(rvalue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fc.getSelectedFile();
					//System.out.println(selectedFile.getAbsolutePath());
					currentFilePath=selectedFile.getAbsolutePath();
					canvas.saveFile(selectedFile.getAbsolutePath());
				}
				
			}
		});
		button_3.setFont(new Font("Times New Roman", Font.BOLD, 18));
		button_3.setBackground(Color.WHITE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				
				int rvalue = fc.showOpenDialog(null);
				
				if(rvalue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fc.getSelectedFile();
					//System.out.println(selectedFile.getAbsolutePath());
					currentFilePath=selectedFile.getAbsolutePath();
					canvas.openFile(selectedFile.getAbsolutePath());
				
				}
			}
		});
		
		
		canvas.setBackground(Color.WHITE);
		canvas.setBounds(107, 64, 855, 487);
		
		frame.getContentPane().add(canvas);
		
	
		
		Button button_6 = new Button("");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(Color.white);
			}
		});
		button_6.setBackground(new Color(255, 255, 255));
		button_6.setBounds(10, 282, 38, 27);
		frame.getContentPane().add(button_6);
		
		Button button_7 = new Button("");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(Color.black);
			}
		});
		button_7.setBackground(Color.BLACK);
		button_7.setBounds(54, 518, 38, 27);
		frame.getContentPane().add(button_7);
		
		Button button_8 = new Button("");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(new Color(0, 0, 128));
			}
		});
		button_8.setBackground(new Color(0, 0, 128));
		button_8.setBounds(54, 282, 38, 27);
		frame.getContentPane().add(button_8);
		
		Button button_9 = new Button("");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(new Color(0, 0, 255));
			}
		});
		button_9.setBackground(new Color(0, 0, 255));
		button_9.setBounds(10, 518, 38, 27);
		frame.getContentPane().add(button_9);
		
		Button button_10 = new Button("");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(new Color(0, 128, 0));
			}
		});
		button_10.setBackground(new Color(0, 128, 0));
		button_10.setBounds(10, 315, 38, 27);
		frame.getContentPane().add(button_10);
		
		Button button_11 = new Button("");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(new Color(0, 128, 128));
			}
		});
		button_11.setBackground(new Color(0, 128, 128));
		button_11.setBounds(54, 480, 38, 27);
		frame.getContentPane().add(button_11);
		
		Button button_12 = new Button("");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(new Color(0, 255, 0));
			}
		});
		button_12.setBackground(new Color(0, 255, 0));
		button_12.setBounds(54, 315, 38, 27);
		frame.getContentPane().add(button_12);
		
		Button button_13 = new Button("");
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(new Color(0, 255, 255));
			}
		});
		button_13.setBackground(new Color(0, 255, 255));
		button_13.setBounds(10, 480, 38, 27);
		frame.getContentPane().add(button_13);
		
		Button button_14 = new Button("");
		button_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(new Color(128, 0, 0));
			}
		});
		button_14.setBackground(new Color(128, 0, 0));
		button_14.setBounds(10, 348, 38, 27);
		frame.getContentPane().add(button_14);
		
		Button button_15 = new Button("");
		button_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(new Color(128, 0, 128));
			}
		});
		button_15.setBackground(new Color(128, 0, 128));
		button_15.setBounds(54, 447, 38, 27);
		frame.getContentPane().add(button_15);
		
		Button button_16 = new Button("");
		button_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(new Color(128, 128, 0));
			}
		});
		button_16.setBackground(new Color(128, 128, 0));
		button_16.setBounds(54, 348, 38, 27);
		frame.getContentPane().add(button_16);
		
		Button button_17 = new Button("");
		button_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(new Color(128, 128, 128));
			}
		});
		button_17.setBackground(new Color(128, 128, 128));
		button_17.setBounds(10, 447, 38, 27);
		frame.getContentPane().add(button_17);
		
		Button button_18 = new Button("");
		button_18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(new Color(192, 192, 192));
			}
		});
		button_18.setBackground(new Color(192, 192, 192));
		button_18.setBounds(10, 381, 38, 27);
		frame.getContentPane().add(button_18);
		
		Button button_19 = new Button("");
		button_19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(new Color(255, 0, 0));
			}
		});
		button_19.setBackground(new Color(255, 0, 0));
		button_19.setBounds(54, 414, 38, 27);
		frame.getContentPane().add(button_19);
		
		Button button_20 = new Button("");
		button_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(new Color(255, 0, 255));
			}
		});
		button_20.setBackground(new Color(255, 0, 255));
		button_20.setBounds(54, 381, 38, 27);
		frame.getContentPane().add(button_20);
		
		Button button_21 = new Button("");
		button_21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setColor(new Color(255, 255, 0));
			}
		});
		button_21.setBackground(new Color(255, 255, 0));
		button_21.setBounds(10, 414, 38, 27);
		frame.getContentPane().add(button_21);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(whiteboardSwing.class.getResource("/image/1.gif")));
		btnNewButton.setBounds(10, 64, 38, 29);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeType("d");
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		JButton button_22 = new JButton("");
		button_22.setIcon(new ImageIcon(whiteboardSwing.class.getResource("/image/5.gif")));
		button_22.setBounds(54, 64, 38, 29);
		button_22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeType("e");
			}
		});
		frame.getContentPane().add(button_22);
		
		JButton button_23 = new JButton("");
		button_23.setIcon(new ImageIcon(whiteboardSwing.class.getResource("/image/2.gif")));
		button_23.setBounds(54, 96, 38, 29);
		button_23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeType("l");
			}
		});
		frame.getContentPane().add(button_23);
		
		JButton button_24 = new JButton("");
		button_24.setIcon(new ImageIcon(whiteboardSwing.class.getResource("/image/3.gif")));
		button_24.setBounds(10, 133, 38, 29);
		button_24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeType("r");
			}
		});
		frame.getContentPane().add(button_24);
		
		JButton button_25 = new JButton("");
		button_25.setIcon(new ImageIcon(whiteboardSwing.class.getResource("/image/4.gif")));
		button_25.setBounds(10, 98, 38, 27);
		button_25.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeType("o");
			}
		});
		frame.getContentPane().add(button_25);
		
		JButton button_26 = new JButton("");
		button_26.setIcon(new ImageIcon(whiteboardSwing.class.getResource("/image/7.gif")));
		button_26.setBounds(54, 135, 38, 27);
		button_26.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeType("c");
			}
		});
		frame.getContentPane().add(button_26);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeType("ol");
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(whiteboardSwing.class.getResource("/image/draw0-1.jpg")));
		btnNewButton_1.setBounds(10, 172, 38, 27);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton button_4 = new JButton("");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeType("cl");
				
			}
		});
		button_4.setIcon(new ImageIcon(whiteboardSwing.class.getResource("/image/draw1.jpg")));
		button_4.setBounds(54, 172, 38, 27);
		frame.getContentPane().add(button_4);
		
		JButton button_5 = new JButton("");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				canvas.setShapeType("rl");
			}
		});
		button_5.setIcon(new ImageIcon(whiteboardSwing.class.getResource("/image/draw3.jpg")));
		button_5.setBounds(10, 209, 38, 27);
		frame.getContentPane().add(button_5);
	
		
		JSlider slider = new JSlider(0,20,1);
		slider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {

			    slider.addChangeListener(new ChangeListener() {
			        public void stateChanged(final ChangeEvent theEvent) {
			         
			        	int stroke = slider.getValue();
			            canvas.setStroke(stroke);
//			            canvas.repaint();
			            
			        }
			    });
				
			}
		});
		slider.setBounds(10, 250, 82, 26);
		frame.getContentPane().add(slider);
		

		JButton btnText = new JButton("");
		btnText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				String text = JOptionPane.showInputDialog("Please input the text you want!");
//				canvas.setText(text);
				canvas.setShapeType("text");
//				canvas.setText("text");
			}
		}
	
			
		);
		btnText.setIcon(new ImageIcon(whiteboardSwing.class.getResource("/image/6.gif")));
		btnText.setBounds(54, 207, 38, 29);
		frame.getContentPane().add(btnText);
	

		
		JPanel chatbox = new JPanel();
		chatbox.setBounds(977, 64, 300, 250);
		frame.getContentPane().add(chatbox);
		chatbox.setLayout(new BorderLayout());
		chatbox.setPreferredSize(new Dimension(300,0));
		JPanel buttonpanel = new JPanel();
		
		buttonpanel.setPreferredSize(new Dimension(0,60));
		textBox = new JTextArea();
		textBox.setEditable(false);
		textBox.setLineWrap(true);
		JScrollPane scrollbar = new JScrollPane(textBox);
		messageArea = new JTextField(25);
		sendButton = new JButton();
		sendButton.setText("Send");
		buttonpanel.add(messageArea);
		buttonpanel.add(sendButton);
		
		chatbox.add(scrollbar);
		
		chatPanel = new JPanel();
		scrollbar.setColumnHeaderView(chatPanel);
		chatPanel.setLayout(new BorderLayout());
		chatPanel.setBackground(Color.white);
		
		chatPanel.setVisible(true);
		chatbox.add(buttonpanel, BorderLayout.SOUTH);
		
		sendButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {

		        //canvas.setShapeType("r");
//                canvas.saveFile("abcd.txt");
		    	List<String> clientNames = new ArrayList<>();
		    	
		    	for (ClientModel client: clients) {
		    		clientNames.add(client.getName());
		    	}
		        String chatMessage = messageArea.getText();
		        Message msg = new Message("Manager", chatMessage, clientNames);
		        messageArea.setText(null);
//                textBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		        textBox.append("You said: " + chatMessage + "\n");
		        try {
		    			broadcastChat(msg);
		    		
		            
		        } catch (Exception ex) {
		            System.out.println(ex);
		        }
		    }

		});
		
		frame.setVisible(true);
			
		
	}

	
	public void setShapeType(char c) {
		currType = c;
	}
	
	
	public List<Shapes> getShapeList() {
        return this.shapeList;
    }
	

	

}
