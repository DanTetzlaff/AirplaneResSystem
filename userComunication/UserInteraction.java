package userComunication;

/**
 * Methods used to interact with the user 
 * @author Jordan Kidney
 * @version 3.0
 * 
 * Last Modified: Feb 10, 2013 - complete redesing of the class
 *                 Jan 8, 2013 - Added yesNo Method
 *                 Jan 7, 2013 - Created
 */
import java.util.*;

public class UserInteraction

{
    public static final char NO_CHAR_INPUT = ' ';   //default for character input
    private  Scanner input;

    /**
     * default constructor
     */
    public UserInteraction()
    {
        input = new Scanner(System.in); 
    }

    /**
     * helper method to contain printing of messages to the console
     * @param message the message to print
     */
    public void print(String message)
    {
        System.out.print(message); 
    }

    /**
     * helper method to contain printing of messages to the console with a new line
     * @param message the message to print
     */
    public void println(String message)
    {
        System.out.println(message); 
    }

    /**
     * Displays a message and waits to read an integer from the user
     * @param message the message to display
     * @return the integer inputed by the user
     */
    public int getInput_Int(String message)
    {
        int userInput = 0;
        print(message);
        userInput = input.nextInt();
        input.nextLine(); // remove the newline character entered by the user
        return userInput;   
    }

     /**
     * Displays a message and waits to read an integer from the user. Verifies
     * that the entered number is greater than or equal to smallestValue
     * @param message the message to display
     * @param smallestValue the smallest possible legal value the user can enter
     * @return the integer inputed by the user
     */
    public int getInput_IntGreaterThan(String message, int smallestValue)
    {
        int userInput = 0;
        boolean end = false;

        do
        {
            print(message);
            userInput = input.nextInt();
            input.nextLine(); // remove the newline character entered by the user
        
            if( userInput < smallestValue)
            {
                println("Error: value must be >= " + smallestValue);
            }
            else
              end = true;
        
        }
        while(!end);

        return userInput;   
    }

    /**
     * gets a double from the input without displaying a message
     * @param message the message to display
     * @return the entered double
     */
    public double getInput_Double(String message)
    {
        double userInput = 0;
        print(message);
        userInput = input.nextDouble();
        input.nextLine(); // remove the newline character entered by the user
        return userInput;   
    }

    /**
     * clears one line from the input console
     */
    public void clearInputLine()
    {
        input.nextLine();  
    }

    /**
     * gets an integer from the input without displaying a message
     * @return the entered integer
     */
    public int getInput_Int()
    {
        int userInput = 0;
        userInput = input.nextInt();
        input.nextLine();
        return userInput;   
    }

    /**
     * Displays a message and waits to read a line of text from the user
     * @param message the message to display
     * @return the line inputed by the user
     * */
    public String getInput_String(String message)
    {
        print(message);
        return input.nextLine().trim();
    }

    /**
     * reads one character from the input
     * @param message the message to display
     * @return the entered character or NO_CHAR_INPUT if no input
     */
    public char getInput_char(String message)
    {
        char userData = NO_CHAR_INPUT;
        print(message);
        String userReply = input.nextLine().toUpperCase();
        // make sure the user did not just hit enter
        if(!userReply.isEmpty())
            userData = userReply.charAt(0);

        return userData;
    }

    /**
     * reads one character from the input and validates that a valid character has been entered
     * @param message the message to display
     * @param validChars a string that contains all valid characters that can be entered
     * @return the entered character or NO_CHAR_INPUT if no input
     */
    public char getInput_char(String message, String validChars)
    {
        char userData = NO_CHAR_INPUT;
        String userReply = "";
        boolean end = false ;
        validChars = validChars.toUpperCase();
        do
        {
            userData = NO_CHAR_INPUT;
            print(message);
            userReply = input.nextLine().toUpperCase();
            // make sure the user did not just hit enter
            if(!userReply.isEmpty())
                userData = userReply.charAt(0);

            // check to see if the entered char is amonge the valid chars
            if(validChars.indexOf(userData) == -1)
                System.out.println("Error: wrong input");
            else
                end = true;

        } while(!end);

        return userData;
    }

    public boolean yesNo(String message)
    {
        String userReply = "";
        boolean result = false;
        System.out.print(message + "(y,n)");
        userReply = input.nextLine();
        // make sure the user did not just hit enter
        if(!userReply.isEmpty())
        {
            switch(userReply.charAt(0))
            {
                case 'y':
                case 'Y': result = true;
                break;
            }
        }

        return result;
    }

    /**
     * pauses the program untill the user hits enter 
     */
    public void pause()
    {
        System.out.println("<hit enter to continue>");
        input.nextLine();
    }

    /**
     * Can be used to clear the bluej termial of all text
     */
    public void clearBlueJTerminal()
    {
        System.out.print('\u000C');
    }
}
