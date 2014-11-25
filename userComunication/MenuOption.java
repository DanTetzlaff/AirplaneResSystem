package userComunication;
/**
 * Represents a single menu option that can be printed and selected by the user
 * @author Jordan Kidney
 * @version 1.0
 * 
 * Last Modified: Feb 18, 2014 - Created (Jordan Kidney)
 */

public class MenuOption 
{
	private String option;
	private String description;

	/**
	 * Constructor 
	 * @param option the option/character the user will select
	 * @param description the description for this option
	 */
	public MenuOption(String option, String description) 
	{
		this.option = option.toLowerCase();
		this.description = description;
	}

	/**
	 * Determines if the given character matches the current menu option object
	 * @param usersChoice the char given by the user
	 * @return true for a match, false otherwise
	 */
    public boolean isAMatch(String usersChoice )
    {
      return (option.compareToIgnoreCase(usersChoice) == 0);	
    }
    
    
    public String getOption() { return option; }
    
	/**
	 *  Formated string for use when displaying all options for the menu
	 */
	public String toString()
	{
		return String.format("%5s: %-20s", option, description);
	}
}
