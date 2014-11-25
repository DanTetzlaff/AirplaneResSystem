package fileControl;

/**
 * interacts with files, opens and creates an object for a current file, organizes reading and writing functions
 * @author Daniel Tetzlaff
 * @verison 1.0
 * Last Modified: Mar 10, 2014 - Created (By Daniel Tetzlaff)
 *                Mar 12, 2014 - Added writeSave to save file with the same name as original (By Daniel Tetzlaff)
 */

import java.io.*;
public class newFile
{
    private readFile reader;
    private writeFile writer;
    private FileInputStream file;
    String fileName;

    public newFile(String name) throws FileNotFoundException, IOException
    {
        fileName = parse(name);
        file = new FileInputStream(fileName);
        FileOutputStream edit = new FileOutputStream(parseReport(fileName));
        reader = new readFile(file);
        writer = new writeFile(edit);
    }

    private String parse(String original)
    {
        String fileName;
        String[] broken = original.split("\\.");

        if ( !broken[broken.length - 1].equals("txt") )
        {
            fileName = original + ".txt";
        }
        else
        {
            fileName = original;
        }

        return fileName;
    }
    
    private String parseReport(String original)
    {
        String[] broken = original.split("\\.");
        String result = "";
        
        for (int index = 0; index < broken.length -1; index++)
        {
            result += broken[index];
        }
        
        return result + "_REPORT.txt";
    }

    public String nextLine()
    {
        return reader.getLine();
    }
    
    public boolean hasNext()
    {
        return reader.hasNext();
    }
    
    public void writeLine(String contents) throws IOException
    {
        writer.printLine(contents);
    }
    
    public void close() throws IOException
    {
        writer.save();
    }
    
    public void writeInt(int num)
    {
        writer.printInt(num);
    }

    public int nextInt()
    {
        int value = 0;
        
        if ( reader.hasInt() )
        {
            value = reader.getInt();
            reader.getLine();
        }
        
        return value;
    }
    
    public void writeSave(String[] rowPat, int totalRows, String rez) throws IOException
    {
        FileOutputStream save = new FileOutputStream(fileName);
        
        writer.writeSave(rowPat, totalRows, rez, save);
        file.close();
    }
}
