# JavaFX_3Dcube
Three-dimensional cube rotation and scaling using JavaFX library. Rotation implies showing only __visible__ side of cube.

## System requirements
Java version 23.0.1

JavaFX version 23.0.1

## Compilation

1. Download latest [JavaFx](https://gluonhq.com/products/javafx/) library and extract contents.

2. To compile, open command line in __source code directory__ and write  
   `javac --module-path {Path to your javafx lib folder} --add-modules=javafx.controls Main.java`

    - For example if JavaFx contents extracted to `С:\Java`  
      Then command should look like this:  
      `javac --module-path "C:\Java\javafx-sdk-23.0.1\lib" --add-modules=javafx.controls Main.java`

4. After compilation run `Main` file:  
   `java --module-path {Path to your javafx lib folder} --add-modules=javafx.controls Main`

## Usage

After successful compilation just enter X, Y, Z degree and, if you want, change scale of cube. To confirm and see the result of entered values just press "Enter!" button.

![Figure 1. Working program](https://github.com/titemov/JavaFX_3Dcube/blob/main/javafx_3dcube.png)

After pressing the button you will see __projection__ of three-dimensional cube.



## References
1. https://habr.com/ru/articles/494094/
2. https://habr.com/ru/articles/497808/
