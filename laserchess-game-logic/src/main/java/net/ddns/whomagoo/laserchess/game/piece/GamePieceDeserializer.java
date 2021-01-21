package net.ddns.whomagoo.laserchess.game.piece;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GamePieceDeserializer implements JsonDeserializer<GamePiece> {

  public Map<String, Class<? extends GamePiece>> bindings;

  public static GamePieceDeserializer defaultDeserializer(){
    Map<String, Class<? extends GamePiece>> bindings = new HashMap<>();

    bindings.put(PieceNames.BeamSplitter, BeamSplitter.class);
    bindings.put(PieceNames.Block, Block.class);
    bindings.put(PieceNames.DiagonalMirror, DiagonalMirror.class);
    bindings.put(PieceNames.HorizontalMirror, HorizontalMirror.class);
    bindings.put(PieceNames.Hypercube, Hypercube.class);
    bindings.put(PieceNames.King, King.class);
    bindings.put(PieceNames.Laser, Laser.class);
    bindings.put(PieceNames.TriangularMirror, TriangularMirror.class);

    return new GamePieceDeserializer(bindings);
  }

  public GamePieceDeserializer() {
    bindings = new HashMap<>();
  }

  public GamePieceDeserializer(Map<String, Class<? extends GamePiece>> mapping) {
      this();
      if (mapping != null) bindings = mapping;

  }

  public void setBinding(String name, Class<? extends GamePiece> classObj){
    bindings.put(name, classObj);
  }

  @Override
  public GamePiece deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    if(jsonElement == null) return null;

    String t = jsonElement.getAsJsonObject().get("typeName").getAsString();

    if(bindings.containsKey(t)) return jsonDeserializationContext.deserialize(jsonElement, bindings.get(t));

    return null;
  }
}
