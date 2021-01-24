package net.ddns.whomagoo.laserchess.gui.pc;

import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {

  public static class AsNonApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
      // does nothing
    }

  }
  /**
   * Rigorous Test :-)
   */
  @Test
  void shouldAnswerWithTrue()
  {
    assertTrue( true );
  }

  /*
  @Test
  void testMouseEvents(){
    //TODO
    GameView gv = new GameView(GameFactory.makeDefaultBoard());

    ObservableList<Node> children = gv.getChildren();

    //TODO check if each child when "clicked" will match to correct space
    for(Node c : children){
      System.out.println(c);
    }
//    gv.onClick.handle(new MouseEvent());
  }

   */

}
