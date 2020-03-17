import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ServerDrawListener extends Thread {

    private ObjectInputStream ois;
    private Canvas1 canvas;
    private ClientModel cm;


    public ServerDrawListener(Canvas1 canvas, ObjectInputStream ois, ClientModel cm) {
        this.ois = ois;
        this.canvas = canvas;
        this.cm = cm;
    }

    @Override
    public void run() {
//        super.run();
    	 while (true) {
             try {

                 Drawing drawing = (Drawing) ois.readObject();
                 if (drawing.getdStatus() == 1) {
                     this.canvas.getClientDrawing(drawing);
                 }
                 else{
                     this.canvas.getTempDrawing(drawing);
                 }
//                 this.canvas.getClientDrawing(drawing);

             } catch (Exception e) {
                // System.out.println(cm.getName()+ " disconnected");
                 break;
             }
         }
         whiteboardSwing.kickClient(this.cm);

    }
}
