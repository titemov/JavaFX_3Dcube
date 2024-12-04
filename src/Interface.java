import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Line;

public class Interface extends Application {
    public static int startX=500;
    public static int startY=300;
    public static int len=250;

    public static Line[] lines = new Line[12];

    public static Vector[] vertices ={
            new Vector(-1, 1, 1,1), // 0th vertex (0th XDD)
            new Vector(-1, 1, -1,1), // 1st vertex
            new Vector(1, 1, -1,1), // 2nd vertex
            new Vector(1, 1, 1,1), // 3rd vertex
            new Vector(-1, -1, 1,1), // 4th vertex
            new Vector(-1, -1, -1,1), // 5th vertex
            new Vector(1, -1, -1,1), // 6th vertex
            new Vector(1, -1, 1,1), // 7th vertex
    };

    public static int[][] edges={
            {0, 1},//top
            {1, 2},
            {2, 3},
            {3, 0},

            {0, 4},//between top and bottom
            {1, 5},
            {2, 6},
            {3, 7},

            {4, 5},//bottom
            {5, 6},
            {6, 7},
            {7, 4},
    };

    @Override
    public void start(Stage stage){
        Group group = new Group();
        Group interfaceGroup = new Group();

        Label[] axisLabel = new Label[3];

        Line X = new Line(startX,startY,startX+len,startY); //len=500+10
        axisLabel[0]=new Label("X");
        axisLabel[0].setLayoutX(startX+len);
        axisLabel[0].setLayoutY(startY);
        interfaceGroup.getChildren().add(axisLabel[0]);

        Line Y = new Line(startX,startY,startX,startY-len);//len=500+10
        axisLabel[1]=new Label("Y");
        axisLabel[1].setLayoutX(startX+5);
        axisLabel[1].setLayoutY(startY-len-5);
        interfaceGroup.getChildren().add(axisLabel[1]);

        Line Z = new Line(startX,startY,startX- (double) len /2,startY+ (double) len /2);
        axisLabel[2]=new Label("Z");
        axisLabel[2].setLayoutX(startX- (double) len /2);
        axisLabel[2].setLayoutY(startY+ (double) len /2);
        interfaceGroup.getChildren().add(axisLabel[2]);

        interfaceGroup.getChildren().addAll(X,Y,Z);

        Label coordinatesLabel = new Label("Enter angle:");
        coordinatesLabel.setLayoutX(10);
        coordinatesLabel.setLayoutY(10);
        interfaceGroup.getChildren().addAll(coordinatesLabel);

        Label[] tf_names = new Label[3];
        TextField[][] degree_tf = new TextField[1][3];

        for(int i = 0; i< degree_tf.length; i++){
            for(int n = 0; n< degree_tf[0].length; n++){
                tf_names[0] = new Label("X ");
                tf_names[1] = new Label("Y ");
                tf_names[2] = new Label("Z ");
                tf_names[n].setLayoutX(10);
                tf_names[n].setLayoutY(50+30*n);

                degree_tf[i][n] = new TextField("0");
                degree_tf[i][n].setLayoutX(25+50*i);
                degree_tf[i][n].setLayoutY(50+30*n);
                degree_tf[i][n].setPrefColumnCount(3);

                interfaceGroup.getChildren().addAll(tf_names[n], degree_tf[i][n]);
            }
        }

        Label scaleLabel = new Label("Scale:");
        scaleLabel.setLayoutX(25);
        scaleLabel.setLayoutY(140);

        TextField scale_tf = new TextField("100");
        scale_tf.setLayoutX(25);
        scale_tf.setLayoutY(160);
        scale_tf.setPrefColumnCount(3);

        interfaceGroup.getChildren().addAll(scaleLabel,scale_tf);

        //init cube
        for(int i=0;i<lines.length;i++){
            lines[i] = new Line(startX,startY,startX,startY);
            lines[i].setStroke(Color.DARKGREEN);
            interfaceGroup.getChildren().add(lines[i]);
        }

        Label onError = new Label("");
        onError.setLayoutX(10);
        onError.setLayoutY(220);
        interfaceGroup.getChildren().addAll(onError);

        Button enterBtn = new Button("Enter!");
        enterBtn.setLayoutX(25);
        enterBtn.setLayoutY(190);
        enterBtn.setPrefWidth(100);
        enterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    onError.setText(" ");
                    for(int i=0;i<lines.length;i++){
                        Backend.drawLine(lines[i],startX,startY,startX,startY);
                    }
                    double[][] matrix;
                    double x_angle = Double.parseDouble(degree_tf[0][0].getText())-180;
                    double y_angle = Double.parseDouble(degree_tf[0][1].getText())-180;
                    double z_angle = Double.parseDouble(degree_tf[0][2].getText())-180;
                    double scale = Double.parseDouble(scale_tf.getText());
                    if(scale<1 || scale>150){
                        scale= 150;
                    }
                    System.out.println("X: "+x_angle+"\nY: "+ y_angle +"\nZ: "+ z_angle);
                    matrix = Matrix.getRotationX(x_angle);
                    matrix = Matrix.multiply(Matrix.getRotationY(y_angle),matrix);
                    matrix = Matrix.multiply(Matrix.getRotationZ(z_angle),matrix);
                    matrix = Matrix.multiply(Matrix.getScale(scale,scale,scale),matrix);
                    matrix = Matrix.multiply(Matrix.getTranslation(startX, startY, 0),matrix);
                    Vector[] sceneVertices = new Vector[vertices.length];
                    int minIndex =-1;
                    double min =1000;
                    for(int i=0;i<vertices.length;i++){
                        Vector vertex = Matrix.vecMultiply(matrix,vertices[i]);
                        sceneVertices[i]=vertex;
                        //System.out.println("len="+vertex.getLen()+" z="+vertex.z+" index="+i);
                        if(vertex.z<min){
                            min =vertex.z;
                            minIndex =i;
                        }
                    }

                    //System.out.println(minIndex +" "+ min);
                    for (int i = 0; i < edges.length; i++) {
                        int[] e = edges[i];
                        if (!(e[0] == minIndex || e[1] == minIndex)) {
                            Backend.drawLine(lines[i], sceneVertices[e[0]].x,
                                    sceneVertices[e[0]].y, sceneVertices[e[1]].x, sceneVertices[e[1]].y);
                        }
                    }
                }catch (Exception e){
                    onError.setText("Error: "+e.getMessage());
                    System.out.println(e.getMessage());
                }
            }
        });
        interfaceGroup.getChildren().addAll(enterBtn);



        group.getChildren().addAll(interfaceGroup);
        Scene scene = new Scene(group, Color.SNOW);
        stage.setScene(scene);
        stage.setTitle("JavaFX 3D cube");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);
        stage.show();
    }

    public static void show(){
        Application.launch();
    }
}
