package net.ddns.whomagoo.laserchess.gui;

import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.piece.Piece;

public class PieceView extends ImageView {
  Piece gamePiece;

  public PieceView(Piece gp){
    this.gamePiece = gp;

    Image i = PiecesCollections.getImage(gp.typeName(), gp.teamName());

    if(i == null){
      TextArea p = new TextArea();
      p.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
      p.setText(gp.toString());

      p.setPrefSize(500, 500);
//      setImage();
    } else {
      setRotate(90 * Directions.getNum(gp.facing()));
      setImage(i);
    }
  }

  public void updateRotation(){
    setRotate(90 * Directions.getNum(gamePiece.facing()));
  }

  public int getGameX(){
    return gamePiece.xPos();
  }

  public int getGameY(){
    return gamePiece.yPos();
  }
}
