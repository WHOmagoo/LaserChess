package net.ddns.whomagoo.laserchess.game;

import com.google.gson.Gson;
import net.ddns.whomagoo.laserchess.game.piece.Block;
import net.ddns.whomagoo.laserchess.game.piece.GamePiece;
import net.ddns.whomagoo.laserchess.game.piece.Piece;
import net.ddns.whomagoo.laserchess.game.piece.PieceDeserializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

;

public class PieceSerializerTest {

  @Test
  public void testBlockToJson(){
    Gson gson = PieceDeserializer.makeGsonWithTypeAdapter();

    Block b = new Block("TeamA");
    b.setXPos(12);
    b.setYPos(7);
    b.setFacing(Directions.NORTH);

    String json = gson.toJson(b);

    System.out.println(json);


    Piece p = gson.fromJson(json, GamePiece.class);

    assertEquals(b, p);
  }
}
