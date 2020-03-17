
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class Client extends JFrame {
    private static Socket chatSocket;
    private static Socket drawSocket;

    private static ObjectOutputStream drawOut;
    private static ObjectOutputStream chatOut;
    private String currentFilePath="";
    private static String clientName;
    private JFrame frame;
    private JPanel controls, chatPanel;
    private static CanvasClient canvas = new CanvasClient();
    public static JTextArea textArea, textBox, clientsArea;
    public static JLabel connectedClients;
    private JTextField messageArea;
    public static Message message;
    private JButton sendButton;
    private JButton circle, line, rect, oval, col;

    public Client(String clientName) {
//        Container contents = getContentPane();
//        contents.setLayout(null);
//
//        controls = new JPanel();
//        controls.setBounds(0, 0, 600, 40);
//        controls.setLayout(null);
//
//        circle = new JButton("Circle");
//        oval = new JButton("Oval");
//        line = new JButton("Line");
//        rect = new JButton("Rectangle");
//        col = new JButton("Colour");
//        circle.setBounds(110, 0, 100, 30);
//        oval.setBounds(410, 0, 100, 30);
//        line.setSize(100, 30);
//        rect.setBounds(210, 0, 100, 30);
//        col.setBounds(310, 0, 100, 30);
//        controls.add(line);
//        controls.add(circle);
//        controls.add(oval);
//        controls.add(rect);
//        controls.add(col);
//
//        contents.add(controls);
//
//        circle.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                canvas.setShapeType("c");
//            }
//
//        });
//
//        oval.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                //canvas.setShapeType("o");
//                canvas.openFile("abcd.txt");
//            }
//
//        });
//
//        line.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                canvas.setShapeType("l");
//            }
//
//        });
//
//        rect.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                //canvas.setShapeType("r");
////                canvas.saveFile("abcd.txt");
//                Message msg = new Message(clientName, "Mai bhi bhejounga!");
//
//                try {
//
//                    chatOut.reset();
//                    chatOut.writeObject(msg);
//                    chatOut.flush();
//                } catch (Exception ex) {
//                    System.out.println(ex);
//                }
//            }
//
//        });
//
//        col.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Color color = JColorChooser.showDialog(null, "Color Picker",
//                        Color.black);
//                canvas.setColor(color);
//            }
//
//        });
// 
//        canvas.setBounds(0, 40, 400, 340);
//        canvas.setBackground(Color.white);
//        contents.add(canvas);
//
//        setTitle("Whiteboard");
//        setSize(400, 400);
//        setVisible(true);
    	
    	frame = new JFrame();
		frame.getContentPane().setForeground(Color.BLACK);
		frame.getContentPane().setFont(new Font("Times New Roman", Font.BOLD, 20));
		frame.setBounds(100, 100, 1500, 728);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		
		panel.setBounds(0, 5, 325, 43);
		frame.getContentPane().add(panel);
		
		chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatPanel.setBackground(Color.white);
		
		JPanel chatbox = new JPanel();
        chatbox.setLayout(new BorderLayout());
        chatbox.setPreferredSize(new Dimension(300,0));
        
        connectedClients = new JLabel("Connected Clients");
        connectedClients.setBounds(1064, 320, 200, 30);
        
        
        clientsArea = new JTextArea();
        JScrollPane clientScrollBar = new JScrollPane(clientsArea);
        clientsArea.setBounds(980, 360, 300, 250);
        clientsArea.setEditable(false);
        
        
 //       frame.getContentPane().add(connectedClients);
        frame.add(connectedClients);
        frame.add(clientsArea);
        
        JPanel menuPanel = new JPanel();
        JPanel buttonpanel = new JPanel();
        
        buttonpanel.setPreferredSize(new Dimension(0,60));
        textBox = new JTextArea();
        textBox.setEditable(false);
        textBox.setLineWrap(true);
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        JScrollPane scrollbar = new JScrollPane(textBox);
        messageArea = new JTextField(25);
        sendButton = new JButton();
        sendButton.setText("Send");
        buttonpanel.add(messageArea);
        buttonpanel.add(sendButton);
        
        chatbox.add(scrollbar);
        chatbox.add(buttonpanel, BorderLayout.SOUTH);
        chatPanel.setBounds(977, 76, 300, 239);
        chatPanel.add(chatbox, BorderLayout.EAST);
        
        this.setVisible(true);
        
        frame.getContentPane().add(chatPanel);
        
        sendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //canvas.setShapeType("r");
//                canvas.saveFile("abcd.txt");
                String chatMessage = messageArea.getText();
                Message msg = new Message(clientName, chatMessage, null);
                messageArea.setText(null);
//                textBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                textBox.append("You said: " + chatMessage + "\n");
                try {
            			chatOut.reset();
                        chatOut.writeObject(msg);
                        chatOut.flush();
            		
                    
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }

        });
		
//		Button button_1 = new Button("New");
//		panel.add(button_1);
//		button_1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				
//				   String[] options = {"YES", "NO"};
//			        int x = JOptionPane.showOptionDialog(null, "Do you want to save the current file?",
//			                "Click a button",
//			                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
//			        //System.out.println(x);
//			        if(x==0 && currentFilePath.equals("")) {
//			        	JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//						
//						int rvalue = fc.showSaveDialog(null);
//						
//						if(rvalue == JFileChooser.APPROVE_OPTION) {
//							File selectedFile = fc.getSelectedFile();
//							//System.out.println(selectedFile.getAbsolutePath());
//							currentFilePath=selectedFile.getAbsolutePath();
//							canvas.saveFile(selectedFile.getAbsolutePath());
//						}
//						currentFilePath="";
//			        	canvas.newFile();
//			        }
//			        else if(x==0 && (!currentFilePath.equals(""))) {
//			        	canvas.saveFile(currentFilePath);
//			        	currentFilePath="";
//			        	canvas.newFile();
//			        }
//			        else {
//			        	currentFilePath="";
//			        	canvas.newFile();
//			        }
//				
//			}
//		});
//		button_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
//		button_1.setBackground(Color.WHITE);
//		
//		Button button = new Button("Open");
//		panel.add(button);
//		button.setBackground(Color.WHITE);
//		button.setFont(new Font("Times New Roman", Font.BOLD, 18));
//		
//		Button button_2 = new Button("Save");
//		panel.add(button_2);
//		button_2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if(currentFilePath.equals("")) {
//				JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//				
//				int rvalue = fc.showSaveDialog(null);
//				
//				if(rvalue == JFileChooser.APPROVE_OPTION) {
//					File selectedFile = fc.getSelectedFile();
//					//System.out.println(selectedFile.getAbsolutePath());
//					currentFilePath=selectedFile.getAbsolutePath();
//					canvas.saveFile(selectedFile.getAbsolutePath());
//				}
//				}
//				else {
//					canvas.saveFile(currentFilePath);
//				}
//			}
//		});
//		button_2.setFont(new Font("Times New Roman", Font.BOLD, 18));
//		button_2.setBackground(Color.WHITE);
//		
//		Button button_3 = new Button("SaveAs");
//		panel.add(button_3);
//		button_3.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//				
//				int rvalue = fc.showSaveDialog(null);
//				
//				if(rvalue == JFileChooser.APPROVE_OPTION) {
//					File selectedFile = fc.getSelectedFile();
//					//System.out.println(selectedFile.getAbsolutePath());
//					currentFilePath=selectedFile.getAbsolutePath();
//					canvas.saveFile(selectedFile.getAbsolutePath());
//				}
//				
//			}
//		});
//		button_3.setFont(new Font("Times New Roman", Font.BOLD, 18));
//		button_3.setBackground(Color.WHITE);
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//				
//				int rvalue = fc.showOpenDialog(null);
//				
//				if(rvalue == JFileChooser.APPROVE_OPTION) {
//					File selectedFile = fc.getSelectedFile();
//					//System.out.println(selectedFile.getAbsolutePath());
//					currentFilePath=selectedFile.getAbsolutePath();
//					canvas.openFile(selectedFile.getAbsolutePath());
//				
//				}
//			}
//		});
		
		
		canvas.setBackground(Color.WHITE);
		canvas.setBounds(107, 76, 864, 467);
		
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
		button_7.setBounds(54, 516, 38, 27);
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
		button_9.setBounds(10, 516, 38, 27);
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
		button_11.setBounds(54, 483, 38, 27);
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
		button_13.setBounds(10, 483, 38, 27);
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
		button_15.setBounds(54, 450, 38, 27);
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
		button_17.setBounds(10, 450, 38, 27);
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
		button_19.setBounds(54, 417, 38, 27);
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
		button_21.setBounds(10, 417, 38, 27);
		frame.getContentPane().add(button_21);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(Client.class.getResource("/image/1.gif")));
		btnNewButton.setBounds(10, 74, 38, 29);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeType("d");
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		JButton button_22 = new JButton("");
		button_22.setIcon(new ImageIcon(Client.class.getResource("/image/5.gif")));
		button_22.setBounds(54, 74, 38, 29);
		button_22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeType("e");
			}
		});
		frame.getContentPane().add(button_22);
		
		JButton button_23 = new JButton("");
		button_23.setIcon(new ImageIcon(Client.class.getResource("/image/2.gif")));
		button_23.setBounds(54, 107, 38, 29);
		button_23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeType("l");
			}
		});
		frame.getContentPane().add(button_23);
		
		JButton button_24 = new JButton("");
		button_24.setIcon(new ImageIcon(Client.class.getResource("/image/3.gif")));
		button_24.setBounds(10, 141, 38, 29);
		button_24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeType("r");
			}
		});
		frame.getContentPane().add(button_24);
		
		JButton button_25 = new JButton("");
		button_25.setIcon(new ImageIcon(Client.class.getResource("/image/4.gif")));
		button_25.setBounds(10, 109, 38, 27);
		button_25.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeType("o");
			}
		});
		frame.getContentPane().add(button_25);
		
		JButton button_26 = new JButton("");
		button_26.setIcon(new ImageIcon(Client.class.getResource("/image/7.gif")));
		button_26.setBounds(54, 143, 38, 27);
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
		btnNewButton_1.setIcon(new ImageIcon(Client.class.getResource("/image/draw0-1.jpg")));
		btnNewButton_1.setBounds(10, 175, 38, 27);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton button_4 = new JButton("");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeType("cl");
				
			}
		});
		button_4.setIcon(new ImageIcon(Client.class.getResource("/image/draw1.jpg")));
		button_4.setBounds(54, 175, 38, 27);
		frame.getContentPane().add(button_4);
		
		JButton button_5 = new JButton("");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				canvas.setShapeType("rl");
			}
		});
		button_5.setIcon(new ImageIcon(Client.class.getResource("/image/draw3.jpg")));
		button_5.setBounds(10, 205, 38, 27);
		frame.getContentPane().add(button_5);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				String text = JOptionPane.showInputDialog("Please input the text you want!");
//				canvas.setText("text");
				canvas.setShapeType("text");
				
			}
		});
		button.setIcon(new ImageIcon(Client.class.getResource("/image/6.gif")));
		button.setBounds(54, 205, 38, 29);
		frame.getContentPane().add(button);
		
		JSlider slider = new JSlider(0, 20, 1);
		slider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				
				slider.addChangeListener(new ChangeListener() {
			        public void stateChanged(final ChangeEvent theEvent) {
			         
			        	int stroke = slider.getValue();
			            canvas.setStroke(stroke);
//			            canvas.repaint();
			            
			        }
			    });
			}
		});
		slider.setBounds(15, 248, 77, 26);
		frame.getContentPane().add(slider);
		frame.setVisible(true);
		
    }

    public static void sendDrawingToServer(Drawing drawing) {
        try {
            drawOut.reset();
            drawOut.writeObject(drawing);
            drawOut.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
//    	Client client = new Client("vv");



        try {
        	String ip = null;
        	int portNumber = 0;
        	
        	String ipSocket = JOptionPane.showInputDialog("Enter the IP address, PortNumber, and your name(ip:portNumber:name)");
        	StringTokenizer tokenizer = new StringTokenizer(ipSocket, ":");
        	
        	if (tokenizer.hasMoreTokens()) {
        		ip = tokenizer.nextToken();
        	}
        	
        	if (tokenizer.hasMoreTokens()) {
        		portNumber = Integer.parseInt(tokenizer.nextToken());
        	}
        	
        	if (tokenizer.hasMoreTokens()) {
        		clientName = tokenizer.nextToken();
        	}
        	
//            InetAddress ip = InetAddress.getByName("localhost");
            chatSocket = new Socket(ip, portNumber);
            drawSocket = new Socket(ip, portNumber);


            chatOut = new ObjectOutputStream(new BufferedOutputStream(chatSocket.getOutputStream()));
            chatOut.flush();
            ObjectInputStream chatOis = new ObjectInputStream(new BufferedInputStream(chatSocket.getInputStream()));

//            Scanner name = new Scanner(System.in);
//            System.out.println("\nEnter your name: ");
//            clientName = name.nextLine();
//            name.close();
            Message msg = new Message(clientName, "join", null);
            chatOut.writeObject(msg);
            chatOut.flush();
            
//            String response = (String) chatOis.readUTF();
//            System.out.println(response);
            
        	
            
           
            
            drawOut = new ObjectOutputStream(new BufferedOutputStream(drawSocket.getOutputStream()));
            drawOut.flush();
            ObjectInputStream drawOis = new ObjectInputStream(new BufferedInputStream(drawSocket.getInputStream()));
            
            
            
            Drawing drawing = (Drawing) drawOis.readObject();
            canvas.getManagerBoard(drawing);
            
            message = (Message) chatOis.readObject();
            List<String> clientNames = message.getClients();
            if (message.getMsg().equals("Connection request was refused by the manager")) {

        		JOptionPane.showMessageDialog(null, "Connection was refused by"
        				+ " the manager", "Alert", 
        				JOptionPane.ERROR_MESSAGE);
        		try {
        			Thread.sleep(1000);
        			System.exit(0);
        		} catch (InterruptedException e1) {
        			e1.printStackTrace();
        		}
            }
            
            else if (message.getMsg().equals("User Already Exists")) {
           
        		JOptionPane.showMessageDialog(null, "User already exists! Please"
        				+ " try with a different name", "Alert", 
        				JOptionPane.ERROR_MESSAGE);
        		try {
        			Thread.sleep(1000);
        			System.exit(0);
        		} catch (InterruptedException e1) {
        			e1.printStackTrace();
        		}
            }
            

            canvas.setClientName(clientName);

            ClientDrawListener cdl = new ClientDrawListener(canvas, drawOis);
            cdl.start();

            ClientChatListener ccl = new ClientChatListener(chatOis);
            ccl.start();
            
            EventQueue.invokeLater(new Runnable() {
    			
    			public void run() {
//    				try {
//    					whiteboardSwing window = new whiteboardSwing();
    				Client gui = new Client(clientName);
    				gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    				if (message.getClients().size() > 0) {
    	            	for (String client: message.getClients()) {
    	            		System.out.print(client);
    	            		clientsArea.append(client + "\n");
    	            	}
    	            }	
    			}
    			
    		});
            
            

//            Client gui = new Client(clientName);
//            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            


            //****************************this code block works fine************************
//            ObjectOutputStream ous = new ObjectOutputStream(new BufferedOutputStream(drawSocket.getOutputStream()));
//            ous.flush();
//            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(drawSocket.getInputStream()));
//            System.out.println("Client works");
//            while (true) {
//                List<Shapes> shapeList = (List<Shapes>) ois.readObject();
//                System.out.println(shapeList);
//                canvas.getManagerBoard(shapeList);
//            }


//            Rectangle shape = (Rectangle) ois.readObject();
//            System.out.println(shape);

//            ClientDrawListener cdl = new ClientDrawListener(canvas, ois);
//
//            System.out.println(shapeList);
//            canvas.getManagerBoard(shapeList);
//            cdl.start();


        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println(e + "" + e.getMessage());
            JOptionPane.showMessageDialog(null, e, "Port error!", JOptionPane.PLAIN_MESSAGE);

        }

    }

}
