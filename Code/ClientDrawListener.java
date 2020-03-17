import java.io.ObjectInputStream;

public class ClientDrawListener extends Thread {

    private ObjectInputStream ois;
    private CanvasClient canvas;


    public ClientDrawListener(CanvasClient canvas, ObjectInputStream ois) {
        this.ois = ois;
        this.canvas = canvas;
    }

    @Override
    public void run() {
//        super.run();
    	while (true) {
            try {

                Drawing drawing = (Drawing) ois.readObject();
                if (drawing.getdStatus() == 1) {
                    this.canvas.getManagerBoard(drawing);
                }
                else{
                    this.canvas.currDrawing(drawing);
                }

            } catch (Exception e) {
                System.out.println(e);
                break;
            }
        }
    }
}
