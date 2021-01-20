package net.ddns.whomagoo.laserchess.resources;

import java.io.InputStream;

/**
 * Hello world!
 *
 */
public class ResourceGetter
{
    public static final String colorConfig = "TeamColors.json";

    public static InputStream getResourceAsStream(String resourceName){
        return ResourceGetter.class.getClassLoader().getResourceAsStream(resourceName);
    }

    public static void main(String[] args) {
    }
}
