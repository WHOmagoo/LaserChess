package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Board;
import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.move.Move;

import java.util.ArrayList;
import java.util.List;

public abstract class GamePiece implements Piece{
  protected String teamName = "";

  protected String typeName = "";

  protected String facing = "";

  protected int xPos = 0;

  protected int yPos = 0;

  public GamePiece(String teamName){
    this.teamName = teamName;
  }

  public String teamName(){
    return teamName;
  }

  public String typeName(){
    return typeName;
  }

  public String facing(){
    return facing;
  }

  public void setFacing(String facing){
    this.facing = facing;
  }

  public int xPos(){
    return xPos;
  }

  public void setXPos(int newPos){
    xPos = newPos;
  }

  public int yPos(){
    return yPos;
  }

  public void setYPos(int newPos){
    yPos = newPos;
  }

  public void rotate(boolean clockwise){
    setFacing(Directions.rotate(facing, clockwise));
  }

  public String toString(){
    return String.format("%s %s facing %s", teamName, typeName, facing);
  }

  @Override
  public List<Move> getValidMoves(List<Move> moves, Board gp) {
    ArrayList<Move> result = new ArrayList<Move>(moves.size());

    for(Move m : moves){
      if (m.getName().startsWith("Move") && gp.isSameTeam(m.getTargetX(), m.getTargetY(), teamName)){
        continue;
      }

      if(m.getName().equals(Move.FIRE_LASER)){
        //Calculates directly in front of the Laser in in bounds
        if (!gp.inBounds((m.getTargetX() + m.getSource().xPos()) / 2, (m.getTargetY() + m.getSource().yPos()) / 2)){
          continue;
        }
      }

      result.add(m);
    }

    return result;
  }

}
