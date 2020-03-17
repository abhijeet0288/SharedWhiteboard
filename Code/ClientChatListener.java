import java.io.ObjectInputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClientChatListener extends Thread {

    private ObjectInputStream ois;


    public ClientChatListener(ObjectInputStream ois) {
        this.ois = ois;
    }

    @Override
    public void run() {
//        super.run();
        while (true) {
            try {

                Message msg = (Message) ois.readObject();
              //  System.out.println(msg.getSender()+" says : "+msg.getMsg());
                if (Client.textBox != null) {
                	Client.textBox.append(msg.getSender()+" says : "+msg.getMsg() + "\n");
                }
                
                if (msg.getSender().equals("Manager")) {
                	if (Client.clientsArea != null) {
                		Client.clientsArea.setText(null);
                		for (String client: msg.getClients()) {
                			Client.clientsArea.append(client + "\n");
                		}
                	}
                }
                
//                whiteboardSwing.textBox.append(msg.getSender() + " says : " + msg.getMsg() + "\n");

            } catch (Exception e) {
                System.out.println(e);
        		JOptionPane.showMessageDialog(null, "Server closed!"
        				+ " or kicked out", "Alert", 
        				JOptionPane.ERROR_MESSAGE);
        		try {
        			Thread.sleep(1000);
        			System.exit(0);
        		} catch (InterruptedException e1) {
        			e1.printStackTrace();
        		}
                break;

            }
        }

    }
}
