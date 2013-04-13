package lolwut;

import java.awt.Shape;

/**
 * The abstract class that represents a Lolwut enemy.
 * The basic types of enemies are Kirbeh, Pickatchu,
 * Nian, and Dohmoh.
 * @author Anran Li
 */
public abstract class Enemy extends PhysicalObject
{
  /**
   * Default horizontal velocity for enemies.
   */
  public static final double ENEMY_HORIZONTAL_VELOCITY = 2.0;
  /**
   * Default horizontal velocity for Pickatchu (fast) enemies.
   */
  public static final double PICKATCHU_HORIZONTAL_VELOCITY = 4.0;
  
  /**
   * Default enemy height. 
   */
  public static final double ENEMY_HEIGHT = 50.0;
  /**
   * Default enemy width.
   */
  public static final double ENEMY_WIDTH = 50.0;
  /**
   * Default Dohmoh (large) enemy height.
   */
  public static final double DOHMOH_HEIGHT = 100.0;
  /**
   * Default Dohmoh (large) enemy width.
   */
  public static final double DOHMOH_WIDTH = 100.0;
  
	/**
	 * Constructs an <code>Enemy</code> with the given parameters.
	 * @param x The x-coordinate of the upper left corner of the <code>Enemy</code>
	 * @param y The y-coordinate of the upper left corner of the <code>Enemy</code>
	 * @param width The width of the <code>Enemy</code>
	 * @param height The height of the <code>Enemy</code>
	 * @param imagePath The path to the image of the <code>Enemy</code>
	 */
	public Enemy(double x, double y, double width, double height, String imagePath)
  {
    super(x, y, width, height, imagePath);
  }
  
  /**
   * Gets the area that can harm the Penguin.
   * @return A <code>java.awt.Shape</code> of area that can harm the player
   */
  public abstract Shape getDeadlyArea();
  /**
   * Gets the area that the Penguin can kill this <code>Enemy</code> by stepping on.
   * @return A <code>java.awt.Shape</code> of area that can harm the <code>Enemy</code>
   */
  public abstract Shape getVulnerableArea();
}
