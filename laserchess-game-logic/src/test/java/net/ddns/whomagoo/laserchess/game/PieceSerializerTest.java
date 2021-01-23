package net.ddns.whomagoo.laserchess.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.ddns.whomagoo.laserchess.game.piece.Block;
import net.ddns.whomagoo.laserchess.game.piece.GamePiece;
import net.ddns.whomagoo.laserchess.game.piece.GamePieceDeserializer;
import net.ddns.whomagoo.laserchess.game.piece.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

;

public class PieceSerializerTest {

  @Test
  public void testBlockToJson(){
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(GamePiece.class, GamePieceDeserializer.defaultDeserializer());
    Gson gson = gsonBuilder.create();

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
