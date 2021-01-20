package net.ddns.whomagoo.laserchess.game;

import net.ddns.whomagoo.laserchess.game.move.Move;
import net.ddns.whomagoo.laserchess.game.piece.GamePiece;
import net.ddns.whomagoo.laserchess.game.piece.Hypercube;
import net.ddns.whomagoo.laserchess.game.piece.Piece;

import java.io.Serializable;
import java.util.*;

;

public class Board implements Serializable {

  public int sizeX;
  private Collection<LaserSegment> laserPath;
  private boolean laserFired;

  private int getSizeX() {
    return sizeX;
  }

  private int movesTaken;

  private String curTeamTurn;

  private List<String> teamOrder;
  private Iterator<String> teamIterator;

  private boolean hasTeleported;

  private int sizeY;
  public int getSizeY() {
    return sizeY;
  }

  private Piece[][] items;

  public Board(int sizeX, int sizeY, List<String> teamOrder) {
    init(sizeX, sizeY);
  }

  public Board(int edgeSize, List<String> teamOrder){
    init(edgeSize, edgeSize, teamOrder);
  }

  private void init(int sizeX, int sizeY){
    this.sizeX = sizeX;
    this.sizeY = sizeY;

    laserFired = false;

    movesTaken = 0;

    items = new GamePiece[sizeX][sizeY];
    laserPath = new ArrayList<>();
  }

  private void init(int sizeX, int sizeY, List<String> teamOrder){
    teamIterator = teamOrder.iterator();
    this.teamOrder = teamOrder;
    curTeamTurn = teamIterator.next();
    init(sizeX, sizeY);
  }


  public Piece getPiece(int xPos, int yPos){
    return items[xPos][yPos];
  }

  public Piece setPiece(int xPos, int yPos, Piece newPiece){
    //Remove old piece
    Piece removed = removePiece(xPos, yPos);
    items[xPos][yPos] = newPiece;
    if(newPiece != null) {
      newPiece.setXPos(xPos);
      newPiece.setYPos(yPos);
    }

    return removed;
  }

  public boolean inBounds(int xPos, int yPos){
   return  (xPos >= 0 && xPos < sizeX)
       && (yPos >= 0 && yPos < sizeY);
  }

  public boolean isEmpty(int xPos, int yPos){
    return inBounds(xPos, yPos) && (items[xPos][yPos] == null);
  }

  public boolean isNeutral(int xPos, int yPos){
    return inBounds(xPos, yPos) && items[xPos][yPos] == null;
  }

  public List<Move> getMoves(Piece gamePiece){
    if(gamePiece == null
        || "__environment__".equals(gamePiece.teamName())
        || !curTeamTurn.equals(gamePiece.teamName())){
      return new ArrayList<>();
    }

    List<Move> possibleMoves = gamePiece.getAllPossibleMoves();

    List<Move> legalMoves = gamePiece.getValidMoves(possibleMoves, this);

    if(!hasTeleported && hasNeighbor(gamePiece.xPos(), gamePiece.yPos(), 4, 4)){
      legalMoves.add(new Move("Move " + Directions.getDirection(gamePiece.xPos(), gamePiece.yPos(), 4, 4), new Location(4,4), gamePiece));
    }

    return legalMoves;
  }

  public boolean hasNeighbor(int xOrigin, int yOrigin, int xOther, int yOther){
    return (xOrigin + 1 == xOther && yOrigin == yOther) ||
        (xOrigin - 1 == xOther && yOrigin == yOther) ||
        (yOrigin + 1 == yOther && xOrigin == yOther) ||
        (yOrigin - 1 == yOther && xOrigin == yOther);
  }

  private Piece removePiece(int x, int y){
    if(inBounds(x, y)) {
      Piece removing = items[x][y];
      if (removing != null) {
        removing.setXPos(-1);
        removing.setYPos(-1);
      }
      items[x][y] = null;
      return removing;
    }

    return null;
  }

  private void respawnPiece(Piece pieceToMove){
    hasTeleported = true;
    ArrayList<Location> possibleMoves = new ArrayList<>(sizeX * sizeY);

    for(int x = 0; x < sizeX; x++){
      for(int y = 0; y < sizeY; y++){
        if(isEmpty(x,y)){
          possibleMoves.add(new Location(x,y));
        }
      }
    }

    Location newLocation = possibleMoves.get((int) (Math.random() * possibleMoves.size()));
    setPiece(newLocation.getKey(), newLocation.getValue(), pieceToMove);

    int rotationCount = (int) (Math.random() * 4);
    for(int i = 0; i < rotationCount; i++) {
      pieceToMove.rotate(true);
    }
  }

  public Piece movePiece(int oldXPos, int oldYPos, int newXPos, int newYPos, Piece pieceToMove){

    if(newXPos == 4 && newYPos == 4){
      items[oldXPos][oldYPos] = null;
      respawnPiece(pieceToMove);
      return null;
    }

    Piece result = setPiece(newXPos, newYPos, items[oldXPos][oldYPos]);
    items[oldXPos][oldYPos] = null;

    if(pieceToMove instanceof Hypercube){
      if(result != null){
        respawnPiece(result);
      }
    }

    return result;
  }

  public Piece movePiece(Piece piece, int newXPos, int newYPos){
    return movePiece(piece.xPos(), piece.yPos(), newXPos, newYPos, piece);
  }

  public void rotate(int xPos, int yPos, boolean clockwise){
    Piece item = items[xPos][yPos];
    if(item != null){
      item.rotate(clockwise);
    }
  }

  public String toString(){

    StringBuilder result = new StringBuilder();
    for(int y = sizeY - 1; y >= 0; y--){
      for(int x = 0; x < sizeX; x++){
        result.append("|");
        if(items[x][y] != null) {
          result.append(items[x][y]);
        } else {
          result.append("___");
        }
      }

      result.append("|\n");
    }

    return result.toString();
  }


  public boolean isSameTeam(int targetX, int targetY, String teamName) {
    return inBounds(targetX, targetY)
        && items[targetX][targetY] != null
        && items[targetX][targetY].teamName().equals(teamName);
  }

  public void setLaserPath(Collection<LaserSegment> result) {
    if(result == null){
      result = new ArrayList<>();
    }

    for(LaserSegment segment : result){
      if(Directions.DESTROYED.equals(segment.getDirectionOut())){
        removePiece(segment.getLocX(), segment.getLocY());
      }
    }

    this.laserFired = true;
    this.laserPath = Collections.unmodifiableCollection(result);
  }

  public Collection<LaserSegment> getLaserPath(){
    return laserPath;
  }

  public String getCurTeamTurn(){
    return curTeamTurn;
  }

  private void nextTeam() {
    if (!teamIterator.hasNext()) {
      teamIterator = teamOrder.listIterator();
    }
    curTeamTurn = teamIterator.next();
    hasTeleported = false;
    laserFired = false;
  }

  public void moveTaken() {
    if(movesTaken == 1){
      movesTaken = 0;
      nextTeam();
    } else {
      movesTaken++;
    }
  }

  public boolean hasTeleported() {
    return hasTeleported;
  }

  public boolean laserFired() {
    return laserFired;
  }
}
