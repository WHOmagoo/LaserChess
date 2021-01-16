package net.ddns.whomagoo.laserchess.game.move;

import javafx.util.Pair;
import net.ddns.whomagoo.laserchess.game.Board;
import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class Move {
  public static final String MOVE_NORTH = "Move North";
  public static final String MOVE_EAST = "Move East";
  public static final String MOVE_SOUTH = "Move South";
  public static final String MOVE_WEST = "Move West";
  public static final String ROTATE_CLOCKWISE = "Rotate Clockwise";
  public static final String ROTATE_COUNTERCLOCKWISE = "Rotate Counterclockwise";
  public static final String FIRE_LASER = "Fire Laser";

  private String name;
  private int targetX;
  private int targetY;
  private MoveDoer move;
  private Piece source;



  public Move(String name, int targetX, int tagetY, Piece source){
      this.name = name;
      this.targetX = targetX;
      this.targetY = tagetY;
      this.source = source;
      switch (name){
        case MOVE_EAST:
        case MOVE_NORTH:
        case MOVE_SOUTH:
        case MOVE_WEST:
          move = new Relocate();
          break;
        case ROTATE_CLOCKWISE:
          move = new Rotation(true);
          break;
        case ROTATE_COUNTERCLOCKWISE:
          move = new Rotation(false);
          break;
        case FIRE_LASER:
          move = new FireLaser();
      }
  }

  public Move(String name, Pair<Integer, Integer> targetLoc, Piece source){
    this(name, targetLoc.getKey(), targetLoc.getValue(), source);
  }

  public Piece getSource(){
    return source;
  }

  public String getName(){
    return name;
  }

  public int getTargetX(){
    return targetX;
  }

  public int getTargetY(){
    return targetY;
  }

  public void doMove(Board board){
    move.doMove(board, this);
    System.out.println(this);
  }

  public String toString(){
    return String.format("%s (%d, %d)", name, targetX, targetY);
  }

  public static List<Move> movingMoves(int xPos, int yPos, Piece source){
    List<Move> result = new ArrayList<>();

    Pair<Integer, Integer> loc = new Pair<Integer, Integer>(xPos, yPos);
    result.add(new Move(Move.MOVE_NORTH, Directions.getLocationInDirection(loc, Directions.NORTH), source));
    result.add(new Move(Move.MOVE_SOUTH, Directions.getLocationInDirection(loc, Directions.SOUTH), source));
    result.add(new Move(Move.MOVE_EAST, Directions.getLocationInDirection(loc, Directions.EAST), source));
    result.add(new Move(Move.MOVE_WEST, Directions.getLocationInDirection(loc, Directions.WEST), source));

    return result;
  }

  public static List<Move> defaultMoves(int xPos, int yPos, Piece source){
    List<Move> result = movingMoves(xPos, yPos, source);
    result.add(new Move(Move.ROTATE_CLOCKWISE, xPos, yPos, source));
    result.add(new Move(Move.ROTATE_COUNTERCLOCKWISE, xPos, yPos, source));
    return result;
  }

  public static List<Move> allMoves(int xPos, int yPos, Piece source){
    List<Move> result = defaultMoves(xPos, yPos, source);
    Pair<Integer, Integer> fireLoc = Directions.getLocationInDirection(new Pair<>(source.xPos(), source.yPos()), source.facing(), 2);
    result.add(new Move(Move.FIRE_LASER, fireLoc.getKey(), fireLoc.getValue(), source));
    return result;
  }

  public static List<Move> moveToNeutralAndInBounds(List<Move> moves, Board board){
    List<Move> result = new ArrayList<>(moves.size());
    for (Move move : moves){
      if(move.getName().startsWith("Move")){
        if(board.isEmpty(move.getTargetX(), move.getTargetY())) {
          result.add(move);
        }
      }else {
        result.add(move);
      }
    }

    return result;
  }

}
