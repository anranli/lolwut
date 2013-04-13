package lolwut;

import giantmetalbowserFileIO.EasyFileReader;
import giantmetalbowserFileIO.EasyFileWriter;


/**
 * The class for reading and writing high scores to files.
 * It utilizes the giantmetalbowserFileIO
 * @author Daniel Wong
 */
public class ScoreIOManager
{
  /**
   * The default path to save files to (scores.dat).
   */
  public static final String DEFAULT_PATH = "scores.dat";
  /**
   * The maximum number of scores to save (10).
   */
  public static final int MAX_NUM_SCORES = 10;
  /**
   * The character used to separate groups of names and scores (<code>\n</code>).
   */
  public static final char SCORE_SEPARATOR = '\n';
  /**
   * The character used to separate the names from the scores (<code>(char)1</code>).
   */
  public static final char NAME_SEPARATOR = (char)(1);
  
  private String path;
  
  /**
   * Constructs a <code>ScoreIOManager</code> that reads and writes to the given pathname.
   */
  public ScoreIOManager()
  {
    this(DEFAULT_PATH);
  }
  
  /**
   * Constructs a <code>ScoreIOManager</code> that reads and writes to the given pathname.
   * @param path The pathname to read and write to.
   */
  public ScoreIOManager(String path)
  {
    this.path = path;
  }
  
  /**
   * Checks if the given score is on the list of high scores.
   * @param score The score to check
   * @return <code>true</code> if the given score is on the list of high scores, <code>false</code> otherwise
   */
  public boolean isHighScore(int score)
  {
    return getPlace(score) >= 0;
  }
  
  /**
   * Checks the place of the given score (zero to nine) based on the current high scores.
   * @param score The score to check
   * @return The place as an integer (0-9) of the given score, or -1 if the score is not on the list of high scores
   */
  public int getPlace(int score)
  {
    int[] scores = getScores();
    if (scores.length <= 0)
      return 0;
    
    for (int i = 0; i < scores.length; i ++)
    {
      if (score > scores[i])
      {
        return i;
      }
    }
    if (scores.length < MAX_NUM_SCORES)
      return scores.length;
    
    return -1;
  }
  
  /**
   * Writes a name and score association to the given path, if the score is greater than the other
   * scores that have been recorded or fewer than the maximum number of scores were recorded.
   * @param name The name to record
   * @param score The score to record
   * @return <code>true</code> if the given score was recorded, <code>false</code> otherwise.
   */
  public boolean writeScore(String name, int score)
  {
    boolean wroteGivenScore = false;
    int scoresWritten = 0;
    StringBuffer allScores = new StringBuffer();
    String[] previousScores = readScores().split("\n");
    for (String scoreStr : previousScores)
    {
      if (scoresWritten >= MAX_NUM_SCORES)
        break;
      
      if (score > parseScore(scoreStr) && !wroteGivenScore)
      {
        allScores.append(name);
        allScores.append(NAME_SEPARATOR);
        allScores.append(score);
        allScores.append(SCORE_SEPARATOR);
        wroteGivenScore = true;
        scoresWritten ++;
      }
      if (scoresWritten < MAX_NUM_SCORES)
      {
        allScores.append(scoreStr);
        allScores.append(SCORE_SEPARATOR);
        scoresWritten ++;
      }
    }
    if (scoresWritten < MAX_NUM_SCORES && !wroteGivenScore)
    {
      allScores.append(name);
      allScores.append(NAME_SEPARATOR);
      allScores.append(score);
      allScores.append(SCORE_SEPARATOR);
      wroteGivenScore = true;
    }
    allScores.deleteCharAt(allScores.length()-1); //get rid of last newline character
    EasyFileWriter w = new EasyFileWriter(path);
    return w.write(allScores.toString()) && wroteGivenScore;
  }
  
  private String parseName(String scoreStr)
  {
    try
    {
      return scoreStr.substring(0, scoreStr.indexOf(NAME_SEPARATOR));
    }
    catch(Exception e)
    {
      System.err.println("Corrupt high score from "+path+" : "+scoreStr);
    }
    return scoreStr;
  }
  
  private int parseScore(String scoreStr)
  {
    if (scoreStr == null || scoreStr.length() <= 0)
      return 0;
    
    int startPos = scoreStr.indexOf(NAME_SEPARATOR)+1;
    int score = 0;
    try
    {
      score = Integer.parseInt(scoreStr.substring(startPos));
    }
    catch (Exception e)
    {
      System.err.println("Corrupt high score from "+path+" : "+scoreStr);
    }
    return score;
  }
  
  private String readScores()
  {
    EasyFileReader r = new EasyFileReader(path);
    return r.read();
  }
  
  /**
   * Gets a <code>String</code> array of the names recorded in the file.
   * @return A <code>String</code> array of the names recorded
   */
  public String[] getNames()
  {
    String[] scoreStrs = readScores().split(""+SCORE_SEPARATOR);
    if (scoreStrs.length <= 0 || scoreStrs[0].length() <= 0)
      return new String[0];
    String[] names = new String[scoreStrs.length];
    for (int i = 0; i < scoreStrs.length && i < names.length; i ++)
    {
      names[i] = parseName(scoreStrs[i]);
    }
    return names;
  }
  
  /**
   * Gets a <code>int</code> array of the scores recorded in the file.
   * @return A <code>int</code> array of the scores recorded
   */
  public int[] getScores()
  {
    String[] scoreStrs = readScores().split(""+SCORE_SEPARATOR);
    if (scoreStrs.length <= 0 || scoreStrs[0].length() <= 0)
      return new int[0];
    
    int[] scores = new int[scoreStrs.length];
    for (int i = 0; i < scoreStrs.length && i < scores.length; i ++)
    {
      scores[i] = parseScore(scoreStrs[i]);
    }
    return scores;
  }
}