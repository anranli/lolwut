package giantmetalbowserSound;

/**
 * Represents an event when a <code>Playable</code> starts playing,
 * is stopped, finishes playing, or ends. <br/>
 * <ul>
 * <li><code>SOUND_PLAYED</code> events occur when a <code>Playable</code> begins
 * to play.</li>
 * <li><code>SOUND_INTERRUPTED</code> events occur when a <code>Playable</code> is
 * interrupted using its <code>stop()</code> method.</li>
 * <li><code>SOUND_COMPLETED</code> events occur when a <code>Playable</code> reaches
 * the end of its audio file. The <code>Playable</code> may or may not continue to
 * play, depending on if it is set to repeat or not.</li>
 * <code>SOUND_ENDED</code> events occur when a <code>Playable</code> ends
 * <li>its playback for any reason. This can occur either by <code>stop()</code> method
 * (in which case a <code>SOUND_ENDED</code> event is also created) or by
 * reaching the end of the audio file (in which case a <code>SOUND_COMPLETED</code>
 * events is also created).</li>
 * </ul> 
 * @author Daniel Wong (giantmetalbowser)
 * @version 1.0 September 31, 2010
 */
@SuppressWarnings("serial")
public class SoundEvent extends java.util.EventObject
{
  /**
   * Indicates a <code>SoundEvent</code> for starting playback. <code>SOUND_PLAYED</code> events
   * occur either when a <code>Playable</code>'s <code>play()</code> method is called, or after the
   * <code>Playable</code> finished playback and is set to repeat. If a <code>SOUND_PLAYED</code>
   * event is created because of repeating, it will occur after a <code>SOUND_COMPLETED</code> event.
   */
  public static final int SOUND_PLAYED = 0;
  /**
   * Indicates a <code>SoundEvent</code> for interrupting playback with <code>stop()</code>.
   * A <code>SOUND_INTERRUPTED</code> event will be followed by a <code>SOUND_ENDED</code>
   * event.
   */
  public static final int SOUND_INTERRUPTED = 1;
  /**
   * Indicates a <code>SoundEvent</code> for reaching the end of the playback.
   * If the <code>Playable</code> is set to repeat, the <code>SOUND_COMPLETED</code> event
   * will be followed by a <code>SOUND_PLAYED</code> event. Otherwise, a <code>SOUND_COMPLETED</code>
   * event will be followed by a <code>SOUND_ENDED</code> event.
   */
  public static final int SOUND_COMPLETED = 2;
  /**
   * Indicates a <code>SoundEvent</code> for ending playback in some way,
   * either by the <code>stop()</code> method or by reaching the end of playback
   * without repeating. <code>SOUND_ENDED</code> events will occur after
   * <code>SOUND_COMPLETED</code> or <code>SOUND_INTERRUPTED</code> events.
   */
  public static final int SOUND_ENDED = 3;
  
  /**
   * The <code>Playable</code> that caused this <code>SoundEvent</code>.
   */
  protected final Playable source;
  /**
   * The type of <code>SoundEvent</code> (<code>SOUND_PLAYED</code> or
   * <code>SOUND_INTERRUPTED</code> or <code>SOUND_COMPLETED</code> or
   * <code>SOUND_ENDED</code>).
   */
  protected final int type;
  
  /**
   * Constructs a <code>SoundEvent</code> with the given source of the given type.
   * @param source The source of the <code>SoundEvent</code>.
   * @param type The type (<code>SOUND_PLAYED</code> or <code>SOUND_INTERRUPTED</code> or
   * <code>SOUND_COMPLETED</code> or <code>SOUND_ENDED</code>) of this <code>SoundEvent</code>.
   */
  public SoundEvent(Playable source, int type)
  {
    super(source);
    this.source = source;
    this.type = type;
  }

  /**
   * Gets the source <code>Playable</code> that triggered this <code>SoundEvent</code>.
   * @return The source that triggered this event.
   */
  public Playable getSource()
  {
    return source;
  }

  /**
   * Gets the type (<code>SOUND_PLAYED</code> or <code>SOUND_INTERRUPTED</code> or
   * <code>SOUND_COMPLETED</code> or <code>SOUND_ENDED</code>) of this
   * <code>SoundEvent</code>.
   * @return The type of <code>SoundEvent</code>.
   */
  public int getType()
  {
    return type;
  }
}
