package giantmetalbowserFileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A class for reading files. It's simple. Create an
 * <code>EasyFileReader</code> from a <code>String</code>
 * pathname, and then use the <code>read()</code> method.
 * Example:
 * <blockquote><code>EasyFileReader reader = new EasyFileReader("Document.txt");<br/>
 * String text = reader.read();</code></blockquote>
 * @author giantmetalbowser
 */
public class EasyFileReader
{
  /**
   * The currentPathname
   */
  private String pathname;

  /**
   * Constructs a FileIOManager under the given pathname
   * @param pathname the pathname to use
   */
  public EasyFileReader(String pathname)
  {
    this.pathname = pathname;
  }

  /**
   * Changes the pathname
   * @param pathname The new pathname
   */
  public void setPathname(String pathname)
  {
    this.pathname = pathname;
  }

  /**
   * Gets the pathname
   * @return the pathname
   */
  public String getPathname()
  {
    return pathname;
  }
  
  /**
   * Loads a String from the text of the given file.
   * @return The text of the given file.
   */
  public String read()
  {
    StringBuffer strBuff = new StringBuffer();
    FileInputStream in = null;
    try
    {
      in = new FileInputStream(new File(pathname));
      while (in.available() > 0) //TODO I don't know what I'm doing
      {
        strBuff.append((char)(in.read()));
      }
    }
    catch (FileNotFoundException e)
    {
      return strBuff.toString();
    }
    catch (IOException e)
    {
      return strBuff.toString();
    }
    if (in != null)
    {
      try
      {
        in.close();
      }
      catch (IOException e)
      {}
    }
    return strBuff.toString();
  }
}
