package net.ddns.whomagoo.laserchess.game.piece;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PieceDeserializer implements JsonDeserializer<Piece> {

  public Map<String, Class<? extends Piece>> bindings;

  public Class<Piece> baseType;

  public static Gson makeGsonWithTypeAdapter(){
    GsonBuilder gb = new GsonBuilder();
    gb.registerTypeHierarchyAdapter(Piece.class, defaultDeserializer());
    return gb.create();
  }

  public Class<Piece> getBaseType(){
    return baseType;
  }

  public static PieceDeserializer defaultDeserializer(){
    Map<String, Class<? extends Piece>> bindings = new HashMap<>();

    bindings.put(PieceNames.BeamSplitter, BeamSplitter.class);
    bindings.put(PieceNames.Block, Block.class);
    bindings.put(PieceNames.DiagonalMirror, DiagonalMirror.class);
    bindings.put(PieceNames.HorizontalMirror, HorizontalMirror.class);
    bindings.put(PieceNames.Hypercube, Hypercube.class);
    bindings.put(PieceNames.King, King.class);
    bindings.put(PieceNames.Laser, Laser.class);
    bindings.put(PieceNames.TriangularMirror, TriangularMirror.class);

    return new PieceDeserializer(bindings);
  }

  PieceDeserializer() {
    bindings = new HashMap<>();
    baseType = Piece.class;
  }

  public PieceDeserializer(Map<String, Class<? extends Piece>> mapping) {
      this();
      if (mapping != null) bindings = mapping;

  }

  public void setBinding(String name, Class<? extends Piece> classObj){
    bindings.put(name, classObj);
  }

  @Override
  public Piece deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    if(jsonElement == null) return null;

    String t = jsonElement.getAsJsonObject().get("typeName").getAsString();

    if(bindings.containsKey(t)) return new Gson().fromJson(jsonElement, bindings.get(t));

    return null;
  }
}
