import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientModel {

    private String name;
    private Socket dSocket,cSocket;
    private ObjectInputStream drawOis, chatOis;
    private ObjectOutputStream drawOut, chatOut;

    public ClientModel(String name, Socket dSocket, Socket cSocket, ObjectInputStream drawOis, ObjectInputStream chatOis, ObjectOutputStream drawOut, ObjectOutputStream chatOut) {

        this.name = name;
        this.dSocket = dSocket;
        this.cSocket = cSocket;
        this.drawOis = drawOis;
        this.chatOis = chatOis;
        this.drawOut = drawOut;
        this.chatOut = chatOut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getdSocket() {
        return dSocket;
    }

    public void setdSocket(Socket dSocket) {
        this.dSocket = dSocket;
    }

    public Socket getcSocket() {
        return cSocket;
    }

    public void setcSocket(Socket cSocket) {
        this.cSocket = cSocket;
    }

    public ObjectInputStream getDrawOis() {
        return drawOis;
    }

    public void setDrawOis(ObjectInputStream drawOis) {
        this.drawOis = drawOis;
    }

    public ObjectInputStream getChatOis() {
        return chatOis;
    }

    public void setChatOis(ObjectInputStream chatOis) {
        this.chatOis = chatOis;
    }

    public ObjectOutputStream getDrawOut() {
        return drawOut;
    }

    public void setDrawOut(ObjectOutputStream drawOut) {
        this.drawOut = drawOut;
    }

    public ObjectOutputStream getChatOut() {
        return chatOut;
    }

    public void setChatOut(ObjectOutputStream chatOut) {
        this.chatOut = chatOut;
    }




}
