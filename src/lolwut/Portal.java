package lolwut;

/**
 * A class that represent the portals used to end the game and
 * the fake portals to distract the player.
 * @author Anran Li
 */
public class Portal extends PhysicalObject{
  private boolean isCorrectPortal;
  /**
   * The default width of a portal 
   */
  public static final double PORTAL_WIDTH = 30.0;
  /**
   * The default height of a portal
   */
  public static final double PORTAL_HEIGHT = 50.0;
  /**
   * The default horizontal velocity of a portal
   */
  public static final double PORTAL_HORIZONTAL_VELOCITY = 0;

  /**
   * Constructs a new <code>Portal</code> with the given parameters that is NOT the
   * endgame portal.
   * @param x The x-coordinate of the upper left corner
   * @param y The y-coordinate of the upper left corner
   * @param width The width
   * @param height The height
   * @param imagePath The pathname to the image
   */
  public Portal(double x, double y, double width, double height,
      String imagePath) {
    super(x, y, width, height, imagePath);
    isCorrectPortal = false;
  }

  /**
   * Gets whether this is the endgame portal
   * @return <code>true</code> if this portal ends the game, <code>false</code> otherwise
   */
  public boolean getIsCorrectPortal(){
    return isCorrectPortal;
  }

  /**
   * Sets whether this is the endgame portal
   * @param isCorrectPortal <code>true</code> if this portal ends the game, <code>false</code> otherwise
   */
  public void setIsCorrectPortal(boolean isCorrectPortal){
    this.isCorrectPortal = isCorrectPortal;
  }
}
