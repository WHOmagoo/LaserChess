package net.ddns.whomagoo.laserchess.gui.pc;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.util.Pair;
import net.ddns.whomagoo.laserchess.resources.ResourceGetter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PiecesCollections {
  private static HashMap<String, Document> svgDocs = new HashMap<>();
  private static HashMap<Pair<String, String>, Image> pieces = new HashMap<>();
  private static SnapshotParameters snapshotParams = null;

  private static SnapshotParameters getSnapshotParams(){
    if(snapshotParams == null){
      snapshotParams = new SnapshotParameters();
      snapshotParams.setFill(Color.TRANSPARENT);
    }

    return snapshotParams;
  }

  public static void loadAllAssets(){
    String[] pieceNames = {
        "Beam Splitter",
        "Block",
        "Diagonal Mirror",
        "Horizontal Mirror",
        "Hypercube",
        "King",
        "Laser",
        "Rotate Clockwise",
        "Rotate Counterclockwise",
        "Tile Select",
        "Triangular Mirror",
        "Fire Laser",
        "Laser Segment"
    };

    HashMap<String, GameSprite> result = new HashMap<>();

    for (String name :
        pieceNames) {

      try {

        ClassLoader cl = PiecesCollections.class.getClassLoader();
        InputStream is = ResourceGetter.getResourceAsStream(name + ".svg");
        Document doc = getDocument(is);
        //Image i = loadImage(doc, "TeamB").snapshot(getSnapshotParams(), null);
        svgDocs.put(name, doc);
      } catch (Exception e) {
        System.out.println("In PiecesCollections.java:41 could not load'" + name + "'");
        System.out.println(e.getMessage());
        System.out.println();
        result.put(name, null);
      }
    };
  }

  public static Image getImage(String pieceName, String teamName){
    Pair<String, String> key = new Pair<>(teamName, pieceName);
    if(!pieces.containsKey(key)) {
      GameSprite gamePiece = getGamePiece(pieceName, teamName);

      if(gamePiece == null){
       pieces.put(key, null);
      } else {
        WritableImage img = gamePiece.snapshot(getSnapshotParams(), null);
        pieces.put(key, img);
      }
    }

    return pieces.get(key);
  }

  public static GameSprite getGamePiece(String name, String teamName){
    return loadImage(svgDocs.get(name), teamName);
  }

  public static GameSprite loadImage(Document svgDoc, String teamName){

    if(svgDoc == null){
      return null;
    }

    Pattern idPattern = Pattern.compile("(.+?)(\\d+)");

    HashMap<String, List<Shape>> resultingItems = new HashMap<>();

    Element svg = (Element) svgDoc.getElementsByTagName("svg").item(0);

    Color colors[] = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.INDIGO};

    Node graphic = svg.getElementsByTagName("g").item(0);
     NodeList shapes = graphic.getChildNodes();
    for (int i = 0; i < shapes.getLength(); i++){

      Node n = shapes.item(i);
      if(!(n instanceof Element)){
        continue;
      }
      Element cur = (Element) n;

      String style = cur.getAttribute("style");
      String id = cur.getAttribute("id");


      Shape shape = null;

      switch(cur.getTagName()){
        case "path":
          String content = cur.getAttribute("d");
          SVGPath path = new SVGPath();
          path.setContent(content);
          shape = path;
          break;
        case "rect":
          double width = Double.parseDouble(cur.getAttribute("width"));
          double height = Double.parseDouble(cur.getAttribute("height"));
          double x = Double.parseDouble(cur.getAttribute("x"));
          double y = Double.parseDouble(cur.getAttribute("y"));
          Rectangle rect = new Rectangle(x, y, width, height);
          shape = rect;
          break;
        case "circle":
          double radius = Double.parseDouble(cur.getAttribute("r"));
          x = Double.parseDouble(cur.getAttribute("cx"));
          y = Double.parseDouble(cur.getAttribute("cy"));
          Circle circle = new Circle(x, y, radius);
          shape = circle;
          break;

      }

      //path.setStyle(style);
      if(shape == null){
        continue;
      }
      shape.setId(id);

      System.out.println(id + ":" + colors[i % colors.length]);

      Matcher idMatcher = idPattern.matcher(id);

      if(idMatcher.matches()) {
        String typeName = idMatcher.group(1);
        String idNum = idMatcher.group(2);

        if(!resultingItems.containsKey(typeName)){
          resultingItems.put(typeName, new ArrayList<>());
        }

        resultingItems.get(typeName).add(shape);
      }
      else {
        //TODO figure how to handle this
        if(!resultingItems.containsKey("!error")){
          resultingItems.put("!error", new ArrayList<>());
        }

        resultingItems.get("!error").add(shape);
      }


        /*
        if (matchReflective.matches()) {
          int num = Integer.parseInt(matchReflective.group(2));

          path.setFill(Color.LIGHTCYAN);
          resultingItems.put(path);
        } else if (matchOpaque.matches()) {
          int num = Integer.parseInt(matchOpaque.group(1));
          path.setFill(Color.RED);
          result.add(path);
        } else if (matchReflectiveBorder.matches()) {
          path.setFill(Color.TRANSPARENT);
          path.setStroke(Color.LIGHTSTEELBLUE);
          path.setStrokeWidth(4.0);
          result.add(path);
        } else if (matchOpaqueBorder.matches()) {
          path.setFill(Color.TRANSPARENT);
          path.setStroke(Color.MAROON);
          path.setStrokeWidth(4.0);
          result.add(path);
        }
        */
      //path.setFill(Color.rgb(r,g,b));*/

    }

    return new GameSprite(resultingItems, teamName);
  }

  public static Document getDocument(InputStream resourceIn) throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
    Document doc=dBuilder.parse(resourceIn);
    return doc;
  }
}
