package net.ddns.whomagoo.laserchess.game.piece;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.move.Move;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.rules.ExpectedException;

import java.util.List;

public class GamePieceDeserializerTest {

  @Test
  public void testDefaultDeserializer() {
    GamePieceDeserializer gpd = GamePieceDeserializer.defaultDeserializer();
    Assertions.assertNotNull(gpd);

    Assertions.assertEquals(8, gpd.bindings.size());
    Assertions.assertTrue(gpd.bindings.containsKey(PieceNames.BeamSplitter));
    Assertions.assertTrue(gpd.bindings.containsKey(PieceNames.Block));
    Assertions.assertTrue(gpd.bindings.containsKey(PieceNames.DiagonalMirror));
    Assertions.assertTrue(gpd.bindings.containsKey(PieceNames.HorizontalMirror));
    Assertions.assertTrue(gpd.bindings.containsKey(PieceNames.Hypercube));
    Assertions.assertTrue(gpd.bindings.containsKey(PieceNames.King));
    Assertions.assertTrue(gpd.bindings.containsKey(PieceNames.Laser));
    Assertions.assertTrue(gpd.bindings.containsKey(PieceNames.TriangularMirror));
    Assertions.assertEquals(BeamSplitter.class, gpd.bindings.get(PieceNames.BeamSplitter));
    Assertions.assertEquals(Block.class, gpd.bindings.get(PieceNames.Block));
    Assertions.assertEquals(DiagonalMirror.class, gpd.bindings.get(PieceNames.DiagonalMirror));
    Assertions.assertEquals(HorizontalMirror.class, gpd.bindings.get(PieceNames.HorizontalMirror));
    Assertions.assertEquals(Hypercube.class, gpd.bindings.get(PieceNames.Hypercube));
    Assertions.assertEquals(King.class, gpd.bindings.get(PieceNames.King));
    Assertions.assertEquals(Laser.class, gpd.bindings.get(PieceNames.Laser));
    Assertions.assertEquals(TriangularMirror.class, gpd.bindings.get(PieceNames.TriangularMirror));
  }

  @Test
  public void testSetBinding() {
    GamePieceDeserializer gpd = new GamePieceDeserializer();
    Assertions.assertEquals(0, gpd.bindings.size());

    gpd.setBinding("Test", GamePiece.class);

    Assertions.assertEquals(1, gpd.bindings.size());
  }

  @Test
  public void testDeserializeBeamSplitter() {
    GamePiece b = new BeamSplitter("TeamA");
    testDeserialize(b);
  }

  @Test
  public void testDeserializeGamePiece() {
    GamePiece b = new Block("TeamA");
    testDeserialize(b);
  }
  @Test
  public void testDeserializeDiagonalMirror() {
    GamePiece b = new DiagonalMirror("TeamA");
    testDeserialize(b);
  }
  
  @Test
  public void testDeserializeHorizontalMirror() {
    GamePiece b = new HorizontalMirror("TeamA");
    testDeserialize(b);
  }
  
  @Test
  public void testDeserializeHypercube() {
    GamePiece b = new Hypercube("TeamA");
    testDeserialize(b);
  }
  
  @Test
  public void testDeserializeKing() {
    GamePiece b = new King("TeamA");
    testDeserialize(b);
  }
  
  @Test
  public void testDeserializeLaser() {
    GamePiece b = new Laser("TeamA");
    testDeserialize(b);
  }
  
  @Test
  public void testDeserializeTriangularMirror() {
    GamePiece b = new TriangularMirror("TeamA");
    testDeserialize(b);
  }
  
  @Test
  public void testDeserializeOtherType() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(GamePiece.class, GamePieceDeserializer.defaultDeserializer());
    Gson gson = gsonBuilder.create();

    String json = "{\"teamName\":\"TeamA\",\"typeName\":\"Random\",\"facing\":\"North\",\"xPos\":12,\"yPos\":7}";

    Assertions.assertNull(gson.fromJson(json, GamePiece.class));

  }
  
  private void testDeserialize(GamePiece p){
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(GamePiece.class, GamePieceDeserializer.defaultDeserializer());
    Gson gson = gsonBuilder.create();
    
    p.setXPos(12);
    p.setYPos(7);
    p.setFacing(Directions.NORTH);

    String json = gson.toJson(p);

    System.out.println(json);


    Piece newPiece = gson.fromJson(json, GamePiece.class);

    Assertions.assertEquals(p, newPiece);
  }
}