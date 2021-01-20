package net.ddns.whomagoo.laserchess.resources;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class ResourceGetterTest
{

    @Test
    public void colorConfigExists(){
        assertNotNull(ResourceGetter.getResourceAsStream(ResourceGetter.colorConfig));
    }
}
