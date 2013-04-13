package giantmetalbowserFileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A class for writing to files. It's simple. Create an
 * <code>EasyFileWriter</code> from a <code>String</code>
 * pathname, and then use the <code>write(String)</code> method.
 * Example:
 * <blockquote><code>EasyFileWriter writer = new EasyFileWriter("Document.txt");<br/>
 * writer.write("This is the text in the document.");</code></blockquote>
 * @author giantmetalbowser
 */
public class EasyFileWriter
{
  /**
   * The currentPathname
   */
  private String pathname;

  /**
   * Constructs a FileIOManager under the given pathname
   * @param pathname the pathname to use
   */
  public EasyFileWriter(String pathname)
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
   * Saves data in the form of a bytestream.
   * @param data The data to save
   * @return <code>true</code> if the save was successful, <code>false</code> otherwise.
   */
  public boolean write(String data)
  {
    FileOutputStream out = null;
    try
    {
      out = new FileOutputStream(new File(pathname));
      out.write(data.getBytes());
    }
    catch (FileNotFoundException e)
    {
      return false;
    }
    catch (IOException e)
    {
      return false;
    }
    
    if (out != null)
    {
      try
      {
        out.close();
      }
      catch (IOException e)
      {
        return false;
      }
      return true;
    }
    return true;
  }
}
