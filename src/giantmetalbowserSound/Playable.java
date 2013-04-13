package giantmetalbowserSound;

/**
 * The interface that playable sounds should implement.
 * <code>SoundListener</code>s can be added to <code>Playable</code>s to keep track of
 * playback status.
 * @author Daniel Wong (giantmetalbowser)
 * @version 1.0 September 31, 2010
 */
public interface Playable
{
  /**
   * Plays this <code>Playable</code> from the beginning.
   */
  public void play();
  
  /**
   * Plays this <code>Playable</code> from the given frame.
   * @param startFrame The frame to start at.
   */
  public void play(int startFrame);
  
  /**
   * Stops this <code>Playable</code> if it is playing. 
   */
  public void stop();
  
  /**
   * Indicates whether the <code>Playable</code> is playing.
   * @return <code>true</code> if the <code>Playable</code> is playing, <code>false</code> otherwise.
   */
  public boolean isPlaying();
  
  /**
   * Indicates whether the <code>Playable</code> can be played.
   * @return <code>true</code> if the <code>Playable</code> can be played, <code>false</code> otherwise.
   */
  public boolean canBePlayed();
  
  /**
   * Indicates whether the <code>Playable</code> will repeat when played.
   * @return <code>true</code> if the <code>Playable</code> repeats when played, <code>false</code> otherwise.
   */
  public boolean isRepeating();
  
  /**
   * Sets whether the <code>Playable</code> will repeat when played.
   * @param repeating if <code>true</code>, this <code>Playable</code> will repeat; otherwise, it will not repeat.
   */
  public void setRepeating(boolean repeating);
  
  /**
   * Returns the canonical path of the sound file to be played.
   * @return The canonical path of the sound file.
   */
  public String getCanonicalPath();
  
  /**
   * Adds a <code>SoundListener</code> to check for <code>SoundEvents</code>.
   * @param soundListener The <code>SoundListener</code> to add.
   */
  public void addSoundListener(SoundListener soundListener);
  
  /**
   * Removes the given <code>SoundListener</code> from this <code>Playable</code>.
   * @param soundListener The <code>SoundListener</code> to remove.
   */
  public void removeSoundListener(SoundListener soundListener);
}
