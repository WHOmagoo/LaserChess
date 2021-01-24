package net.ddns.whomagoo.laserchess.gui.pc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import net.ddns.whomagoo.laserchess.game.GameFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class LaserChessApp extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException, SAXException, ParserConfigurationException  {
    System.out.println("Hello world");

    String svgcont = "M 174.52699,173.6895 55.093199,54.255714 H 43.564929 V 65.783988 L 162.99872,185.21777 h 11.52827 z";

    SVGPath p = new SVGPath();
    p.setContent(svgcont);

    //p.setFill(Color.ORANGE);
    p.setStyle("fill:none;stroke:#000000;stroke-width:0.264583px;stroke-linecap:butt;stroke-linejoin:miter;stroke-opacity:1");

    PiecesCollections.loadAllAssets();

    //Group root = new Group();

    AnchorPane ap = new AnchorPane();
    GameView gv = new GameView(GameFactory.makeDefaultBoard());
    Button b = new Button("Refresh");
    b.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        gv.update();
        System.out.println(gv.getMinWidth());
      }
    });
    //root.getChildren().add(gv);
    //root.getChildren().add(b);

//    Scene scene = new Scene(root, 1920, 1080, Color.DIMGRAY.darker());

    /**
     * H and V Box will center the game board on the screen.
     * HBox is sceen root
     */
    VBox vbox = new VBox();
    vbox.setAlignment(Pos.CENTER);
    //vbox.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

    HBox hbox = new HBox();
    hbox.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
    hbox.getChildren().add(vbox);
    hbox.setAlignment(Pos.CENTER);
    hbox.setBackground(new Background(new BackgroundFill(Color.rgb(22, 21, 18), null, null)));




//    boardContainer.setMinSize(300,300);
//    boardContainer.setMaxSize(1000,1000);
//    boardContainer.setPrefSize(500,500);



    gv.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
    gv.setMinSize(200,200);

    Scene scene = new Scene(hbox, 1920, 1080, Color.GREEN);

    gv.prefWidthProperty().bind(Bindings.min(hbox.widthProperty(), hbox.heightProperty()));
    gv.prefHeightProperty().bind(Bindings.min(hbox.widthProperty(), hbox.heightProperty()));
    vbox.getChildren().add(gv);



//
//    boardContainer.prefWidthProperty().bind(scene.widthProperty());
//    boardContainer.prefWidthProperty().bind(scene.widthProperty());

    primaryStage.setScene(scene);

    primaryStage.show();
  }
}
