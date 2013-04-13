package lolwut;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that creates the <code>Platform</code>s for Lolwut. 
 * @author Anran Li
 */
public class LevelMaker
{
  /**
   * The pathname for the platform images
   */
  public static final String PLATFORM_IMAGE = "img/rainbow.png";
  
  /**
   * Generates the <code>Platform</code>s for Lolwut.
   * @return A <code>List</code> of <code>Platform</code>s
   */
  public static List<Platform> getPlatforms()
  {
    ArrayList<Platform> platforms = new ArrayList<Platform>();
    platforms.add(new Platform(50, 500, 700, 50, PLATFORM_IMAGE));
    platforms.add(new Platform(-150, 800, 300, 15, PLATFORM_IMAGE));
    platforms.add(new Platform(400, 400, 400, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(300, 300, 200, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(600, 200, 200, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(500, 100, 200, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(300, 0, 200, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(600, -100, 200, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(700, -200, 200, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(300, 300, 200, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(300, -300, 400, 50, PLATFORM_IMAGE));
    
    platforms.add(new Platform(400, -400, 400, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(600, -600, 300, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(700, -500, 200, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(850, -700, 500, 50, PLATFORM_IMAGE));
    platforms.add(new Platform(900, -800, 200, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(850, -900, 500, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(1000, -1000, 100, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(1150, -1040, 200, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(1270, -1250, 200, 20, PLATFORM_IMAGE));
    
    platforms.add(new Platform(1300, -1200, 500, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(1400, -1100, 100, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(1450, -1100, 200, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(1500, -1040, 230, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(1600, -1000, 400, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(1750, -900, 500, 50, PLATFORM_IMAGE));
    platforms.add(new Platform(1700, -800, 400, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(1850, -740, 200, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(1900, -600, 300, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(2070, -550, 200, 20, PLATFORM_IMAGE));
    
    platforms.add(new Platform(2100, -400, 200, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(2450, -500, 500, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(2500, -540, 230, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(2600, -400, 400, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(2750, -300, 500, 50, PLATFORM_IMAGE));
    platforms.add(new Platform(2700, -200, 100, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(2850, -140, 200, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(2700, -100, 500, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(3070, 0, 200, 20, PLATFORM_IMAGE));
    
    platforms.add(new Platform(2900, 100, 200, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(2840, 200, 400, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(2750, 300, 300, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(2600, 440, 230, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(2600, 500, 400, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(2450, 600, 500, 50, PLATFORM_IMAGE));
    platforms.add(new Platform(2300, 700, 100, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(2350, 840, 200, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(2200, 900, 500, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(2370, 1000, 200, 20, PLATFORM_IMAGE));
    
    platforms.add(new Platform(2100, 1100, 200, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(2040, 1200, 100, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(1950, 1300, 500, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(1800, 1440, 230, 10, PLATFORM_IMAGE));
    
    platforms.add(new Platform(1650, 1500, 500, 50, PLATFORM_IMAGE));
    platforms.add(new Platform(1400, 1600, 100, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(1350, 1740, 200, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(700, 1800, 2500, 100, PLATFORM_IMAGE));
    platforms.add(new Platform(1170, 1700, 500, 10, PLATFORM_IMAGE));
    
    platforms.add(new Platform(1050, 1620, 500, 50, PLATFORM_IMAGE));
    platforms.add(new Platform(900, 1500, 100, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(850, 1440, 200, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(850, 1340, 200, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(700, 1200, 400, 10, PLATFORM_IMAGE));
    
    platforms.add(new Platform(750, 1120, 500, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(660, 1070, 500, 30, PLATFORM_IMAGE));
    platforms.add(new Platform(500, 1000, 300, 20, PLATFORM_IMAGE));
    platforms.add(new Platform(250, 940, 200, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(300, 800, 500, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(200, 700, 500, 10, PLATFORM_IMAGE));
    platforms.add(new Platform(100, 600, 200, 20, PLATFORM_IMAGE));
    
    return platforms;
  }
}
