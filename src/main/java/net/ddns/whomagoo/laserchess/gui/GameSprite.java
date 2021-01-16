package net.ddns.whomagoo.laserchess.gui;

import com.google.gson.Gson;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSprite extends Group {
  private GameView contained;
  private int viewXLoc;
  private int viewYLoc;

  //For a given team, what are the associated colors
  //Inner HashMap, for a given layer, what is that layer's color
  static Map<String, Map<String, Map<String, Color>>> colors = getColors();

  HashMap<String, List<Shape>> items;

  public static Map<String, Map<String, Map<String, Color>>> getColors(){
    Map<String, Map<String, Map<String, Color>>> result = new HashMap<>();


    Gson g = new Gson();

    try {
      FileReader fr = new FileReader("assets/TeamColors.json");

      Map<String, Map<String, Map<String, String>>> json = (Map<String, Map<String, Map<String, String>>>) g.fromJson(fr, Map.class);


    json.forEach((team, layer) -> {
      layer.forEach((layerName, part) -> {
        part.forEach((partName, color) -> {
          if(!result.containsKey(team)){
            result.put(team, new HashMap<>());
          }

          Map<String, Map<String, Color>> layerMap = result.get(team);

          if(!layerMap.containsKey(layerName)){
            layerMap.put(layerName, new HashMap<>());
          }

          Map<String, Color> partMap = layerMap.get(layerName);



          Color c = Color.TRANSPARENT;
          try {
            c = Color.valueOf(color);
          } catch (IllegalArgumentException ignored){
            if(!color.equals("")){
              System.out.println("Could not parse color of " + team + " " + layerName + " " + partName);
            }

          }
          partMap.put(partName, c);

        });
      });
    });

    //BufferedInputStream bis = new BufferedInputStream(fr);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }


    return result;
  }

  public GameSprite(){
    items = new HashMap<>();
  }

  public GameSprite(GameSprite other){
    this.items = other.items;
    setPickOnBounds(true);
    update();
  }

  public GameSprite(HashMap<String, List<Shape>> items, String teamName){
    this.items = items;

    Map<String, Map<String, Color>> teamColors = colors.get(teamName);
    //TODO load default color scheme when teamColors is null

    List<Shape> shapes = items.get("opaque");
    if(shapes != null){
      for (Shape p:
           shapes) {
        p.setFill(teamColors.get("opaque").get("fill"));
      }
    }

    shapes = items.get("opaqueBorder");
    if(shapes != null){
      for (Shape p:
          shapes) {
        p.setFill(teamColors.get("opaqueBorder").get("fill"));
        p.setStroke(teamColors.get("opaqueBorder").get("stroke"));
        p.setStrokeWidth(5);
      }
    }

    shapes = items.get("reflectiveBorder");
    if(shapes != null){
      for (Shape s: shapes){
        s.setFill(teamColors.get("reflectiveBorder").get("fill"));
        s.setStroke(teamColors.get("reflectiveBorder").get("stroke"));
        s.setStrokeWidth(15);
      }
    }

    shapes = items.get("translucent");
    if(shapes != null){
      for (Shape p:
          shapes) {
        p.setFill(teamColors.get("translucent").get("fill"));
        //p.setStroke();
        //p.setStrokeWidth(5);
      }
    }

    shapes = items.get("translucentBorder");
    if(shapes != null){
      for (Shape p:
          shapes) {
        p.setFill(teamColors.get("translucentBorder").get("fill"));
        p.setStroke(teamColors.get("translucentBorder").get("stroke"));
        p.setStrokeWidth(4);
        p.getStrokeDashArray().addAll(.025,9.0);

        //p.setStroke();
        //p.setStrokeWidth(5);
      }
    }

    shapes = items.get("reflective");
    if(shapes != null){
      for (Shape s: shapes) {
        s.setFill(teamColors.get("reflective").get("fill"));
      }
    }

    shapes = items.get("laser");
    if(shapes != null){
      for (Shape s: shapes) {
        s.setStroke(teamColors.get("laser").get("stroke"));
        s.setStrokeWidth(11.7529);
      }
    }

    update();
  }

  public void update(){
    getChildren().clear();
    for (Map.Entry<String, List<Shape>> paths : items.entrySet()) {
      for (Shape item : paths.getValue()) {
        //System.out.println(item.getTransforms());
        getChildren().add(item);
      }
    }
  }

  public Node getAllLayers(){

    Pane p = new Pane();

    for (Map.Entry<String, List<Shape>> items : items.entrySet()) {
      for (Shape item : items.getValue()) {
        p.getChildren().add(item);
      }
    }

    return p;
  }
}
