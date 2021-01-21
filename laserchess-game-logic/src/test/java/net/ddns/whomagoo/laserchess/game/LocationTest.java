package net.ddns.whomagoo.laserchess.game;

import org.junit.Assert;
import org.junit.Test;

public class LocationTest {

  @Test
  public void testConstructor(){
    Location l = new Location(0,0);
    Assert.assertNotNull(l);
  }

  @Test
  public void testLocationX(){
    Location l = new Location(5,0);

    Assert.assertEquals(5, l.getX());
  }

  @Test
  public void testLocationY(){
    Location l = new Location(0,5);

    Assert.assertEquals(5, l.getY());
  }

  @Test
  public void testSetterX(){
    Location l = new Location(0,1);
    l.setX(2);

    Assert.assertEquals(2, l.getX());
  }

  @Test
  public void testSetterY(){
    Location l = new Location(0,1);
    l.setY(2);

    Assert.assertEquals(2, l.getY());
  }


}
