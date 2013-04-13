package lolwut;

/**
 * The class that represents the player. The Penguin can switch between
 * Penguin and Bunny (powered up) modes.
 * @author Anran Li
 */
public class Penguin extends PhysicalObject
{
  /**
   * The default width
   */
  public static final double PENGUIN_WIDTH = 50;
  /**
   * The default height
   */
  public static final double PENGUIN_HEIGHT = 50;
  /**
   * The maximum speed in penguin mode
   */
  public static final double MAX_PENGUIN_SPEED = 5;
  /**
   * The maximum speed in bunny mode
   */
  public static final double MAX_BUNNY_SPEED = 10;
  /**
   * The fasted speed at which the penguin will naturally fall
   */
  public static final double DEFAULT_TERMINAL_VELOCITY = 16;
  /**
   * The speed at which the penguin jumps off the ground
   */
  public static final double DEFAULT_JUMP_SPEED = 18; 
  /**
   * The speed at which the penguin bounces off an enemy when killing it
   */
  public static final double DEFAULT_ENEMY_JUMP_SPEED = 12;
  /**
   * The default rate of movement acceleration
   */
  public static final double DEFAULT_RUN_ACCELERATION = 1;
  /**
   * The default rate of skidding deccleration when no longer moving
   */
  public static final double DEFAULT_SKID_DECCELERATION = 0.5;

  /**
   * The state of standing on a platform
   */
  public static final int STANDING = 0;
  /**
   * The state of jumping or falling through the air
   */
  public static final int JUMPING = 1;

  /**
   * The image pathname of a standing penguin
   */
  public static final String PENGUIN_STAND_IMAGEPATH = "img/penguin.gif";
  /**
   * The image pathname of a jumping penguin
   */
  public static final String PENGUIN_JUMP_IMAGEPATH = "img/penguin_jump.png";
  /**
   * The image pathname of a standing bunny
   */
  public static final String BUNNY_STAND_IMAGEPATH = "img/bunny.gif";
  /**
   * The image pathname of a jumping bunny
   */
  public static final String BUNNY_JUMP_IMAGEPATH = "img/bunny_jump.png";

  private boolean isFacingRight;
  private boolean isBunny; //is powered up
  private boolean isDead;

  /**
   * Constructs an <code>Penguin</code> with the given parameters.
   * @param x The x-coordinate of the upper left corner of the <code>Penguin</code>
   * @param y The y-coordinate of the upper left corner of the <code>Penguin</code>
   * @param width The width of the <code>Penguin</code>
   * @param height The height of the <code>Penguin</code>
   * @param imagePath The path to the image of the <code>Penguin</code>
   */
  public Penguin(double x, double y, double width, double height, String imagePath)
  {
    super(x, y, width, height, imagePath);
    isFacingRight = true;
    isBunny = false;
  }

  /**
   * Determines if the penguin is powered up as a bunny
   * @return <code>true</code> if the penguin is powered up, <code>false</code> otherwise
   */
  public boolean isBunny(){
    return isBunny;
  }

  /**
   * Gets the maximum running speed of the penguin.
   * @return The maximum running speed
   */
  public double getMaxSpeed()
  {
    if (isBunny)
      return MAX_BUNNY_SPEED;
    return MAX_PENGUIN_SPEED;
  }

  /**
   * Gets the terminal velocity of the penguin
   * @return The terminal velocity
   */
  public double getTerminalVelocity()
  {
    return DEFAULT_TERMINAL_VELOCITY;
  }

  /**
   * Gets the speed at which the penguin jumps from the platform
   * @return The jump speed
   */
  public double getJumpSpeed()
  {
    return DEFAULT_JUMP_SPEED;
  }

  /**
   * Gets the speed at which the penguin bounces off an enemy.
   * @return The speed at which the penguin bounces off an enemy
   */
  public double getEnemyJumpSpeed()
  {
    return DEFAULT_ENEMY_JUMP_SPEED;
  }

  /**
   * Gets the rate at which the penguin accelerates when running.
   * @return The accelerate rate
   */
  public double getRunAcceleration()
  {
    return DEFAULT_RUN_ACCELERATION;
  }

  /**
   * Gets the rate at which the penguin deccelerates when skidding after running.
   * @return The skid decceleration rate
   */
  public double getSkidDecceleration()
  {
    return DEFAULT_SKID_DECCELERATION;
  }

  /**
   * Does damage to the penguin. Kills the penguin if not powered up,
   * or depowers the penguin if powered up as bunny.
   */
  public void damage()
  {
    if (isBunny)
    {
      isBunny = false;
      setImagePath(PENGUIN_STAND_IMAGEPATH);
    }
    else
      isDead = true;
  }

  /**
   * Powers up the penguin into bunny mode.
   */
  public void powerUp(){
    if (!isBunny)
    {
      isBunny = true;
      setImagePath(BUNNY_STAND_IMAGEPATH);
    }
  }
  
  /**
   * Sets whether the penguin is facing right
   * @param isFacingRight <code>true</code> if the penguin is facing right, <code>false</code> otherwise
   */
  public void setIsFacingRight(boolean isFacingRight)
  {
    this.isFacingRight = isFacingRight;
  }
  
  /**
   * Returns whether the penguin is facing right
   * @return <code>true</code> if the penguin if facing right, <code>false</code> otherwise
   */
  public boolean isFacingRight(){
    return isFacingRight;
  }

  /**
   * Sets whether the penguin is dead.
   * @param isDead <code>true</code> if the penguin is dead, <code>false</code> otherwise
   */
  public void setIsDead(boolean isDead)
  {
    this.isDead = isDead;
  }

  /**
   * Gets if the penguin is dead or alive
   * @return <code>true</code> if the penguin is dead, <code>false</code> otherwise
   */
  public boolean isDead()
  {
    return isDead;
  }

  /**
   * Sets the image path based on whether the penguin is standing or jumping
   * @param status The jumping or standing status of the penguin
   */
  public void setImagePath(int status)
  {
    if (isBunny())
    {
      if (status == JUMPING)
      {
        setImagePath(BUNNY_JUMP_IMAGEPATH);
      }
      else
      {
        setImagePath(BUNNY_STAND_IMAGEPATH);
      }
    }
    else //is Penguin
    {
      if (status == JUMPING)
      {
        setImagePath(PENGUIN_JUMP_IMAGEPATH);
      }
      else
      {
        setImagePath(PENGUIN_STAND_IMAGEPATH);
      }
    }
  }
}
