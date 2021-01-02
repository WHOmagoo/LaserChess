package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.move.Move;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BeamSplitter extends GamePiece {
  public BeamSplitter(String teamName) {
    super(teamName);
    typeName = "Beam Splitter";
  }

  @Override
  public List<Move> getAllPossibleMoves() {
    return Move.defaultMoves(xPos, yPos, this);
  }

  @Override
  public List<String> hit(String direction) {
    if (facing.equals(direction)){
      return Collections.singletonList(Directions.DESTROYED);
    }

    if(Directions.opposite(facing).equals(direction)){
      //Should split the beam into two here
      return Arrays.asList(Directions.counterClockwise(direction), Directions.clockwise(direction));
    }

    return Collections.singletonList(Directions.opposite(facing));
  }
}
