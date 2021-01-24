package net.ddns.whomagoo.laserchess.game.piece;

import com.google.gson.Gson;
import net.ddns.whomagoo.laserchess.game.Directions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

;

class PieceDeserializerTest {

  @Test
  void testDefaultDeserializer() {
    PieceDeserializer gpd = PieceDeserializer.defaultDeserializer();
    assertNotNull(gpd);

    assertEquals(8, gpd.bindings.size());
    assertTrue(gpd.bindings.containsKey(PieceNames.BeamSplitter));
    assertTrue(gpd.bindings.containsKey(PieceNames.Block));
    assertTrue(gpd.bindings.containsKey(PieceNames.DiagonalMirror));
    assertTrue(gpd.bindings.containsKey(PieceNames.HorizontalMirror));
    assertTrue(gpd.bindings.containsKey(PieceNames.Hypercube));
    assertTrue(gpd.bindings.containsKey(PieceNames.King));
    assertTrue(gpd.bindings.containsKey(PieceNames.Laser));
    assertTrue(gpd.bindings.containsKey(PieceNames.TriangularMirror));
    assertEquals(BeamSplitter.class, gpd.bindings.get(PieceNames.BeamSplitter));
    assertEquals(Block.class, gpd.bindings.get(PieceNames.Block));
    assertEquals(DiagonalMirror.class, gpd.bindings.get(PieceNames.DiagonalMirror));
    assertEquals(HorizontalMirror.class, gpd.bindings.get(PieceNames.HorizontalMirror));
    assertEquals(Hypercube.class, gpd.bindings.get(PieceNames.Hypercube));
    assertEquals(King.class, gpd.bindings.get(PieceNames.King));
    assertEquals(Laser.class, gpd.bindings.get(PieceNames.Laser));
    assertEquals(TriangularMirror.class, gpd.bindings.get(PieceNames.TriangularMirror));
  }

  @Test
  void testSetBinding() {
    PieceDeserializer gpd = new PieceDeserializer(null);
    assertEquals(0, gpd.bindings.size());

    String type = "Test";
    gpd.setBinding(type, GamePiece.class);

    assertEquals(1, gpd.bindings.size());
    assertTrue(gpd.bindings.containsKey(type));
  }

  @Test
  void testDeserializeBeamSplitter() {
    GamePiece b = new BeamSplitter("TeamA");
    testDeserialize(b);
  }

  @Test
  void testDeserializeGamePiece() {
    GamePiece b = new Block("TeamA");
    testDeserialize(b);
  }
  @Test
  void testDeserializeDiagonalMirror() {
    GamePiece b = new DiagonalMirror("TeamA");
    testDeserialize(b);
  }
  
  @Test
  void testDeserializeHorizontalMirror() {
    GamePiece b = new HorizontalMirror("TeamA");
    testDeserialize(b);
  }
  
  @Test
  void testDeserializeHypercube() {
    GamePiece b = new Hypercube("TeamA");
    testDeserialize(b);
  }
  
  @Test
  void testDeserializeKing() {
    GamePiece b = new King("TeamA");
    testDeserialize(b);
  }
  
  @Test
  void testDeserializeLaser() {
    GamePiece b = new Laser("TeamA");
    testDeserialize(b);
  }
  
  @Test
  void testDeserializeTriangularMirror() {
    GamePiece b = new TriangularMirror("TeamA");
    testDeserialize(b);
  }
  
  @Test
  void testDeserializeOtherType() {
    Gson gson = PieceDeserializer.makeGsonWithTypeAdapter();

    String json = "{\"teamName\":\"TeamA\",\"typeName\":\"Random\",\"facing\":\"North\",\"xPos\":12,\"yPos\":7}";

    assertNull(gson.fromJson(json, GamePiece.class));

  }

  @Test
  private Piece testDeserialize(GamePiece p){
    Gson gson = PieceDeserializer.makeGsonWithTypeAdapter();


    p.setXPos(12);
    p.setYPos(7);
    p.setFacing(Directions.NORTH);

    String json = gson.toJson(p);

    System.out.println(json);


    Piece newPiece = gson.fromJson(json, Piece.class);

    assertEquals(p, newPiece);
    return newPiece;
  }
}