package userComunication;
/**
 * Used to manage printing a menu of choices and getting the proper selected choice from the user
 * @author Jordan Kidney
 * @version 1.0
 * 
 * Last Modified: Feb 18, 2014 - Created (Jordan Kidney)
 */
import java.util.ArrayList;
public class Menu 
{
    private UserInteraction comm;
    private boolean clearOnPrintMenu;
    private ArrayList<MenuOption> menuChoices;

    /**
     * Default Constructor
     */
    public Menu(UserInteraction comm)
    {
        this.comm = comm;
        menuChoices = new ArrayList<MenuOption>();
        clearOnPrintMenu = false;
    }

    public Menu()
    {
        this( new UserInteraction() );
    }

    /**
     * used to turn on/off the clear terminal when the meneu is printed
     */
    public void clearWhenMenuIsPrinted(boolean value)
    {
      clearOnPrintMenu = value;  
    }
    
    /**
     * Adds a menu option that the user can select. This method does not 
     * check for duplicate choices/options
     * 
     * @param option the option object to add to the possible choices 
     */
    public void addMenuOption(MenuOption option)
    {
        menuChoices.add(option);  
    }
   /**
     * Prints all the choices to the user and gets a valid choice from the user.
     * @return the selected option (based upon the options that have been given to the user)
     */
    public MenuOption getUserChoice()
    {
        return getUserChoice("Menu: ");
    }

    /**
     * Prints all the choices to the user and gets a valid choice from the user.
     * @param header the message to display above the menu
     * @return the selected option (based upon the options that have been given to the user)
     */
    public MenuOption getUserChoice(String header)
    {
        String selection = "";
        MenuOption selectedOption  = null;
        boolean end = false;
        //keep asking for the choice until they enter to correct value
        do
        {
            if(clearOnPrintMenu) comm.clearBlueJTerminal();
            
            comm.println(header);
            //print out all options
            for(MenuOption option: menuChoices )
                comm.println(option.toString());

            //get user choice
            selection = comm.getInput_String("Enter choice: ");

            selectedOption = isValidChoice(selection);
            if(selectedOption == null)
            {
                comm.println("Error: invalid choice");
                
                if(clearOnPrintMenu)comm.pause();
            }
            else
                end = true;

        }while(!end);
        return selectedOption;
    }

    /**
     * Determines if the given choice is valid based upon the current options in the menu
     * @param choice the choice made by the user
     * @return The selected menu option object if it is a valid choice, null otherwise
     */
    private MenuOption isValidChoice(String choice)
    {
        MenuOption matchFound = null;

        for(MenuOption option: menuChoices )
        {
            if(option.isAMatch(choice) )
            {
                matchFound = option;
                break;
            }

        }

        return matchFound;
    }

    /**
     * Removes all current menu options for this menu
     */
    public void clear() { menuChoices.clear(); }
}

