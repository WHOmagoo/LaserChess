package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Directions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BeamSplitterTest {


  @Test
  public void testOnHitNorthNorth(){
    BeamSplitter b = new BeamSplitter("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.NORTH);

    assertEquals(1, resultingDirections.size());
    assertEquals(Directions.DESTROYED, resultingDirections.get(0));
  }

  @Test
  public void testOnHitNorthSouth(){
    BeamSplitter b = new BeamSplitter("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.SOUTH);

    assertEquals(2, resultingDirections.size());
    assertTrue(resultingDirections.contains(Directions.EAST));
    assertTrue(resultingDirections.contains(Directions.WEST));
  }

  @Test
  public void testOnHitNorthEast(){
    BeamSplitter b = new BeamSplitter("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.EAST);

    assertEquals(1, resultingDirections.size());
    assertEquals(Directions.SOUTH, resultingDirections.get(0));
  }

  @Test
  public void testOnHitNorthWest(){
    BeamSplitter b = new BeamSplitter("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.WEST);

    assertEquals(1, resultingDirections.size());
    assertEquals(Directions.SOUTH, resultingDirections.get(0));
  }
}