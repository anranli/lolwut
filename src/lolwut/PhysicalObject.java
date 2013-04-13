package lolwut;

import java.awt.geom.Rectangle2D;

/**
 * An abstract class that represents all physical objects that can
 * be displayed in Lolwut.
 * @author Daniel Wong
 */
public abstract class PhysicalObject
{
  private double x, y, width, height, horizontalVelocity, verticalVelocity;
  private String imagePath;
  
  /**
   * Constructs a new <code>PhysicalObject</code> with the given parameters.
   * @param x The x-coordinate
   * @param y The y-coordinate
   * @param width The width
   * @param height The height
   * @param imagePath The pathname to the image
   */
  public PhysicalObject(double x, double y, double width, double height, String imagePath)
  {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.imagePath = imagePath;
  }
    
  /**
   * Constructs and returns a new <code>Rectangle2D.Double</code> that surrounds this <code>PhysicalObject</code>.
   * The <code>Rectangle2D.Double</code> is constructed based on the <code>PhysicalObject</code>'s coordinates and size.
   * @return A <code>Rectangle2D.Double</code> that surrounds this <code>PhysicalObject</code>
   */
  public Rectangle2D.Double getRect()
  {
    return new Rectangle2D.Double(x, y, width, height);
  }
  
  /**
   * Moves this <code>PhysicalObject</code> to the given coordinates.
   */
  public void moveTo(double x, double y)
  {
    this.x = x;
    this.y = y;
  }
  
  /**
   * Moves this <code>PhysicalObject</code> according to its horizontal and vertical velocity.
   */
  public void move()
  {
    x += horizontalVelocity;
    y += verticalVelocity;
  }
  
  /**
   * Changes the acceleration of this <code>PhysicalObject</code> based on the parameters.
   * @param horizontalAcc The horizontal change in velocity
   * @param verticalAcc The vertical change in velocity
   */
  public void accelerate(double horizontalAcc, double verticalAcc)
  {
    horizontalVelocity += horizontalAcc;
    verticalVelocity += verticalAcc;
  }
  
  /**
   * Sets the velocity to the given parameters.
   * @param horizontalVelocity The new horizontal velocity
   * @param verticalVelocity The new vertical velocity
   */
  public void setVelocity(double horizontalVelocity, double verticalVelocity)
  {
    this.horizontalVelocity = horizontalVelocity;
    this.verticalVelocity = verticalVelocity;
  }
  
  /**
   * Gets the x-coordinate of this <code>PhysicalObject</code>.
   * @return The x-coordinate of this object
   */
  public double getX()
  {
    return x;
  }
  
  /**
   * Gets the x-coordinate of the center of this <code>PhysicalObject</code>.
   * @return The x-coordinate of the center of this object
   */
  public double getCenterX()
  {
    return x + width/2.0;
  }

  /**
   * Sets the x-coordinate of this <code>PhysicalObject</code>.
   * @param x The x-coordinate to set
   */
  public void setX(double x)
  {
    this.x = x;
  }

  /**
   * Gets the y-coordinate of this <code>PhysicalObject</code>.
   * @return The y-coordinate of this object
   */
  public double getY()
  {
    return y;
  }
  
  /**
   * Gets the y-coordinate of the center of this <code>PhysicalObject</code>.
   * @return The y-coordinate of the center of this object
   */
  public double getCenterY()
  {
    return y + height/2.0;
  }

  /**
   * Sets the y-coordinate of this <code>PhysicalObject</code>.
   * @param y The y-coordinate to set
   */
  public void setY(double y)
  {
    this.y = y;
  }

  /**
   * Gets the width of this <code>PhysicalObject</code>.
   * @return The width
   */
  public double getWidth()
  {
    return width;
  }

  /**
   * Sets the width of this <code>PhysicalObject</code>.
   * @param width The width to set
   */
  public void setWidth(double width)
  {
    this.width = width;
  }

  /**
   * Gets the height of this <code>PhysicalObject</code>.
   * @return The height
   */
  public double getHeight()
  {
    return height;
  }

  /**
   * Sets the height of this <code>PhysicalObject</code>.
   * @param height The height to set
   */
  public void setHeight(double height)
  {
    this.height = height;
  }

  /**
   * Gets the horizontal velocity of this <code>PhysicalObject</code>.
   * @return the xVelocity
   */
  public double getHorizontalVelocity()
  {
    return horizontalVelocity;
  }

  /**
   * Sets the horizontal velocity of this <code>PhysicalObject</code>.
   * @param horizontalVelocity The horizontalVelocity to set
   */
  public void setHorizontalVelocity(double horizontalVelocity)
  {
    this.horizontalVelocity = horizontalVelocity;
  }

  /**
   * Gets the vertical velocity of this <code>PhysicalObject</code>.
   * @return The vertical velocity
   */
  public double getVerticalVelocity()
  {
    return verticalVelocity;
  }

  /**
   * Sets the vertical velocity of this <code>PhysicalObject</code>.
   * @param verticalVelocity The vertical velocity to set
   */
  public void setVerticalVelocity(double verticalVelocity)
  {
    this.verticalVelocity = verticalVelocity;
  }

  /**
   * Gets the pathname to this <code>PhysicalObject</code>'s image.
   * @return The pathname for this <code>PhysicalObject</code>'s image
   */
  public String getImagePath()
  {
    return imagePath;
  }

  /**
   * Sets the pathname for this <code>PhysicalObject</code>'s image.
   * @param imagePath The pathname to set
   */
  public void setImagePath(String imagePath)
  {
    this.imagePath = imagePath;
  }
}