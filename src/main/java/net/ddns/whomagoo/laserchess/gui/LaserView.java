package net.ddns.whomagoo.laserchess.gui;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.LaserSegment;

import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class LaserView extends StackPane {

  public static List<ImageView> getViews(LaserSegment segment){
    Image i = PiecesCollections.getImage("Laser Segment", "TeamA");

    ArrayList<ImageView> result = new ArrayList<>();

    if(i == null){
      //TODO
    } else {
      ImageView entry = new ImageView(i);
      entry.setRotate(Directions.getNum(segment.getDirectionSource()) * 90);
      result.add(entry);

      if(!Directions.DESTROYED.equals(segment.getDirectionOut())) {
        ImageView exit = new ImageView(i);
        exit.setRotate(Directions.getNum(segment.getDirectionOut()) * 90);
        result.add(exit);
      } else {
        ImageView exit = new ImageView(PiecesCollections.getImage("Tile Select", "TeamA"));
        result.add(exit);
      }
    }

    return result;
  }
}
