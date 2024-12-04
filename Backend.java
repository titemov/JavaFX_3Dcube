import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Random;

public class Backend extends Interface {
    public static int rng(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }

    public static void drawLine(Line line,double x1,double y1,double x2,double y2){
        line.setStartX(x1);
        line.setStartY(y1);
        line.setEndX(x2);
        line.setEndY(y2);
    }
}