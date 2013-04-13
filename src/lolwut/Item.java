package lolwut;

/**
 * A class that represents an item that the Penguin can pickup. 
 * @author Anran Li
 */
public abstract class Item extends PhysicalObject
{
	private int pointValue;
	/**
	 * The default width for an item
	 */
	public static final double ITEM_WIDTH = 50.0;
	/**
	 * The default height for an item
	 */
	public static final double ITEM_HEIGHT = 50.0;
	/**
	 * The default horizontal velocity for an item 
	 */
	public static final double ITEM_HORIZONTAL_VELOCITY = 0;
	
  /**
   * Constructs an <code>Item</code> with the given parameters.
   * @param x The x-coordinate of the upper left corner
   * @param y The y-coordinate of the upper left corner
   * @param width The width
   * @param height The height
   * @param imagePath The pathname of the image
   */
  public Item(double x, double y, double width, double height, String imagePath)
  {
    super(x, y, width, height, imagePath);
  }
  
  /**
   * This method is called when the player collects the <code>Item</code>.
   */
  public abstract void activate(Penguin player);

	/**
	 * @return the point value
	 */
	public int getPointValue()
	{
		return pointValue;
	}

	/**
	 * @param pointValue point value to set to
	 */
	public void setPointValue(int pointValue)
	{
		this.pointValue = pointValue;
	}
}
