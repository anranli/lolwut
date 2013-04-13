package giantmetalbowserSound;

/**
 * The listener for the <code>giantmetalbowserSound</code> package. 
 * @author Daniel Wong (giantmetalbowser)
 * @version 1.0 September 31, 2010
 */
public interface SoundListener extends java.util.EventListener
{ 
  /**
   * Invoked when a <code>Playable</code> is played.
   * This occurs both when the <code>play()</code> method is called and when the
   * <code>Playable</code> repeats.
   * @param e The <code>SoundEvent</code>.
   */
  public void soundPlayed(SoundEvent e);
  /**
   * Invoked when a <code>Playable</code> is interrupted by the <code>stop()</code> method.
   * A call to <code>soundInterrupted(SoundEvent)</code> should always be followed by a call
   * to <code>soundEnded</code>.
   * @param e The <code>SoundEvent</code>.
   */
  public void soundInterrupted(SoundEvent e);
  /**
   * Invoked when a <code>Playable</code> completes its playback.
   * A call to <code>soundCompleted(SoundEvent)</code> should always be followed by either a call to
   * <code>soundPlayed(SoundEvent)</code> or <code>soundEnded(SoundEvent)</code>, depending on the
   * <code>Playable</code>'s repeat setting.
   * @param e The <code>SoundEvent</code>
   */
  public void soundCompleted(SoundEvent e);
  /**
   * Invoked when a <code>Playable</code>'s playback ends.
   * A call to <code>soundEnded(SoundEvent)</code> should always come after either a call to
   * <code>soundInterrupted(SoundEvent)</code> or <code>soundCompleted(SoundEvent)</code>, depending
   * on how the <code>Playable</code> ended its playback.
   * @param e The <code>SoundEvent</code>
   */
  public void soundEnded(SoundEvent e);
}
