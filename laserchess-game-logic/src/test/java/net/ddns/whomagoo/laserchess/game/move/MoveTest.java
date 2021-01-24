package net.ddns.whomagoo.laserchess.game.move;

import com.google.gson.Gson;
import net.ddns.whomagoo.laserchess.game.Location;
import net.ddns.whomagoo.laserchess.game.piece.Block;
import net.ddns.whomagoo.laserchess.game.piece.PieceDeserializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveTest {

  @Test
  void getSource() {
  }

  @Test
  void getName() {
  }

  @Test
  void getTargetX() {
  }

  @Test
  void getTargetY() {
  }

  @Test
  void testToString() {
  }

  @Test
  void testEquals() {
    Move m1 = new Move(Move.MOVE_EAST, new Location(1,3), new Block("Team A"));
    Move m2 = new Move(Move.MOVE_EAST, new Location(1,3), new Block("Team A"));
    assertEquals(m1, m2);
  }

  @Test
  void testHashCode() {
  }

  @Test
  void testSerialization(){
    Move m = new Move(Move.MOVE_EAST, new Location(1,3), new Block("Team A"));

    Gson gson = PieceDeserializer.makeGsonWithTypeAdapter();

    String s = gson.toJson(m);

    Move deserializedMove = gson.fromJson(s, Move.class);
    assertEquals(m, deserializedMove);
  }
}