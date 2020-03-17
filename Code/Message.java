import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {

    private String sender, msg;
    private List<String> clients;
 
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public List<String> getClients() {
        return clients;
    }

    public void setClients(List<String> clients) {
        this.clients = clients;
    }

    public Message(String sender, String msg, List<String> clients) {

        this.sender = sender;
        this.msg = msg;
        this.clients = clients;
    }
}
