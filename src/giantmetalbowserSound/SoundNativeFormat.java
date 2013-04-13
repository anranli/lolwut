package giantmetalbowserSound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.event.EventListenerList;

/**
 * A class for playing sounds in native Java-supported formats as audio output.
 * <strong>This class can only be used for small audio files of types that Java supports.</strong>
 * Those file types include <strong>.WAV</strong>, <strong>.AIFF</strong>, and <strong>.AU</strong>.
 * It utilizes the <code>javax.sound.sampled</code> package to create a
 * far simpler interface for playing sounds.
 * @author Daniel Wong (giantmetalbowser)
 * @version 1.01 October 31, 2010
 */
class SoundNativeFormat implements Playable
{
  /**
   * The <code>Clip</code> that contains the sound to play.
   */
  private Clip audioClip;
  /**
   * Determines whether or not the <code>SoundNativeFormat</code> is repeating. Defaults to <code>false</code>.
   */
  private boolean isRepeating;
  /**
   * Determines if the <code>SoundNativeFormat</code> is currently playing.
   */
  private boolean isPlaying;
  /**
   * Determines if the <code>SoundNativeFormat</code> can be played.
   * Evaluated when the audio file is interpretted.
   */
  private boolean canBePlayed;
  /**
   * The canonical pathname of the sound file.
   */
  private final String canonicalPath;
  /**
   * The <code>SoundListener</code>s to track <code>SoundEvent</code>s.
   */
  private EventListenerList soundListeners;
  /**
   * Determines if the <code>SoundNativeFormat</code> was immedately interrupted by
   * the <code>stop()</code> method.
   */
  private boolean justInterrupted;
  
  /**
   * Constructs a <code>SoundNativeFormat</code> object from the audio file with the given file name (including path). 
   * If the audio file does not exist or is not supported by Java, the <code>SoundNativeFormat</code> cannot be played.
   * @param fileName The file name (including path) of the audio file.
   */
  public SoundNativeFormat(String fileName)
  {
    this(new File(fileName));
  }
  
  /**
   * Constructs a <code>SoundNativeFormat</code> object from the given audio file. 
   * If the audio file does not exist or is not supported by Java, the <code>SoundNativeFormat</code> cannot be played.
   * @param soundFile The file name (including path) of the audio file.
   */
  public SoundNativeFormat(File soundFile)
  {
    soundListeners = new EventListenerList();
    String pathAttempt = null;
    try
    {
      pathAttempt = soundFile.getCanonicalPath();
    }
    catch (IOException e)
    {
      pathAttempt = soundFile.getAbsolutePath();
    }
    canonicalPath = pathAttempt;
    canBePlayed = true;
    AudioInputStream audioInputStream = null;
    try
    {
      audioInputStream = AudioSystem.getAudioInputStream(soundFile);
    }
    catch (UnsupportedAudioFileException e)
    {
      canBePlayed = false;
      return;
    }
    catch (IOException e)
    {
      canBePlayed = false;
      return;
    }
    AudioFormat musicAudioFormat = audioInputStream.getFormat();
    try
    {
      audioClip = (Clip)(AudioSystem.getLine(new DataLine.Info(Clip.class, musicAudioFormat)));
    }
    catch (LineUnavailableException e)
    {
      canBePlayed = false;
      return;
    }
    try
    {
      audioClip.open(audioInputStream);
      audioClip.addLineListener(new SoundLineListener());
    }
    catch (LineUnavailableException e)
    {
      canBePlayed = false;
      return;
    }
    catch (IOException e)
    {
      canBePlayed = false;
      return;
    }
  }
  
  /**
   * Plays this <code>SoundNativeFormat</code> from the start.
   * If the <code>SoundNativeFormat</code> is currently playing while this method is called, 
   * the <code>SoundNativeFormat</code> will start from the beginning.
   */
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#play()
   */
  public void play()
  {
    play(0);
  }
  
  /**
   * Plays this <code>SoundNativeFormat</code> from the given frame.
   * If the <code>SoundNativeFormat</code> is currently playing while this method is called,
   * the <code>SoundNativeFormat</code> will restart from the given frame.
   * @param startFrame The frame to start at.
   */
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#play(int)
   */
  public void play(int startFrame)
  {
    if (!canBePlayed)
      return;

    notifySoundListeners(SoundEvent.SOUND_PLAYED);
    audioClip.setFramePosition(startFrame);
    audioClip.start();
  }
  
  /**
   * Stops playback. If the <code>SoundNativeFormat</code> is not in playback when <code>stop()</code>
   * is called, the method will not notify <code>SoundListener</code>s.
   */
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#stop()
   */
  public void stop()
  {
    if (!canBePlayed || !isPlaying)
      return;
    
    justInterrupted = true;
    notifySoundListeners(SoundEvent.SOUND_INTERRUPTED);
    audioClip.stop();
  }
  
  /**
   * Indicates whether the <code>SoundNativeFormat</code> is currently being played.
   * @return <code>true</code> if the <code>SoundNativeFormat</code> is being played, <code>false</code> otherwise.
   */
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#isPlaying()
   */
  public boolean isPlaying()
  {
    return isPlaying;
  }

  /**
   * Indicates whether the <code>SoundNativeFormat</code> can be played.
   * This is determined when the <code>SoundNativeFormat</code> is created, 
   * based on the validity of the given sound file.
   * @return <code>true</code> if the <code>SoundNativeFormat</code> can be played, <code>false</code> otherwise.
   */
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#canBePlayed()
   */
  public boolean canBePlayed()
  {
    return canBePlayed;
  }
  
  /**
   * Sets whether to repeat the <code>SoundNativeFormat</code> when played.
   * @param repeating if <code>true</code>, this <code>SoundNativeFormat</code> will repeat when played; otherwise, it will not repeat.
   */
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#setRepeating(boolean)
   */
  public void setRepeating(boolean repeating)
  {
    this.isRepeating = repeating;
  }
  
  /**
   * Indicates whether the <code>SoundNativeFormat</code> will repeat when played.
   * @return <code>true</code> if the <code>SoundNativeFormat</code> will repeat, <code>false</code> otherwise.
   */
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#isRepeating()
   */
  public boolean isRepeating()
  {
    return isRepeating;
  }
  
  /**
   * Returns the canonical path of the audio file to be played by this <code>SoundNativeFormat</code>.
   * @return The canonical path of the audio file.
   */
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#getCanonicalPath()
   */
  public String getCanonicalPath()
  {
    return canonicalPath;
  }
  
  /**
   * Attaches a <code>SoundListener</code> to this <code>SoundNativeFormat</code>.
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
   * Removes a <code>SoundListener</code> from this <code>SoundNativeFormat</code>.
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
   * <code>SoundNativeFormat</code>.
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
   * <code>SoundNativeFormat</code>.
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
   * <code>SoundNativeFormat</code>.
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
   * <code>SoundNativeFormat</code>.
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
   * Notifies the <code>SoundListener</code>s that an event occurred, based on the given
   * type. If the type if not valid, this method does nothing.
   * @param type The type of <code>SoundEvent</code> to notify.
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
   * The <code>LineListener</code> to help keep track of sound playback.
   * @author Daniel Wong (giantmetalbowser)
   */
  protected class SoundLineListener implements LineListener
  {
    public void update(LineEvent event)
    {
      if (event.getType() == LineEvent.Type.START)
      {
        isPlaying = true;
      }
      else if (event.getType() == LineEvent.Type.STOP)
      {
        if (!justInterrupted)
        {
          //playback completed, not necessarily ended
          notifySoundListeners(SoundEvent.SOUND_COMPLETED);
          if (isRepeating)
          {
            play();
          }
          else
          {
            isPlaying = false;
            notifySoundListeners(SoundEvent.SOUND_ENDED);
          }
        }
        else //Stopped using stop()
        {
          isPlaying = false;
          justInterrupted = false;
          notifySoundListeners(SoundEvent.SOUND_ENDED);
        }
      }
    }
  }
}