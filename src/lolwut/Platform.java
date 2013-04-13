package lolwut;

/**
 * A class that represents platforms that can be stood on
 * by the player and by enemies and items
 * @author Daniel Wong
 */
public class Platform extends PhysicalObject
{
  /**
   * Constructs a new <code>Platform</code> with the given parameters.
   * @param x The x-coordinate of the upper left corner
   * @param y The y-coordinate of the upper left corner
   * @param width The width
   * @param height The height
   * @param imagePath The pathname to the image
   */
  public Platform(double x, double y, double width, double height, String imagePath)
  {
    super(x, y, width, height, imagePath);
  }
}
