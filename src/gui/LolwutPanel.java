package gui;

import giantmetalbowserPlayerInput.PlayerInputKeyListener;
import giantmetalbowserSound.Sound;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import lolwut.LolwutBrain;
import lolwut.Penguin;
import lolwut.PhysicalObject;
import lolwut.ScoreIOManager;

/**
 * The panel for displaying the Lolwut game. Simply construct a
 * <code>LolwutPanel</code> and add it to a frame.
 * @author Anran Li and Daniel Wong
 */
public class LolwutPanel extends JPanel
  implements ActionListener
{
  private static final long serialVersionUID = -8213301432309174883L;
  
  private static final int MILLIS_PER_FRAME = 33;
	private static final int MESSAGE_POPUP_DURATION = 3000;
	private static final int DEAD_ZONE_X = 385;
	private static final int DEAD_ZONE_WIDTH = 30;
	private static final int DEAD_ZONE_Y = 285;
	private static final int DEAD_ZONE_HEIGHT = 30;

	private LolwutBrain lolwut;
	private PlayerInputKeyListener pikl;
	private boolean leftPressed, rightPressed, upPressed, downPressed;
	private Sound backgroundMusic, jumpSound, killEnemySound, getDamagedSound, winGameSound, loseGameSound, collectItemSound, powerUpSound;
	private long itemMessagePopupTime, enemyMessagePopupTime;
	private Rectangle2D.Double deadZone;
	private ArrayList<Sound> soundList;
	private ScoreIOManager scoreIO;
	private boolean scoreAlreadySaved;

	/**
	 * Constructs a new <code>LolwutPanel</code>.
	 */
	public LolwutPanel()
	{
		lolwut = new LolwutBrain();
		scoreIO = new ScoreIOManager();
		pikl = generatePIKL();
		this.addKeyListener(pikl);
		this.requestFocusInWindow();
		new Timer(MILLIS_PER_FRAME, this).start();

		soundList = new ArrayList<Sound>();
		backgroundMusic = new Sound("sound/bg1.mp3");
		jumpSound = new Sound("sound/jump.mp3");
		killEnemySound = new Sound("sound/kill.mp3");
		getDamagedSound = new Sound("sound/damaged.mp3");
		collectItemSound = new Sound("sound/item.mp3");
		powerUpSound = new Sound("sound/powerup.mp3");
		winGameSound = new Sound("sound/win.mp3");
		loseGameSound = new Sound("sound/lose.mp3");
		soundList.add(jumpSound);
		soundList.add(killEnemySound);
		soundList.add(getDamagedSound);
		soundList.add(powerUpSound);
		soundList.add(collectItemSound);
		backgroundMusic.setRepeating(true);
		resetGame();
		lolwut.setDisplay(LolwutBrain.DISPLAY_MAIN_MENU);
	}

	private void resetGame()
	{
		lolwut.resetGame();
		stopSounds();
		backgroundMusic.stop();
		backgroundMusic.play();
		itemMessagePopupTime = 0;
		enemyMessagePopupTime = 0;
		scoreAlreadySaved = false;
		leftPressed = false;
		rightPressed = false;
		upPressed = false;
		downPressed = false;
		deadZone = generateDeadZone();
		repaint();
	}

	private Rectangle2D.Double generateDeadZone()
	{
		return new Rectangle2D.Double(DEAD_ZONE_X, DEAD_ZONE_Y, DEAD_ZONE_WIDTH, DEAD_ZONE_HEIGHT);
	}

	private PlayerInputKeyListener generatePIKL()
	{
		PlayerInputKeyListener pikl = new PlayerInputKeyListener();
		pikl.addAction(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), new LeftPressedAction());
		pikl.addAction(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), new LeftReleasedAction());
		pikl.addAction(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), new RightPressedAction());
		pikl.addAction(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), new RightReleasedAction());
		pikl.addAction(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), new UpPressedAction());
		pikl.addAction(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), new UpReleasedAction());
		pikl.addAction(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), new DownReleasedAction());
		pikl.addAction(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, true), new PauseAction());
		pikl.addAction(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), new EnterAction());
		return pikl;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * Draws this Lolwut game.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		if (lolwut.display() == LolwutBrain.DISPLAY_INSTRUCTIONS)
    {
      drawInstructions(g2);
      return;
    }
		else if (lolwut.display() == LolwutBrain.DISPLAY_CONTROLS)
    {
      drawControls(g2);
      return;
    }
    else if (lolwut.display() == LolwutBrain.DISPLAY_ABOUT)
    {
      drawAbout(g2);
      return;
    }
    else if (lolwut.display() == LolwutBrain.DISPLAY_HIGH_SCORES)
    {
      this.drawHighScores(g2);
      return;
    }
		else if (lolwut.display() == LolwutBrain.DISPLAY_MAIN_MENU)
		{
			this.drawMainMenu(g2);
			return;
		}
		else if (lolwut.gameOver())
		{
			this.drawGameOver(g2);
			drawScore(g2);
      drawTime(g2);
			this.saveScore();
			return;
		}
		else if (lolwut.winGame())
		{
			this.drawWinGame(g2);
			drawScore(g2);
      drawTime(g2);
			this.saveScore();
			return;
		}
		else if (lolwut.isPaused()) //Draw paused
		{
			drawPauseMenu(g2);
			drawScore(g2);
      drawTime(g2);
			return;
		}

		drawBackground(g2);

		g2.translate((int)(-deadZone.getX()+DEAD_ZONE_X), (int)(-deadZone.getY()+DEAD_ZONE_Y));
		drawObjects(g2);

		g2.translate(-(int)(-deadZone.getX()+DEAD_ZONE_X), -(int)(-deadZone.getY()+DEAD_ZONE_Y));
		drawScore(g2);
		drawTime(g2);
		
		if (lolwut.getItems().size() == 0 && (itemMessagePopupTime <= 0 || System.currentTimeMillis() <= itemMessagePopupTime + MESSAGE_POPUP_DURATION))
    {
      if (itemMessagePopupTime <= 0)
        itemMessagePopupTime = System.currentTimeMillis();
      drawNoItemsNotification(g2);
    }

    if (lolwut.getEnemies().size() == 0 && (enemyMessagePopupTime <= 0 || System.currentTimeMillis() <= enemyMessagePopupTime + MESSAGE_POPUP_DURATION))
    {
      if (enemyMessagePopupTime <= 0)
        enemyMessagePopupTime = System.currentTimeMillis();
      drawNoEnemiesNotification(g2);
    }

    if (lolwut.portalIsFake())
    {
      drawFakePortalNotification(g2);
    }

    if (!lolwut.getPenguin().getRect().intersects(deadZone))
    {
      double x = deadZone.getX();
      double y = deadZone.getY();
      if (lolwut.getPenguin().getX() > x + deadZone.getWidth())
      {
        x = lolwut.getPenguin().getX() - deadZone.getWidth();
      }
      else if (lolwut.getPenguin().getX()+lolwut.getPenguin().getWidth() < x)
      {
        x = lolwut.getPenguin().getX()+lolwut.getPenguin().getWidth();
      }
      if (lolwut.getPenguin().getY() > y + deadZone.getHeight())
      {
        y = lolwut.getPenguin().getY() - deadZone.getHeight();
      }
      else if (lolwut.getPenguin().getY()+lolwut.getPenguin().getHeight() < y)
      {
        y = lolwut.getPenguin().getY()+lolwut.getPenguin().getHeight();
      }
      deadZone.setRect(x, y, deadZone.getWidth(), deadZone.getHeight());
    }
	}

	private void drawBackground(Graphics2D g2)
	{
		Image bg = (new ImageIcon("img/stars.jpg")).getImage();
		if(bg != null)
		{
			g2.drawImage(bg, 
					0,0,(int)getBounds().getWidth(), (int)getBounds().getHeight(),
					0,0, (int)bg.getWidth(null), (int)bg.getHeight(null),
					null);
		}
	}

	private void drawObjects(Graphics2D g2)
	{
		List<PhysicalObject> objects = lolwut.allPhysicalObjects();
		for (PhysicalObject object : objects)
		{
			Image img = new ImageIcon(object.getImagePath()).getImage();

			if (img.getWidth(null) < 0 || img.getHeight(null) < 0)
				g2.fillRect((int)object.getX(), (int)object.getY(), (int)object.getWidth(), (int)object.getHeight());

			if(object instanceof Penguin){
			  img = new ImageIcon(lolwut.getPenguin().getImagePath()).getImage();

				if(!((Penguin) object).isFacingRight()){ //left = original
					g2.drawImage (img,
							(int)object.getX(), (int)object.getY(), (int)object.getX()+(int)object.getWidth(), (int)object.getY()+(int)object.getHeight(),
							0,0, (int)img.getWidth(null), (int)img.getHeight(null),
							null);
				}
				else{ //right = inverse
					g2.drawImage (img,
							(int)object.getX()+(int)object.getWidth(), (int)object.getY(), (int)object.getX(), (int)object.getY()+(int)object.getHeight(),
							0,0, (int)img.getWidth(null), (int)img.getHeight(null),
							null);
				}
			}
			else{

				if(object.getHorizontalVelocity() <= 0){ //left = original
					g2.drawImage (img,
							(int)object.getX(), (int)object.getY(), (int)object.getX()+(int)object.getWidth(), (int)object.getY()+(int)object.getHeight(),
							0,0,(int)img.getWidth(null), (int)img.getHeight(null),
							null);
				}
				else{ //right = inverse
					g2.drawImage (img,
							(int)object.getX()+(int)object.getWidth(), (int)object.getY(), (int)object.getX(), (int)object.getY()+(int)object.getHeight(),
							0,0, (int)img.getWidth(null), (int)img.getHeight(null),
							null);
				}
			}
		}
	}

	private void drawScore(Graphics2D g2){
		final int height = 20;
		String str = "Score: " + lolwut.getScore();
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height));
		g2.setColor(java.awt.Color.WHITE);
		g2.drawString(str, getWidth()/16, height);
	}

	private void drawTime(Graphics2D g2){
		int seconds = (lolwut.getNumFrames()*MILLIS_PER_FRAME)/1000;
		int minutes = 0;
		int hours = 0;
		if(seconds >= 360){
			hours = seconds / 360;
			seconds = seconds % 360;
		}
		if(seconds >= 60){
			minutes = seconds / 60;
			seconds = seconds % 60;
		}
		String h = ""+hours;
		String m = ""+minutes;
		String s = ""+seconds;
		if(hours < 10)
		{
			h = "0"+hours;
		}
		if(minutes < 10)
		{
			m = "0"+minutes;
		}
		if(seconds < 10)
		{
			s = "0"+seconds;
		}

		final int height = 20;
		String str = "Time: " + h +":"+ m +":"+ s;
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height));
		g2.setColor(java.awt.Color.WHITE);
		g2.drawString(str, getWidth()/16, height*2);
	}

	private void drawNoItemsNotification(Graphics2D g2)
	{
		if (enemyMessagePopupTime > 0)
			enemyMessagePopupTime = Long.MAX_VALUE;
		final int height = 20;
		String pause = "You have collected all the items!";
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height));
		g2.setColor(java.awt.Color.GREEN);
		int strWidth = g2.getFontMetrics().stringWidth(pause);
		g2.drawString(pause, (this.getWidth()-strWidth)/2, (this.getHeight()+height)/2);
	}

	private void drawNoEnemiesNotification(Graphics2D g2)
	{
		if (itemMessagePopupTime > 0)
			itemMessagePopupTime = Long.MAX_VALUE;
		final int height = 20;
		String pause = "You have killed all the enemies!";
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height));
		g2.setColor(java.awt.Color.GREEN);
		int strWidth = g2.getFontMetrics().stringWidth(pause);
		g2.drawString(pause, (this.getWidth()-strWidth)/2, (this.getHeight()+height)/2);
	}

	private void drawFakePortalNotification(Graphics2D g2)
	{
		final int height = 20;
		String pause = "This portal is fake! Find the real one!";
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height));
		g2.setColor(java.awt.Color.RED);
		int strWidth = g2.getFontMetrics().stringWidth(pause);
		g2.drawString(pause, (this.getWidth()-strWidth)/2, (this.getHeight())/2);
	}

	private void drawMenu(Graphics2D g2)
	{
		String menuImagePath = "img/menu.png";
		Image img = new ImageIcon(menuImagePath).getImage();
		g2.drawImage (img,
				0, 0, (int)getBounds().getWidth(), (int)getBounds().getHeight(),
				0,0,(int)img.getWidth(null), (int)img.getHeight(null),
				null);

		final int height = 20;
		String newGame = "new game";
		String instructions = "instructions";
		String controls = "controls";
		String highScores = "high scores";
		String about = "about";
		String quit = "quit";
		ArrayList<String> menuItems = new ArrayList<String>();
		menuItems.add(newGame);
		menuItems.add(instructions);
		menuItems.add(controls);
		menuItems.add(highScores);
		menuItems.add(about);
		menuItems.add(quit);

		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height));
		g2.setColor(java.awt.Color.WHITE);
		for(int i = 0; i < menuItems.size(); i++)
		{
			if(lolwut.getMenuItemSelected() == i)
			{
				g2.setColor(java.awt.Color.YELLOW);
			}
			int strWidth = g2.getFontMetrics().stringWidth(menuItems.get(i));
			g2.drawString(menuItems.get(i), (this.getWidth()-strWidth)/2, (this.getHeight()+height)/2+20*i);

			if(lolwut.getMenuItemSelected() == i)
			{
				g2.setColor(java.awt.Color.WHITE);
			}
		}
	}

	private void drawMainMenu(Graphics2D g2)
	{
		drawMenu(g2);
		final int height = 40;
		String pause = "MAIN MENU";
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height));
		g2.setColor(java.awt.Color.GRAY);
		int strWidth = g2.getFontMetrics().stringWidth(pause);
		g2.drawString(pause, (this.getWidth()-strWidth)/2, (this.getHeight()+height)/2-40);
	}

	private void drawPauseMenu(Graphics2D g2)
	{
		drawMenu(g2);
		final int height = 40;
		String pause = "PAUSED";
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height));
		g2.setColor(java.awt.Color.GRAY);
		int strWidth = g2.getFontMetrics().stringWidth(pause);
		g2.drawString(pause, (this.getWidth()-strWidth)/2, (this.getHeight()+height)/2-40);
	}

	private void drawGameOver(Graphics2D g2)
	{
		drawMenu(g2);
		final int height = 40;
		String str = "GAME OVER";
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height));
		g2.setColor(java.awt.Color.RED);
		int strWidth = g2.getFontMetrics().stringWidth(str);
		g2.drawString(str, (this.getWidth()-strWidth)/2, (this.getHeight()+height)/2-40);
	}

	private void drawWinGame(Graphics2D g2)
	{
		drawMenu(g2);
		final int height = 40;
		String str = "CONGRATULATIONS! YOU WON!";
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height));
		g2.setColor(java.awt.Color.GREEN);
		int strWidth = g2.getFontMetrics().stringWidth(str);
		g2.drawString(str, (this.getWidth()-strWidth)/2, (this.getHeight()+height)/2-40);
	}

	private void drawInstructions(Graphics2D g2)
	{
		String menuImagePath = "img/menu.png";
		Image img = new ImageIcon(menuImagePath).getImage();
		g2.drawImage (img,
				0, 0, (int)getBounds().getWidth(), (int)getBounds().getHeight(),
				0,0,(int)img.getWidth(null), (int)img.getHeight(null),
				null);

		final int height = 20;
		ArrayList<String> instructions = new ArrayList<String>();

		instructions.add("Peter the Penguin is lost in"); 
		instructions.add("sppppaaaaaccccceeeeeee. Help him find the");
		instructions.add("correct portal among the many fake portals");
		instructions.add("to get back home.");
    instructions.add("Avoid or kill enemies such as kibehs,");
    instructions.add("pickatchus, dohmohs, and nians. Collect");
    instructions.add("items such as potatoes, strawberries, and");
		instructions.add("eggs for points. Eat cheesecakes to");
    instructions.add("transform into a bunny for faster travel");
    instructions.add("and an additional life.");
    instructions.add("The score decreases as time increases so");
    instructions.add("try to beat the game as fast as you can.");
    instructions.add("Good luck!");
		instructions.add("");
		instructions.add("See \"controls\" for game controls.");
		instructions.add("");
    instructions.add("Press enter to go back to the");
    instructions.add("previous screen.");


		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height));
		g2.setColor(java.awt.Color.WHITE);

		for(int i = 0; i < instructions.size(); i++)
		{
			int strWidth = g2.getFontMetrics().stringWidth(instructions.get(i));
			g2.drawString(instructions.get(i), (this.getWidth()-strWidth)/2, this.getHeight()/4+(i*20));
		}
	}
	
	private void drawControls(Graphics2D g2)
  {
    String menuImagePath = "img/menu.png";
    Image img = new ImageIcon(menuImagePath).getImage();
    g2.drawImage (img,
        0, 0, (int)getBounds().getWidth(), (int)getBounds().getHeight(),
        0,0,(int)img.getWidth(null), (int)img.getHeight(null),
        null);

    final int height = 20;
    ArrayList<String> controls = new ArrayList<String>();

    controls.add("Navigate the menu using \"up\" and \"down\".");
    controls.add("Press \"enter\" to select the item. If you"); 
    controls.add("are on a screen other than the menu or game");
    controls.add("screen, press \"enter\" to go back to the menu.");
    controls.add("");
    controls.add("On the game screen, press \"P\" to pause and");
    controls.add("unpause the game. To move the penguin, use");
    controls.add("\"left\" for left, \"right\" for right, and \"up\"");
    controls.add("for jump.");
    controls.add("");
    controls.add("See \"instructions\" for game instructions.");
    controls.add("");
    controls.add("Press enter to go back to the");
    controls.add("previous screen.");


    g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height));
    g2.setColor(java.awt.Color.WHITE);

    for(int i = 0; i < controls.size(); i++)
    {
      int strWidth = g2.getFontMetrics().stringWidth(controls.get(i));
      g2.drawString(controls.get(i), (this.getWidth()-strWidth)/2, this.getHeight()/4+(i*20));
    }
  }

	private void drawAbout(Graphics2D g2)
	{
		String menuImagePath = "img/menu.png";
		Image img = new ImageIcon(menuImagePath).getImage();
		g2.drawImage (img,
				0, 0, (int)getBounds().getWidth(), (int)getBounds().getHeight(),
				0,0,(int)img.getWidth(null), (int)img.getHeight(null),
				null);

		final int height = 20;
		ArrayList<String> about = new ArrayList<String>();

		about.add("This game, LOLWUT, was created");
		about.add("by Anran Li and Daniel Wong in");
		about.add(" 2011. Do not redistribute");
		about.add("without permission. The graphics");
		about.add("are their original work but may");
		about.add("be based on certain characters.");
		about.add("Music and sounds were obtained");
		about.add("from Mike Koenig, thecheeseman,");
		about.add("Popup Pixels, and various other");
		about.add("sources on soundbible.com and");
		about.add("GrayZ on newgrounds.com.");
		about.add("");
    about.add("Press enter to go back to the");
    about.add("previous screen.");

		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height));
		g2.setColor(java.awt.Color.WHITE);

		for(int i = 0; i < about.size(); i++)
		{
			int strWidth = g2.getFontMetrics().stringWidth(about.get(i));
			g2.drawString(about.get(i), (this.getWidth()-strWidth)/2, this.getHeight()/4+(i*20));
		}
	}
	
	private void drawHighScores(Graphics2D g2)
	{
	  String menuImagePath = "img/menu.png";
    Image img = new ImageIcon(menuImagePath).getImage();
    g2.drawImage (img,
        0, 0, (int)getBounds().getWidth(), (int)getBounds().getHeight(),
        0,0,(int)img.getWidth(null), (int)img.getHeight(null),
        null);

    final int height = 20;
    ArrayList<String> instructions = new ArrayList<String>();

    instructions.add("  d(^o^)b   High Scores   d(^o^)b");
    instructions.add("");
    int[] scores = scoreIO.getScores();
    String[] names = scoreIO.getNames();
    for (int i = 0; i < scores.length && i < names.length; i ++)
    {
      instructions.add((i+1)+" :  "+scores[i]+" : "+names[i]+"");
    }
    instructions.add("");
    instructions.add("Press enter to go back to the");
    instructions.add("previous screen.");


    g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, height));
    g2.setColor(java.awt.Color.WHITE);

    for(int i = 0; i < instructions.size(); i++)
    {
      int strWidth = g2.getFontMetrics().stringWidth(instructions.get(i));
      g2.drawString(instructions.get(i), (this.getWidth()-strWidth)/2, this.getHeight()/4+(i*20));
    }
	}
	
	private void saveScore()
	{
	  if (scoreAlreadySaved)
	  {
	    return;
	  }
	  scoreAlreadySaved = true;
	  int score = lolwut.getScore();
	  
	  if (!scoreIO.isHighScore(score))
	  {
	    return;
	  }
	  String name = JOptionPane.showInputDialog(this, "Congrats on your score of "+score+"! Please enter your name.");
	  if (name != null && !scoreIO.writeScore(name, score))
	  {
	      JOptionPane.showMessageDialog(this, "Oops! There was a problem saving your score, "+name+". Sorry!", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
	  }
	}
	
	/**
	 * Stops all sounds from playing.
	 */
	public void stopSounds()
	{
		killEnemySound.stop();
		winGameSound.stop();
		loseGameSound.stop();
		jumpSound.stop();
	}

	/**
	 * Stops all sounds from playing, except the given sound.
	 * @param sound The sound to keep playing
	 */
	public void stopSounds(Sound sound)
	{
		for(Sound s : soundList){
			if(!s.equals(sound)){
				sound.stop();
			}
		}
	}

	/**
	 * Plays the appropriate sounds for the current frame.
	 */
	public void playSounds()
	{
		if(lolwut.winGame())
		{
			stopSounds();
			winGameSound.play();
			lolwut.setPaused(true);
		}
		else if(lolwut.gameOver())
		{
			stopSounds();
			loseGameSound.play();
			lolwut.setPaused(true);
		}

		if(lolwut.enemyIsKilled())
		{
			killEnemySound.play();
			lolwut.setEnemyIsKilled(false);
		}
		if(lolwut.penguinIsDamaged())
		{
			getDamagedSound.play();
			lolwut.setPenguinIsDamaged(false);
		}
		if(lolwut.itemIsCollected())
		{
			collectItemSound.play();
			lolwut.setItemIsCollected(false);
		}
		if(lolwut.penguinIsPoweredUp())
		{
			powerUpSound.play();
			lolwut.setPenguinIsPoweredUp(false);
		}

	}

	/**
	 * The Action for moving left when the left arrow key is pressed.
	 * @author Daniel Wong
	 */
	private class LeftPressedAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			leftPressed = true;
		}
	}
	/**
   * The Action for stopping moving left when the left arrow key is released.
   * @author Daniel Wong
   */
	private class LeftReleasedAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			leftPressed = false;
		}
	}
	/**
   * The Action for moving right when the right arrow key is pressed.
   * @author Daniel Wong
   */
	private class RightPressedAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			rightPressed = true;
		}
	}
	/**
   * The Action for stopping moving right when the right arrow key is released.
   * @author Daniel Wong
   */
	private class RightReleasedAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			rightPressed = false;
		}
	}
	/**
   * The Action for jumping when the up arrow key is pressed.
   * @author Daniel Wong
   */
	private class UpPressedAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			upPressed = true;
		}
	}
	/**
   * The Action for stopping the jump when the up arrow key is released.
   * @author Daniel Wong
   */
	private class UpReleasedAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			if(lolwut.displayingMenu())//lolwut.display() == LolwutBrain.DISPLAY_MAIN_MENU || lolwut.isNotActive() || lolwut.gameOver() || lolwut.winGame())
			{
				if(lolwut.getMenuItemSelected() == 0)
					lolwut.setMenuItemSelected(5);
				else
					lolwut.setMenuItemSelected(lolwut.getMenuItemSelected()-1);
			}
			upPressed = false;
		}
	}
	/**
   * The Action for scrolling through menus when the down arrow key is pressed.
   * @author Anran Li
   */
	private class DownReleasedAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			if(lolwut.displayingMenu())//lolwut.display() == LolwutBrain.DISPLAY_MAIN_MENU || lolwut.isNotActive() || lolwut.gameOver() || lolwut.winGame())
			{
				if(lolwut.getMenuItemSelected() == 5)
					lolwut.setMenuItemSelected(0);
				else
					lolwut.setMenuItemSelected(lolwut.getMenuItemSelected()+1);
			}
			downPressed = false;
		}
	}
	/**
   * The Action for pausing the game when the P key is pressed.
   * @author Daniel Wong
   */
	private class PauseAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
		  if (!lolwut.gameOver() && !lolwut.winGame())
		    lolwut.setPaused(!lolwut.isNotActive());
		}
	}
	private class EnterAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
		  int action = lolwut.doMenuAction();
		  if (action == LolwutBrain.MENU_NEW_GAME)
		    resetGame();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * Advances the frame when the timer fires.
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (!lolwut.isNotActive())// && !lolwut.gameOver() && !lolwut.winGame() && !lolwut.displayMainMenu() && !lolwut.displayInstructions() && !lolwut.displayAbout())
		{
			playSounds();
			lolwut.advanceFrame();
			this.doUserInputActions();
		}
		this.repaint();
		if (this.isShowing() && !this.isFocusOwner())// && this.getParent() != null && (this.getParent() instanceof Frame) && ((Frame)(this.getParent())).isActive())
			this.requestFocus();
	}

	private void doUserInputActions()
	{
		if (leftPressed)
		{
			lolwut.movePenguinLeft();
		}
		if (rightPressed)
		{
			lolwut.movePenguinRight();
		}
		if (upPressed)
		{
			if (lolwut.penguinJump())
				jumpSound.play();
		}
		else //up not pressed
		{
			lolwut.penguinEndJump();
		}
	}
}