package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Directions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalMirrorTest {

  @Test
  public void testOnHitNorthNorth(){
    DiagonalMirror b = new DiagonalMirror("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.NORTH);

    assertEquals(1, resultingDirections.size());
    assertEquals(Directions.EAST, resultingDirections.get(0));
  }

  @Test
  public void testOnHitNorthSouth(){
    DiagonalMirror b = new DiagonalMirror("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.SOUTH);

    assertEquals(1, resultingDirections.size());
    assertTrue(resultingDirections.contains(Directions.WEST));
  }

  @Test
  public void testOnHitNorthEast(){
    DiagonalMirror b = new DiagonalMirror("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.EAST);

    assertEquals(1, resultingDirections.size());
    assertEquals(Directions.NORTH, resultingDirections.get(0));
  }

  @Test
  public void testOnHitNorthWest(){
    DiagonalMirror b = new DiagonalMirror("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.WEST);

    assertEquals(1, resultingDirections.size());
    assertEquals(Directions.SOUTH, resultingDirections.get(0));
  }

}