package giantmetalbowserSound;

import java.io.File;

/**
 * The class for playing an audio file of a variety of file types.
 * Supported file types include <strong>.AIFF</strong>, <strong>.AU</strong>,
 * <strong>.MP3</strong>, and <strong>.WAV</strong>.
 * This class utilizes other classes within the package to make file playing
 * easier. It contains a <code>Playable</code> that it initializes in the
 * constructor, then simply calls that <code>Playable</code>'s methods.
 * @author Daniel Wong (giantmetalbowser)
 * @version 1.01 October 31, 2010
 */
public class Sound implements Playable
{
  /**
   * The <code>Playable</code> to use for playback.
   */
  private Playable sound;
  
  /**
   * Constructs a <code>Sound</code> from the given file name.
   * If the file name ends with ".mp3" then a <code>SoundMP3</code> is used
   * to assist with playback, otherwise a <code>SoundNativeFormat</code> is used.
   * @param fileName The file name of the audio.
   */
  public Sound(String fileName)
  {
    if (fileName.endsWith(".mp3"))
    {
      sound = new SoundMP3(fileName);
    }
    else
    {
      sound = new SoundNativeFormat(fileName);
    }
  }
  
  /**
   * Constructs a <code>Sound</code> from the given file.
   * If the file name ends with ".mp3" then a <code>SoundMP3</code> is used
   * to assist with playback, otherwise a <code>SoundNativeFormat</code> is used.
   * @param file The audio file to use for playback.
   */
  public Sound(File file)
  {
    this(file.getAbsolutePath());
  }
  
  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#addSoundListener(giantmetalbowserSound.SoundListener)
   */
  public void addSoundListener(SoundListener soundListener)
  {
    sound.addSoundListener(soundListener);
  }

  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#canBePlayed()
   */
  public boolean canBePlayed()
  {
    return sound.canBePlayed();
  }

  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#getCanonicalPath()
   */
  public String getCanonicalPath()
  {
    return sound.getCanonicalPath();
  }

  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#isPlaying()
   */
  public boolean isPlaying()
  {
    return sound.isPlaying();
  }

  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#isRepeating()
   */
  public boolean isRepeating()
  {
    return sound.isRepeating();
  }

  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#play()
   */
  public void play()
  {
    sound.play();
  }

  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#play(int)
   */
  public void play(int startFrame)
  {
    sound.play(startFrame);
  }

  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#removeSoundListener(giantmetalbowserSound.SoundListener)
   */
  public void removeSoundListener(SoundListener soundListener)
  {
    sound.removeSoundListener(soundListener);
  }

  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#setRepeating(boolean)
   */
  public void setRepeating(boolean repeating)
  {
    sound.setRepeating(repeating);
  }

  /* (non-Javadoc)
   * @see giantmetalbowserSound.Playable#stop()
   */
  public void stop()
  {
    sound.stop();
  }
}
