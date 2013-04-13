import gui.LolwutPanel;

import javax.swing.JFrame;
import javax.swing.UIManager;


/**
 * The driver class for the Lolwut game
 * @author Anran Li
 */
public class Lolwut
{
  /**
   * Executes the game. Also, the game.
   * @param args Does nothing
   */
  public static void main(String[] args)
  {
    try
    {
      // Set cross-platform Java L&F (also called "Metal")
      UIManager.setLookAndFeel(
          UIManager.getSystemLookAndFeelClassName());
    } 
    catch (Exception e)
    {}
    JFrame frame = new JFrame("Lolwut");
    frame.add(new LolwutPanel());
    frame.setBounds(0, 0, 800, 600);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
