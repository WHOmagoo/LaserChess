package net.ddns.whomagoo.laserchess.gui.pc;

import javafx.scene.image.ImageView;
import net.ddns.whomagoo.laserchess.game.move.Move;

public class MoveView extends ImageView {
  private Move move;


  public MoveView(Move move){
    this.move = move;

    String imageName;

    if(move.getName().startsWith("Move")) {
      imageName = "Tile Select";
    } else {
      imageName  = move.getName();
    }
    setImage(PiecesCollections.getImage(imageName, "__overlay__"));
  }

  public Move getMove(){
    return move;
  }
}
