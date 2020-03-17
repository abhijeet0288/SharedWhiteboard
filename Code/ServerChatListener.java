import java.io.ObjectInputStream;

public class ServerChatListener extends Thread {

    private ObjectInputStream ois;


    public ServerChatListener(ObjectInputStream ois) {
        this.ois = ois;
    } 

    @Override
    public void run() {
//        super.run();
        while (true) {
            try {

                Message msg = (Message) ois.readObject();
              //  System.out.println(msg.getSender()+" says : "+msg.getMsg());
                
                whiteboardSwing.broadcastChat(msg);
                whiteboardSwing.textBox.append(msg.getSender() + " says : " + msg.getMsg() + "\n");

            } catch (Exception e) {
                //System.out.println(e);

            }
        }

    }
}
