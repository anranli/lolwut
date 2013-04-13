package giantmetalbowserSound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.event.EventListenerList;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 * A class for playing MP3 music files as audio output.
 * Utilizes Javazoom's JLayer version 1.0 to interpret MP3 files.
 * This library can be found at http://www.javazoom.net/javalayer/sources.html
 * @author Daniel Wong (giantmetalbowser)
 * @version 1.0 September 31, 2010
 */
class SoundMP3 implements Playable
{
  /**
   * The file name, saved as a <code>String</code>.
   */
  private String fileName;
  /**
   * The canonical path, saved as a <code>String</code>.
   */
  private final String canonicalPath;
  /**
   * The <code>javazoom.jl.player.advanced.AdvancedPlayer</code> to
   * assist with sound playing.
   */
  private AdvancedPlayer soundPlayer;
  /**
   * Determines if the <code>SoundMP3</code> can be played.
   */
  private boolean canBePlayed;
  /**
   * Determines whether to repeat this <code>SoundMP3</code>.
   */
  private boolean isRepeating;
  /**
   * Determines if the <code>SoundMP3</code> is currently playing.
   */
  private boolean isPlaying;
  /**
   * The <code>SoundListener</code>s to track <code>SoundEvent</code>s.
   */
  private EventListenerList soundListeners;
  
  /**
   * Constructs a <code>SoundMP3</code> to play the sound from the given file name.
   * The given file name must end in ".mp3" and the file must exist, or the
   * <code>SoundMP3</code> will not be playable.
   * @param fileName
   */
  public SoundMP3(String fileName)
  {
    soundListeners = new EventListenerList();
    this.fileName = fileName;
    //Checks if a file in .mp3 format with the given file name exists
    File tempFile = new File(fileName);
    canBePlayed = (fileName != null && fileName.endsWith(".mp3") && tempFile.exists());
    String pathAttempt = null;
    try
    {
      pathAttempt = tempFile.getCanonicalPath();
    }
    catch (IOException e)
    {
      pathAttempt = tempFile.getAbsolutePath();
    }
    canonicalPath = pathAttempt;
  }
  
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#play()
   */
  public void play()
  {
    play(0);
  }
  
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#play(int)
   */
  public void play(int start)
  {
    if (!canBePlayed)
      return;
    
    if (soundPlayer != null)
    {
      soundPlayer.close();
      soundPlayer = null;
    }
    try
    {
      FileInputStream fileInputStream = new FileInputStream(fileName);
      BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
      soundPlayer = new AdvancedPlayer(bufferedInputStream);
      soundPlayer.setPlayBackListener(new MyPlaybackListener());
    }
    catch (Exception e)
    {
      canBePlayed = false;
      isPlaying = false;
      return;
    }
    final int startCopy = start;
    new Thread()
    {
      public void run()
      {
        try
        {
          isPlaying = true;
          soundPlayer.play(startCopy, Integer.MAX_VALUE);
        }
        catch (Exception e)
        {
          canBePlayed = false;
          isPlaying = false;
        }
      }
    }.start();
    
    this.notifySoundListeners(SoundEvent.SOUND_PLAYED);
  }
  
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#isPlaying()
   */
  public boolean isPlaying()
  {
    return isPlaying;
  }
  
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#stop()
   */
  public void stop()
  {
    if (soundPlayer != null && isPlaying)
    {
      this.notifySoundListeners(SoundEvent.SOUND_INTERRUPTED);
      soundPlayer.close();
      soundPlayer = null;
      isPlaying = false;
      this.notifySoundListeners(SoundEvent.SOUND_ENDED);
    }
  } 
  
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#canBePlayed()
   */
  public boolean canBePlayed()
  {
    return canBePlayed;
  }

  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#isRepeating()
   */
  public boolean isRepeating()
  {
    return isRepeating;
  }

  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#setRepeating(boolean)
   */
  public void setRepeating(boolean repeating)
  {
    this.isRepeating = repeating;
  }
  
  /**
   * Returns the canonical path of the MP3 sound to be played. If an error occurred
   * when retrieving the canonical path, this method returns the absolute path instead.
   * @return The canonical path of the MP3 to play, or the absolute path if an error occurred. 
   */
  public String getCanonicalPath()
  {
    return canonicalPath;
  }
  
  /**
   * Attaches a <code>SoundListener</code> to this <code>SoundMP3</code>.
   * @param soundListener The new <code>SoundListener</code>.
   */
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#addSoundListener(giantmetalbowserSound.SoundListener)
   */
  public void addSoundListener(SoundListener soundListener)
  {
    soundListeners.add(SoundListener.class, soundListener);
  }
  
  /**
   * Removes a <code>SoundListener</code> from this <code>SoundMP3</code>.
   * @param soundListener The <code>SoundListener</code> to remove.
   */
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#removeSoundListener(giantmetalbowserSound.SoundListener)
   */
  public void removeSoundListener(SoundListener soundListener)
  {
    soundListeners.remove(SoundListener.class, soundListener);
  }
  
  /**
   * Calls <code>soundPlayed()</code> on each <code>SoundListener</code> added to this
   * <code>SoundMP3</code>.
   */
  protected void fireSoundPlayed()
  {
    Object[] listenersArray = soundListeners.getListenerList();
    for (int i = 0; i < listenersArray.length; i ++)
    {
      try
      {
        ((SoundListener)listenersArray[i]).soundPlayed(createEvent(SoundEvent.SOUND_PLAYED));
      }
      catch (ClassCastException e)
      {}
    }
  }
  
  /**
   * Calls <code>soundInterrupted()</code> on each <code>SoundListener</code> added to this
   * <code>SoundMP3</code>.
   */
  protected void fireSoundInterrupted()
  {
    Object[] listenersArray = soundListeners.getListenerList();
    for (int i = 0; i < listenersArray.length; i ++)
    {
      try
      {
        ((SoundListener)listenersArray[i]).soundInterrupted(createEvent(SoundEvent.SOUND_INTERRUPTED));
      }
      catch (ClassCastException e)
      {}
    }
  }
  
  /**
   * Calls <code>soundEnded()</code> on each <code>SoundListener</code> added to this
   * <code>SoundMP3</code>.
   */
  protected void fireSoundEnded()
  {
    Object[] listenersArray = soundListeners.getListenerList();
    for (int i = 0; i < listenersArray.length; i ++)
    {
      try
      {
        ((SoundListener)listenersArray[i]).soundEnded(createEvent(SoundEvent.SOUND_ENDED));
      }
      catch (ClassCastException e)
      {}
    }
  }
  
  /**
   * Calls <code>soundCompleted()</code> on each <code>SoundListener</code> added to this
   * <code>SoundMP3</code>.
   */
  protected void fireSoundCompleted()
  {
    Object[] listenersArray = soundListeners.getListenerList();
    for (int i = 0; i < listenersArray.length; i ++)
    {
      try
      {
        ((SoundListener)listenersArray[i]).soundCompleted(createEvent(SoundEvent.SOUND_COMPLETED));
      }
      catch (ClassCastException e)
      {}
    }
  }
  
  /**
   * Calls one of the <code>fireSound_____()</code> methods based on the given type of
   * <code>SoundEvent</code>. The given type should be <code>SoundEvent.SOUND_PLAYED</code>,
   * <code>SoundEvent.SOUND_INTERRUPTED</code>, <code>SoundEvent.SOUND_COMPLETED</code>,
   * or <code>SoundEvent.SOUND_ENDED</code>.
   * @param type The type of <code>SoundEvent</code>.
   */
  private void notifySoundListeners(int type)
  {
    if (type == SoundEvent.SOUND_PLAYED)
    {
      fireSoundPlayed();
    }
    else if (type == SoundEvent.SOUND_INTERRUPTED)
    {
      fireSoundInterrupted();
    }
    else if (type == SoundEvent.SOUND_COMPLETED)
    {
      fireSoundCompleted();
    }
    else if (type == SoundEvent.SOUND_ENDED)
    {
      fireSoundEnded();
    }
  }
  
  /**
   * Returns a new <code>SoundEvent</code> created from the given type.
   * @param type The type of <code>SoundEvent</code>.
   * @return The <code>SoundEvent</code>.
   */
  private SoundEvent createEvent(int type)
  {
    return new SoundEvent(this, type);
  }

  /**
   * Determines actions (replaying or stopping) upon completion of playback and
   * notifies listeners with <code>SoundEvent</code>s.
   * This is only called when the <code>SoundMP3</code> completes playback, and
   * NOT when <code>stop()</code> is called.
   */
  private void completedPlayback()
  {
    this.notifySoundListeners(SoundEvent.SOUND_COMPLETED);
    if (isRepeating && isPlaying) //isPlaying necessary due to delay when stop() called
    {
      play();
    }
    else //!isRepeating
    {
      soundPlayer = null;
      isPlaying = false;
      this.notifySoundListeners(SoundEvent.SOUND_ENDED);
    }
  }
  
  /**
   * The <code>javazoom.jl.player.advanced.PlaybackListener</code> to help keep
   * track of sound playback.
   * @author Daniel Wong (giantmetalbowser)
   */
  protected class MyPlaybackListener extends PlaybackListener
  {
    public void playbackFinished(PlaybackEvent e)
    {
      completedPlayback();
    }
    
    public void playbackStarted(PlaybackEvent e)
    {}
  }
}
