package fileControl;

/**
 * reads information from a given FileInputStream
 * @author Daniel Tetzlaff
 * @version 1.0
 * Last Modified: Mar 10, 2014 - Created (By Daniel Tetzlaff)
 */

import java.io.*;
import java.util.*;
public class readFile
{
    private Scanner fileScan;

    public readFile (FileInputStream file)
    {
        fileScan = new Scanner(file);
    }

    public String getLine()
    {
        return fileScan.nextLine();
    }
    
    public boolean hasNext()
    {
        return fileScan.hasNext();
    }

    public boolean hasInt()
    {
        return fileScan.hasNextInt();
    }

    public int getInt()
    {
        return fileScan.nextInt();
    }
}
