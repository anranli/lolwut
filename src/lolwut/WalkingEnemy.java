package lolwut;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * A class that represents enemies that walk on platforms.
 * @author Anran Li
 */
public class WalkingEnemy extends Enemy
{
  protected static final double DEFAULT_VULNERABILITY_BUFFER_MULTIPLIER = 0.3;
  
  private Platform platform;
  protected double vulnerabilityBuffer;
  
  /**
   * Constructs a new <code>WalkingEnemy</code> with the given parameters.
   * @param x The x-coordinate of the upper left corner
   * @param y The y-coordinate of the upper left corner
   * @param width The width
   * @param height The height
   * @param imagePath The pathname to the image
   */
  public WalkingEnemy(double x, double y, double width, double height,
      String imagePath)
  {
    super(x, y, width, height, imagePath);
    vulnerabilityBuffer = height * DEFAULT_VULNERABILITY_BUFFER_MULTIPLIER;
  }
  
  /**
   * Constructs a new <code>WalkingEnemy</code> with the given parameters.
   * @param x The x-coordinate of the upper left corner
   * @param y The y-coordinate of the upper left corner
   * @param width The width
   * @param height The height
   * @param imagePath The pathname to the image
   * @param platform The platform that the <code>WalkingEnemy</code> rests on
   */
  public WalkingEnemy(double x, double y, double width, double height,
      String imagePath, Platform platform)
  {
    super(x, y, width, height, imagePath);
    this.platform = platform;
  }
  
  /* (non-Javadoc)
   * @see lolwut.PhysicalObject#move()
   */
  public void move()
  {
    if (isOnPlatform() && ((this.getX() <= platform.getX() && this.getHorizontalVelocity() < 0) || (this.getX() + this.getWidth() >= platform.getX() + platform.getWidth() && this.getHorizontalVelocity() > 0)))
      this.setHorizontalVelocity(-this.getHorizontalVelocity());
    super.move();
  }
  
  /**
   * Gets whether this <code>WalkingEnemy</code> is on a platform
   * @return <code>true</code> if on a platform, <code>false</code> otherwise
   */
  public boolean isOnPlatform()
  {
    return platform != null;
  }

  /**
   * Gets the platform that this <code>WalkingEnemy</code> is on
   * @return the platform
   */
  public Platform getPlatform()
  {
    return platform;
  }

  /**
   * Sets the platform that this <code>WalkingEnemy</code> is on
   * @param platform the platform to set
   */
  public void setPlatform(Platform platform)
  {
    this.platform = platform;
  }

  @Override
  public Shape getDeadlyArea()
  {
    return new Rectangle2D.Double(this.getX(), this.getY()+vulnerabilityBuffer, this.getWidth(), this.getHeight()-this.vulnerabilityBuffer);
  }

  @Override
  public Shape getVulnerableArea()
  {
    return new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.vulnerabilityBuffer);
  }
}