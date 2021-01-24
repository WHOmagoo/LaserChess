package net.ddns.whomagoo.laserchess.game;

import com.google.gson.Gson;
import net.ddns.whomagoo.laserchess.game.piece.PieceDeserializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardSerializationTest {

  @Test
  void testBoardSerialization(){
    Board b = GameFactory.makeDefaultBoard();

    Gson g = PieceDeserializer.makeGsonWithTypeAdapter();

    String s = g.toJson(b);

    System.out.println(s);

    assertTrue(true);

    Board loaded = g.fromJson(s, Board.class);

    assertTrue(true);
  }

}
