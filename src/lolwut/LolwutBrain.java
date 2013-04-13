package lolwut;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that represents the "brain" of the game.
 * @author Anran Li and Daniel Wong
 */
/**
 * @author Anran
 *
 */
public class LolwutBrain
{
	/**
	 * Minimum width of the window
	 */
	public static final double MIN_WIDTH = 800;
	/**
	 * Minimum height of the window
	 */
	public static final double MIN_HEIGHT = 600;

	/**
	 * Left most x value of the bounds of the level
	 */
	public static final double SMALLEST_X_POINT = 0;
	/**
	 * Right most x value of the bounds of the level
	 */
	public static final double LARGEST_X_POINT = 3500;
	/**
	 * Lowest y value of the bounds of the level
	 */
	public static final double LOWEST_Y_POINT = 2000;
	/**
	 * Highest y value of the bounds of the level
	 */
	public static final double HIGHEST_Y_POINT = -1500;
	
	/**
	 * Integer value to be used in setDisplay(int) that displays the main menu
	 */
	public static final int DISPLAY_MAIN_MENU = 0;
	/**
	 * Integer value to be used in setDisplay(int) that displays the game screen
	 */
	public static final int DISPLAY_GAME = 1;
	/**
	 * Integer value to be used in setDisplay(int) that displays the pause menu
	 */
	public static final int DISPLAY_PAUSE_MENU = 2;
	/**
	 * Integer value to be used in setDisplay(int) that displays the win menu
	 */
	public static final int DISPLAY_WIN_MENU = 3;
	/**
	 * Integer value to be used in setDisplay(int) that displays the lose menu
	 */
	public static final int DISPLAY_LOSE_MENU = 4;
	/**
	 * Integer value to be used in setDisplay(int) that displays the instructions screen
	 */
	public static final int DISPLAY_INSTRUCTIONS = 5;
	/**
	 * Integer value to be used in setDisplay(int) that displays the about screen
	 */
	public static final int DISPLAY_ABOUT = 6;
	/**
	 * Integer value to be used in setDisplay(int) that displays the controls screen
	 */
	public static final int DISPLAY_CONTROLS = 7;
	/**
	 * Integer value to be used in setDisplay(int) that displays the high scores screen
	 */
	public static final int DISPLAY_HIGH_SCORES = 8;
	
	/**
	 * Integer value to be used in setMenuItemSelected(int) that goes to a new game
	 */
	public static final int MENU_NEW_GAME = 0;
	/**
	 * Integer value to be used in setMenuItemSelected(int) that goes to the instructions screen
	 */
	public static final int MENU_INSTRUCTIONS = 1;
	/**
	 * Integer value to be used in setMenuItemSelected(int) that goes to the controls screen
	 */
	public static final int MENU_CONTROLS = 2;
	/**
	 * Integer value to be used in setMenuItemSelected(int) that goes to the high scores screen
	 */
	public static final int MENU_HIGH_SCORES = 3;
	/**
	 * Integer value to be used in setMenuItemSelected(int) that goes to the about screen
	 */
	public static final int MENU_ABOUT = 4;
	/**
	 * Integer value to be used in setMenuItemSelected(int) that exits the game
	 */
	public static final int MENU_EXIT = 5;
	/**
	 * Integer value to be used in setMenuItemSelected(int) that doesn't do anything
	 */
	public static final int MENU_NO_ACTION = -1;
	
	private static final double PENGUIN_START_X = 100;
	private static final double PENGUIN_START_Y = 300;
	private static final double END_JUMP_DECCELERATION_FACTOR = 0.2;
	private static final double GRAVITY_ACCELERATION = 1;
	private static final double PLATFORM_DETECTION_BUFFER = 10.0;
	private static final double PLATFORM_ADJUST_RATE = 1;
	private static final int SCORE_DECAY_RATE = 30;

	private int display, displayPrevious;
	private boolean isPaused, gameOver, winGame;
	private boolean penguinIsDamaged, enemyIsKilled, itemIsCollected, penguinIsPoweredUp, portalIsFake;
	private int menuItemSelected;
	
	private Penguin penguin;
	private List<Item> items;
	private List<Platform> platforms;
	private List<Enemy> enemies;
	private List<Portal> portals;

	private int score;
	private int numFrames;

	/**
	 * Creates a new LolwutBrain, intializing the items, platforms, enemies, and portals arrays and setting the game to display the main menu
	 */
	public LolwutBrain()
	{
		items = new ArrayList<Item>();
		platforms = new ArrayList<Platform>();
		enemies = new ArrayList<Enemy>();
		portals = new ArrayList<Portal>();
		
		setDisplay(DISPLAY_MAIN_MENU);
	}

	/**
	 * Creates a new game, initializing the penguin, platforms, enemies, items, portals, and score, and setting the game to display the game screen
	 */
	public void resetGame()
	{
		this.initializePenguin();
		this.initializePlatforms();
		this.initializeEnemies();
		this.initializeItems();
		this.initializePortals();
		score = 0;
		numFrames = 0;
		gameOver = false;
		winGame = false;
		isPaused = false;
		setDisplay(DISPLAY_GAME);
	}
	
	/**
	 * @return menu item that is selected
	 */
	public int getMenuItemSelected()
	{
		return menuItemSelected;
	}

	/**
	 * @param menuItemSelected menu item that is selected
	 */
	public void setMenuItemSelected(int menuItemSelected)
	{
		this.menuItemSelected = menuItemSelected;
	}
	
	/**
	 * @param display the integer that represents what to display
	 */
	public void setDisplay(int display)
	{
	  this.displayPrevious = this.display;
	  this.display = display;
	}
	
	/**
	 * Goes back to the previous display
	 */
	public void displayPrevious()
	{
	  int temp = display;
	  this.display = displayPrevious;
	  this.displayPrevious = temp;
	}
	
	/**
	 * @return the integer that represents what to display
	 */
	public int display()
	{
	  return display;
	}
	
	/**
	 * @return if a menu (main menu, pause menu, lose menu, win menu) is being displayed
	 */
	public boolean displayingMenu()
	{
	  return display() == DISPLAY_MAIN_MENU || display() == DISPLAY_PAUSE_MENU ||
	    display() == DISPLAY_LOSE_MENU || display() == DISPLAY_WIN_MENU;
	}
	
	/**
	 * @return if an information page (about, instructions, controls, high scores) is being displayed
	 */
	public boolean displayingInformation()
	{
	  return display() == DISPLAY_ABOUT || display() == DISPLAY_INSTRUCTIONS ||
	    display() == DISPLAY_CONTROLS || display() == DISPLAY_HIGH_SCORES;
	}
	
	
	/**
	 * @return the integer that represents which menu item is selected
	 */
	public int doMenuAction()
	{
	  if (this.display() == DISPLAY_GAME)
	    return MENU_NO_ACTION;
	  
	  if (this.displayingInformation())
	  {
	    displayPrevious();
	    return MENU_NO_ACTION;
	  }
	  
	  if (getMenuItemSelected() == MENU_INSTRUCTIONS)
	  {
	    setDisplay(DISPLAY_INSTRUCTIONS);
	  }
	  else if (getMenuItemSelected() == MENU_CONTROLS)
    {
      setDisplay(DISPLAY_CONTROLS);
    }
	  else if (getMenuItemSelected() == MENU_HIGH_SCORES)
    {
      setDisplay(DISPLAY_HIGH_SCORES);
    }
	  else if (getMenuItemSelected() == MENU_ABOUT)
    {
      setDisplay(DISPLAY_ABOUT);
    }
	  else if (getMenuItemSelected() == MENU_EXIT)
    {
      System.exit(0);
    }
	  return getMenuItemSelected();
	}

	/**
	 * @return if the penguin is damaged
	 */
	public boolean penguinIsDamaged()
	{
		return penguinIsDamaged;
	}

	/**
	 * @param penguinIsDamaged if the penguin is damaged
	 */
	public void setPenguinIsDamaged(boolean penguinIsDamaged)
	{
		this.penguinIsDamaged = penguinIsDamaged;
	}

	/**
	 * @return if an enemy is killed
	 */
	public boolean enemyIsKilled()
	{
		return enemyIsKilled;
	}

	/**
	 * @param enemyIsKilled if an enemy is killed
	 */
	public void setEnemyIsKilled(boolean enemyIsKilled)
	{
		this.enemyIsKilled = enemyIsKilled;
	}

	/**
	 * @return if an item is collected
	 */
	public boolean itemIsCollected()
	{
		return itemIsCollected;
	}

	/**
	 * @param itemIsCollected if an item is collected
	 */
	public void setItemIsCollected(boolean itemIsCollected)
	{
		this.itemIsCollected = itemIsCollected;
	}

	/**
	 * @return if the penguin is powered up
	 */
	public boolean penguinIsPoweredUp()
	{
		return penguinIsPoweredUp;
	}

	/**
	 * @param penguinIsPoweredUp if the penguin is powered up
	 */
	public void setPenguinIsPoweredUp(boolean penguinIsPoweredUp)
	{
		this.penguinIsPoweredUp = penguinIsPoweredUp;
	}

	/**
	 * @return if the portal is fake
	 */
	public boolean portalIsFake()
	{
		return portalIsFake;
	}

	/**
	 * @param portalIsFake if the portal is fake
	 */
	public void setportalIsFake(boolean portalIsFake)
	{
		this.portalIsFake = portalIsFake;
	}

	/**
	 * @return the score
	 */
	public int getScore()
	{
		return score;
	}

	/**
	 * @param score the score
	 */
	public void setScore(int score)
	{
		this.score = score;
	}

	/**
	 * @return number of frames that have progressed since the start of the game
	 */
	public int getNumFrames()
	{
		return numFrames;
	}

	/**
	 * @param numFrames number of frames that have progressed since the start of the game
	 */
	public void setNumFrames(int numFrames)
	{
		this.numFrames = numFrames;
	}

	/**
	 * Make a penguin at the start position
	 */
	private void initializePenguin()
	{
		penguin = new Penguin(PENGUIN_START_X, PENGUIN_START_Y, Penguin.PENGUIN_WIDTH, Penguin.PENGUIN_HEIGHT, Penguin.PENGUIN_STAND_IMAGEPATH);
	}

	/**
	 * Make and place the items (cheesecakes, potatoes, strawberries, eggs) on platforms
	 */
	private void initializeItems()
	{
		items.clear();
		for(int i = 0;i <10; i++)
		{
			items.add(new PowerUpItem(0, 0, Item.ITEM_WIDTH, Item.ITEM_HEIGHT, "img/cheesecake.png"));
		}

		for(int i = 0;i <20; i++)
		{
			items.add(new PointItem(0, 0, Item.ITEM_WIDTH, Item.ITEM_HEIGHT, "img/potato.png", 100));
		}
		for(int i = 0;i <5; i++)
		{
			items.add(new PointItem(0, 0, Item.ITEM_WIDTH, Item.ITEM_HEIGHT, "img/strawberry.png", 150));
		}
		for(int i = 0;i <20; i++)
		{
			items.add(new PointItem(0, 0, Item.ITEM_WIDTH, Item.ITEM_HEIGHT, "img/egg.png", 50));
		}
		for(Item item : items)
		{
			item.setHorizontalVelocity(Item.ITEM_HORIZONTAL_VELOCITY);
			putObjectOnPlatform(item, platforms.get((int)(Math.random()*platforms.size())));
		}
	}

	/**
	 * Make the platforms defined for the level
	 */
	private void initializePlatforms()
	{
		platforms.clear();
		platforms = LevelMaker.getPlatforms();
	}

	/**
	 * Make the portals and set one as the winning portal
	 */
	private void initializePortals()
	{
		portals.clear();
		for(int i = 0; i <15; i++)
		{
			portals.add(new Portal(0, 0, Portal.PORTAL_WIDTH, Portal.PORTAL_HEIGHT, "img/portal.png"));
		}

		int correctPortalNumber = (int) (Math.random()*portals.size());
		for(int i = 0; i <portals.size(); i++)
		{
			Portal portal = portals.get(i);
			portal.setHorizontalVelocity(Portal.PORTAL_HORIZONTAL_VELOCITY);
			putObjectOnPlatform(portal, platforms.get((int)(Math.random()*platforms.size())));
			if(i == correctPortalNumber)
				portal.setIsCorrectPortal(true);

		}

	}

	/**
	 * Make the enemies (kirbehs, nians, dohmohs, pikatchus) and place them on platforms
	 */
	private void initializeEnemies()
	{
		enemies.clear();
		for(int i = 0;i <15; i++)
		{
			enemies.add(makeKirbehEnemy());
		}
		for(int i = 0;i <10; i++)
		{
			enemies.add(makePickatchuEnemy());
		}
		for(int i = 0;i <10; i++)
		{
			enemies.add(makeDohmohEnemy());
		}
		for(int i = 0;i <10; i++)
		{
			enemies.add(makeNianEnemy());
		}
	}

	/**
	 * Make a kirbeh enemy
	 * @return the kirbeh enemy that was made
	 */
	private Enemy makeKirbehEnemy()
	{
		WalkingEnemy kirbeh = new WalkingEnemy(0, 0, Enemy.ENEMY_WIDTH, Enemy.ENEMY_HEIGHT, "img/kirbeh.gif");
		kirbeh.setHorizontalVelocity(Enemy.ENEMY_HORIZONTAL_VELOCITY);
		putObjectOnPlatform(kirbeh, platforms.get((int)(Math.random()*platforms.size())));
		return kirbeh;
	}

	/**
	 * Make a pickatchu enemy
	 * @return the pickatchu enemy that was made
	 */
	private Enemy makePickatchuEnemy()
	
	{
		WalkingEnemy pikatchu = new WalkingEnemy(0, 0, Enemy.ENEMY_WIDTH, Enemy.ENEMY_HEIGHT, "img/pickatchu.gif");
		pikatchu.setHorizontalVelocity(Enemy.PICKATCHU_HORIZONTAL_VELOCITY);
		putObjectOnPlatform(pikatchu, platforms.get((int)(Math.random()*platforms.size())));
		return pikatchu;
	}
	
	/**
	 * Make a nian enemy
	 * @return the nian enemy that was made
	 */
	private Enemy makeNianEnemy()
	{
		FlyingEnemy nian = new FlyingEnemy(Math.random()*LARGEST_X_POINT+1, Math.random()*(LOWEST_Y_POINT - HIGHEST_Y_POINT) + HIGHEST_Y_POINT, Enemy.ENEMY_WIDTH, Enemy.ENEMY_HEIGHT, "img/nian.gif");
		nian.setHorizontalVelocity(Enemy.ENEMY_HORIZONTAL_VELOCITY);
		return nian;
	}

	/**
	 * Make a dohmoh enemy
	 * @return the dohmoh enemy that was made
	 */
	private Enemy makeDohmohEnemy()
	{
		WalkingEnemy dohmoh = new WalkingEnemy(0, 0, Enemy.DOHMOH_WIDTH, Enemy.DOHMOH_HEIGHT, "img/dohmoh.gif");
		dohmoh.setHorizontalVelocity(Enemy.ENEMY_HORIZONTAL_VELOCITY);
		putObjectOnPlatform(dohmoh, platforms.get((int)(Math.random()*platforms.size())));
		return dohmoh;
	}

	/**
	 * Places a PhysicalObject object on a platform
	 * @param object the object to be placed on the platform
	 * @param platform the platform that the object will be placed on 
	 */
	private void putObjectOnPlatform(PhysicalObject object, Platform platform)
	{
	  if (object instanceof WalkingEnemy)
	    ((WalkingEnemy)object).setPlatform(platform);
	  
	  if (object.getX() < platform.getX())
			object.setX(platform.getX() + Math.random()*platform.getWidth()+1);
		if (object.getX() + object.getWidth() > platform.getX() + platform.getWidth())
			object.setX(platform.getX()+platform.getWidth()-object.getWidth());
		object.setY(platform.getY()-object.getHeight());
	}

	/**
	 * Move the penguin right and changes its direction to right
	 */
	public void movePenguinRight()
	{
		if (penguin.getHorizontalVelocity() < penguin.getMaxSpeed())
		{
			penguin.accelerate(penguin.getRunAcceleration(), 0);
			penguin.setIsFacingRight(true);
		}
	}

	/**
	 * Move the penguin left and changes its direction to left
	 */
	public void movePenguinLeft()
	{
		if (penguin.getHorizontalVelocity() > -penguin.getMaxSpeed())
		{
			penguin.accelerate(-penguin.getRunAcceleration(), 0);
			penguin.setIsFacingRight(false);
		}
	}
	
	/**
	 * Attempts to make the penguin jump. If the penguin is not on a platform,
	 * it cannot jump.
	 * @return <code>true</code> if Penguin jumped, <code>false</code> otherwise
	 */
	public boolean penguinJump()
	{
		if (penguinIsOnPlatform()) //Penguins can only jump from platforms
		{
			penguin.accelerate(0, -penguin.getJumpSpeed());
			return true;
		}
		return false;
	}

	/**
	 * Ends the penguin's jump in midair, or does nothing if it is
	 * already falling
	 */
	public void penguinEndJump()
	{
		if (penguin.getVerticalVelocity() < 0)
		{
			penguin.accelerate(0, -penguin.getVerticalVelocity()*END_JUMP_DECCELERATION_FACTOR);
		}
	}

	/**
	 * Processes one frame's worth of activity for the penguin.
	 * Checks if the penguin is on a platform, and causes falling if it is not.
	 * Damages the penguin if it is out of bounds. Accelerates the penguin
	 * if falling. Checks collisions with items, enemies, and portals.
	 */
	public void penguinProcess()
	{
		if (penguinIsOnPlatform()) //If the penguin is standing on a platform
		{
		  penguin.setImagePath(Penguin.STANDING);
			penguin.setVerticalVelocity(0);
			if (penguin.getHorizontalVelocity() < 0)
				penguin.accelerate(penguin.getSkidDecceleration(), 0);
			else if (penguin.getHorizontalVelocity() > 0)
				penguin.accelerate(-penguin.getSkidDecceleration(), 0);
		}
		else
		{
		  penguin.setImagePath(Penguin.JUMPING);
		}
		if (penguin.getY() > LOWEST_Y_POINT)
		{
			penguin.damage();
			penguinIsDamaged = true;
			//penguin.setVerticalVelocity(-penguin.getJumpSpeed()*PIT_ESCAPE_MULTIPLIER);
			penguin.moveTo(PENGUIN_START_X, PENGUIN_START_Y);
			penguin.setVelocity(0, 0);
			if (penguin.isDead())
				gameOver = true;
		}
		else if (penguin.getVerticalVelocity() < penguin.getTerminalVelocity()) //if the penguin is falling at less than terminal velocity
			penguin.accelerate(0, GRAVITY_ACCELERATION);
		itemCheck();
		enemyCheck();
		portalCheck();
	}

	/**
	 * Causing the penguin to bounce off of an enemy that it just killed.
	 */
	public void penguinEnemyJump()
	{
		penguin.setVerticalVelocity(-penguin.getEnemyJumpSpeed());
	}

	/**
	 * Checks what enemies the penguin killed or was damaged by
	 * and takes appropriate action for each situation, either
	 * removing the enemy from the field or causing damage and knockback
	 * to the penguin.
	 */
	public void enemyCheck()
	{
		for (int i = 0; i < enemies.size(); i ++)
		{
			Enemy enemy = enemies.get(i);
			if (enemy.getVulnerableArea().intersects(this.getPenguinFeetCenterRectangle()))
			{
				enemyIsKilled = true;
				enemies.remove(enemy);
				i --;
				penguinEnemyJump();
				score += 100;
			}
			else if (enemy.getDeadlyArea().intersects(penguin.getRect()))
			{
				penguin.damage();
				score -= 50;
				if(enemy.getHorizontalVelocity() > 0){
					penguin.moveTo(enemy.getX()-penguin.getWidth()-enemy.getHorizontalVelocity(), enemy.getY()-enemy.getHeight());
				}
				else
				{
					penguin.moveTo(enemy.getX()+enemy.getWidth()+penguin.getWidth()+enemy.getHorizontalVelocity(), enemy.getY()-enemy.getHeight());
				}
				if (penguin.isDead())
				{
					gameOver = true;
				}
				else
				{
					penguinIsDamaged = true;
				}
			}
		}
	}

	/**
	 * Checks what items the penguin picked up and
	 * activates them.
	 */
	public void itemCheck()
	{
		for (int i = 0; i < items.size(); i ++)
		{
			Item item = items.get(i);
			if (item.getRect().intersects(penguin.getRect()))
			{
				if(item instanceof PowerUpItem)
				{
					if(!penguin.isBunny())
						penguinIsPoweredUp = true;
				}
				else
					itemIsCollected = true;
				item.activate(penguin);
				score += item.getPointValue();
				items.remove(item);
				i--;
			}
		}
	}

	/**
	 * Checks if the penguin intersects with the winning portal,
	 * and ends the game if it does, or raises a flag to display
	 * a message if the penguin intersects with a wrong portal.
	 */
	public void portalCheck()
	{
		portalIsFake = false;
		for (int i = 0; i < portals.size(); i++)
		{
			Portal portal = portals.get(i);
			if (portal.getRect().intersects(penguin.getRect()))
			{
				if(portal.getIsCorrectPortal())
				{
					score += 500;
					winGame = true;
				}
				else
				{
					portalIsFake = true;
				}
			}
		}
	}

	/**
	 * Gets all of the <code>PhysicalObject</code>s in this Lolwut game
	 * @return A <code>List</code> of all <code>PhysicalObject</code>s
	 */
	public List<PhysicalObject> allPhysicalObjects()
	{
		List<PhysicalObject> list = new ArrayList<PhysicalObject>();

		list.addAll(platforms);
		list.addAll(portals);
		list.addAll(items);
		list.addAll(enemies);
		list.add(penguin);
		return list;
	}

	/**
	 * Advances the game by one frame, by moving all objects according to their velocity
	 * and determining what effects happen from collisions.
	 */
	public void advanceFrame()
	{
		this.penguinProcess();
		List<PhysicalObject> objects = this.allPhysicalObjects();
		for (PhysicalObject object : objects)
		{
			if(object instanceof FlyingEnemy)
			{
				if(!new Rectangle2D.Double(SMALLEST_X_POINT, HIGHEST_Y_POINT, LARGEST_X_POINT-SMALLEST_X_POINT, LOWEST_Y_POINT-HIGHEST_Y_POINT).contains(object.getRect())){
					object.setHorizontalVelocity(-object.getHorizontalVelocity());
				}
			}
			object.move();
		}
		numFrames ++;
		if (numFrames % SCORE_DECAY_RATE == 0)
      score --;
	}

	/**
	 * Gets whether the penguin is on a platform.
	 * @return <code>true</code> if the penguin is on a platform, <code>false</code> otherwise
	 */
	public boolean penguinIsOnPlatform()
	{
		if (penguin.getVerticalVelocity() < 0) //if going up, not on platform
			return false;

		boolean leftSupported = false;
		boolean centerSupported = false;
		boolean rightSupported = false;
		
		for (Platform platform : platforms)
		{
			if (penguinCenterIsOnPlatform(platform))
			{
				centerSupported = true;
				platformAdjust(platform);
			}
		}

		return (leftSupported && rightSupported) || centerSupported;
	}

	private void platformAdjust(Platform platform)
	{
		if (penguin.getY() + penguin.getHeight() > platform.getY())
			penguin.moveTo(penguin.getX(), penguin.getY()-PLATFORM_ADJUST_RATE);
		else if (penguin.getY() + penguin.getHeight() < platform.getY() && penguin.getVerticalVelocity() > 0)
			penguin.moveTo(penguin.getX(), platform.getY()-penguin.getHeight());
	}

	private boolean penguinCenterIsOnPlatform(Platform platform)
	{
		//The supportable area is the area that the platform will support the penguin in, above the platform with a depth of up to the penguin's downward velocity
		Rectangle2D.Double platformSupportArea = getPlatformSupportArea(platform);
		Rectangle2D.Double penguinFeetCenter = getPenguinFeetCenterRectangle();
		//Line2D.Double penguinFeetCenterVector = getPenguinFeetCenterVector();
		return (platformSupportArea.intersects(penguinFeetCenter));
	}

	private Rectangle2D.Double getPlatformSupportArea(Platform platform)
	{
		return new Rectangle2D.Double(platform.getX(), platform.getY()-1, platform.getWidth(), Math.max(PLATFORM_DETECTION_BUFFER, penguin.getVerticalVelocity()));
	}

	private Rectangle2D.Double getPenguinFeetCenterRectangle()
	{
		return new Rectangle2D.Double(penguin.getX()+penguin.getWidth()/4, penguin.getY()+penguin.getHeight()-penguin.getVerticalVelocity()/2-1, penguin.getWidth()/2, penguin.getVerticalVelocity()+2);
	}

	/**
	 * Gets the penguin (player) in this game
	 * @return the penguin (player)
	 */
	public Penguin getPenguin()
	{
		return penguin;
	}
	/**
	 * Gets the list of items
	 * @return the items in this game
	 */
	public List<Item> getItems()
	{
		return items;
	}
	/**
	 * Sets the list of items
	 * @param items the items to set
	 */
	public void setItems(List<Item> items)
	{
		this.items = items;
	}

	/**
	 * Gets the list of enemies.
	 * @return the enemies in this game
	 */
	public List<Enemy> getEnemies()
	{
		return enemies;
	}
	/**
	 * Sets the list of enemies
	 * @param enemies the enemies to set
	 */
	public void setEnemies(List<Enemy> enemies)
	{
		this.enemies = enemies;
	}

	/**
	 * Gets whether the game is stopped.
	 * @return <code>true</code> if the game is not running, <code>false</code> otherwise
	 */
	public boolean isNotActive()
	{
		return isPaused || display != DISPLAY_GAME; //|| displayMainMenu || gameOver || winGame || displayInstructions || displayAbout;
	}
	
	/**
	 * Gets whether the game is paused
	 * @return <code>true</code> if the game is paused, <code>false</code> otherwise
	 */
	public boolean isPaused()
	{
	  return isPaused;
	}

	/**
	 * Sets whether the game is paused
	 * @param isPaused the pausation to set
	 */
	public void setPaused(boolean isPaused)
	{
		this.isPaused = isPaused;
		if (isPaused)
		{
		  setDisplay(DISPLAY_PAUSE_MENU);
		}
		else
		{
		  setDisplay(DISPLAY_GAME);
		}
	}

	/**
	 * Pauses the game.
	 */
	public void pause()
	{
		setPaused(true);
	}

	/**
	 * Unpauses the game.
	 */
	public void unpause()
	{
		setPaused(false);
	}

	/**
	 * Gets whether the game is over
	 * @return <code>true</code> if the game is over, <code>false</code> otherwise
	 */
	public boolean gameOver()
	{
		return gameOver;
	}

	/**
	 * Gets whether the game was won
	 * @return <code>true</code> if the game was won, <code>false,</code> otherwise
	 */
	public boolean winGame(){
		return winGame;
	}
}
