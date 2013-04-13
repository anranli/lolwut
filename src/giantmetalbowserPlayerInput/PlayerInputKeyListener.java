package giantmetalbowserPlayerInput;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * This class listens to <code>KeyEvent</code>s and turns the input into
 * <code>ActionEvent</code>s with command strings according to the
 * <code>KeyActionSettings</code> map. In this way, any number
 * of keys can be mapped to an <code>Action</code>.<br/>
 * To properly use this class, you need to make several <code>Action</code>
 * classes to use in this class' map. For example:
 * <blockquote><code>private class MoveLeftAction extends AbstractAction {<br/>
 * &nbsp;&nbsp;public void actionPerformed(ActionEvent e) {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;//Do some action when left is pressed<br/>
 * &nbsp;&nbsp;}<br/>
 * }<br/>
 * private class StopMovingLeftAction extends AbstractAction {<br/>
 * &nbsp;&nbsp;public void actionPerformed(ActionEvent e) {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;//Do some action when left is released<br/>
 * &nbsp;&nbsp;}<br/>
 * }<br/>
 * private PlayerInputKeyListener generatePlayerInputKeyListener() {<br/>
 * &nbsp;&nbsp;PlayerInputKeyListener pikl = new PlayerInputKeyListener();<br/>
 * &nbsp;&nbsp;pikl.addAction(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), new MoveLeftAction());<br/>
 * &nbsp;&nbsp;pikl.addAction(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), new StopMovingLeftAction());<br/>
 * }
 * </code></blockquote>
 * @author giantmetalbowser
 */
public class PlayerInputKeyListener
  implements KeyListener
{
  /**
   * The map of KeyStrokes-to-Actions
   */
  private HashMap<KeyStroke, Action> map;
  
  /**
   * Constructs a PlayerInputKeyListener with no mappings
   */
  public PlayerInputKeyListener()
  {
    map = new HashMap<KeyStroke, Action>();
  }
  
  /**
   * Constructs a PlayerInputKeyListener using the given mappings
   * @param map The Keystroke-to-Action map
   */
  public PlayerInputKeyListener(Map<KeyStroke, Action> map)
  {
    this();
    this.setMap(map);
  }
  
  /**
   * Removes all mappings.
   */
  public void clear()
  {
    map.clear();
  }
  
  /**
   * Adds a given KeyStroke-to-Action mapping
   * @param stroke The KeyStroke that trigger the Action
   * @param action The Action
   */
  public void addAction(KeyStroke stroke, Action action)
  {
    map.put(stroke, action);
  }
  
  /**
   * Adds all KeyStroke-to-Action mappings from the given map
   * @param map The mappings to add
   */
  public void addActions(Map<KeyStroke, Action> map)
  {
    Set<KeyStroke> keyStrokes = map.keySet();
    for (KeyStroke stroke : keyStrokes)
    {
      addAction(stroke, map.get(stroke));
    }
  }
  
  /**
   * Sets all of the KeyStroke-to-Action mappings to those
   * in the given map. Does not link by reference.
   * @param map The KeyStroke-to-Action mappings to use
   */
  public void setMap(Map<KeyStroke, Action> map)
  {
    this.clear();
    this.addActions(map);
  }
  
  /**
   * Fires actionPerformed on the appropriate Action based
   * on KeyStroke passed in.
   * @param stroke The KeyStroke that occurred
   */
  private void fireActionPerformed(KeyStroke stroke)
  {
    Action action = map.get(stroke);
    if (action == null)
      return;
    
    action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_FIRST, action.toString()));
  }
  
  /* (non-Javadoc)
   * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
   */
  public void keyPressed(KeyEvent e)
  {
    KeyStroke stroke = KeyStroke.getKeyStrokeForEvent(e);
    this.fireActionPerformed(stroke);
  }
  
  /* (non-Javadoc)
   * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
   */
  public void keyReleased(KeyEvent e)
  {
    KeyStroke stroke = KeyStroke.getKeyStrokeForEvent(e);
    this.fireActionPerformed(stroke);
  }
  
  /* (non-Javadoc)
   * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
   */
  public void keyTyped(KeyEvent e)
  {}
}