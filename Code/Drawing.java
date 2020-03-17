import java.io.Serializable;
import java.util.List;

public class Drawing implements Serializable {
    private List<Shapes> shapesList;
    private String sender;
    private int dStatus;

    public int getdStatus() {
		return dStatus;
	}

	public void setdStatus(int dStatus) {
		this.dStatus = dStatus;
	}

	public Drawing(List<Shapes> shapesList, String sender, int dStatus) {
        this.shapesList = shapesList;
        this.sender = sender;
        this.dStatus = dStatus;
    }

    public List<Shapes> getShapesList() {
        return shapesList;
    }

    public void setShapesList(List<Shapes> shapesList) {
        this.shapesList = shapesList;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
