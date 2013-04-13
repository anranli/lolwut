package lolwut;

/**
 * A class that represents items to get the player points.
 * @author Anran Li
 */
public class PointItem extends Item{
  /**Constructs a new <code>PhysicalObject</code> with the given parameters.
   * @param x The x-coordinate
   * @param y The y-coordinate
   * @param width The width
   * @param height The height
   * @param imagePath The pathname to the image
   */
	public PointItem(double x, double y, double width, double height,
			String imagePath) {
		super(x, y, width, height, imagePath);
	}
	/**
	 * Constructs a new <code>PhysicalObject</code> with the given parameters.
   * @param x The x-coordinate
   * @param y The y-coordinate
   * @param width The width
   * @param height The height
   * @param imagePath The pathname to the image
   * @param pointValue the number of points the item is worth
	 */
	public PointItem(double x, double y, double width, double height,
			String imagePath, int pointValue) {
		super(x, y, width, height, imagePath);
		setPointValue(pointValue);
	}

	@Override
	public void activate(Penguin player) {
	}
}
