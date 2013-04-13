package lolwut;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * A class that represents an <code>Enemy</code> that flies through space.
 * @author Anran Li
 */
public class FlyingEnemy extends Enemy{

  protected static final double DEFAULT_VULNERABILITY_BUFFER_MULTIPLIER = 0.3;
  protected double vulnerabilityBuffer;

  /**
   * Constructs a <code>FlyingEnemy</code> with the given parameters.
   * @param x The x-coordinate of the upper left corner
   * @param y The y-coordinate of the upper left corner
   * @param width The width
   * @param height The height
   * @param imagePath The pathname of the image
   */
  public FlyingEnemy(double x, double y, double width, double height,
      String imagePath) {
    super(x, y, width, height, imagePath);
    vulnerabilityBuffer = height * DEFAULT_VULNERABILITY_BUFFER_MULTIPLIER;
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
