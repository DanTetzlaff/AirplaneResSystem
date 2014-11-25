/**
 * Represents main functionaly and flow of Air Plane Reservation 
 * System
 * @author Jordan Kidney
 * @author Daniel Tetzlaff
 * @version 2.0
 * Last Modified: Feb 18, 2014 - Created (By Jordan Kidney)
 *                Mar  9, 2014 - Develop basic menu interface/interaction (By Daniel Tetzlaff)
 *                Mar 11, 2014 - Edit added (By Daniel Tetzlaff)
 *                Mar 12, 2014 - Save added (By Daniel Tetzlaff)
 *                             - Completed full functionality, added remove, custom seat, better formatting for output (By Daniel Tetzlaff)
 */

import userComunication.*;

public class AirPlaneResSystem
{
    private UserInteraction comm = new UserInteraction();
    private Menu planeMenu = new Menu(comm);
    private Menu editMenu = new Menu(comm);
    private Menu mealMenu = new Menu(comm);
    private Menu customSeatMenu = new Menu(comm);
    private Plane currentPlane;
    private MenuOption choice = null;
    private boolean planeOpen = false;      // checks for if there is a plane file that is open               
    boolean report = false;                 // a report will only be made once during a single opening of a file

    /**
     * Startup method that will run the reservation system
     */
    public void run()
    {
        createMenu();
        char option;
        comm.clearBlueJTerminal();

        do
        {
            choice = planeMenu.getUserChoice();
            option = choice.getOption().charAt(0);

            comm.println("The user selected: " + option);

            if ( option != 'q' )
            {
                initializeChoice(option);
            }
        }
        while ( option != 'q' );

        if ( planeOpen )
        {
            save();
        }
        comm.clearBlueJTerminal();
        comm.print("Program Exited. Goodbye.");
    }

    /**
     * Creates menus and options that will be displayed to the user
     */
    private void createMenu()
    {
        planeMenu.addMenuOption( new MenuOption("L", "Load reservation file") );
        planeMenu.addMenuOption( new MenuOption("S", "Save and close file") );
        planeMenu.addMenuOption( new MenuOption("V", "View current plane") );
        planeMenu.addMenuOption( new MenuOption("E", "Edit reservation") );
        planeMenu.addMenuOption( new MenuOption("A", "Add new reservation") );
        planeMenu.addMenuOption( new MenuOption("R", "Remove reservation") );
        planeMenu.addMenuOption( new MenuOption("C", "Custom seat search") );
        planeMenu.addMenuOption( new MenuOption("M", "Show all reservations") );
        planeMenu.addMenuOption( new MenuOption("G", "Generate report") );
        planeMenu.addMenuOption( new MenuOption("Q", "Quit") );

        editMenu.addMenuOption( new MenuOption("S", "Edit seat") );
        editMenu.addMenuOption( new MenuOption("P", "Edit passanger") );
        editMenu.addMenuOption( new MenuOption("M", "Edit meal") );

        mealMenu.addMenuOption( new MenuOption("K", "Kosher meal") );
        mealMenu.addMenuOption( new MenuOption("V", "Vegan meal") );
        mealMenu.addMenuOption( new MenuOption("F", "Fish meal") );
        mealMenu.addMenuOption( new MenuOption("C", "Chiken meal") );
        mealMenu.addMenuOption( new MenuOption("B", "Beef meal") );
        mealMenu.addMenuOption( new MenuOption("H", "Halal meal") );

        customSeatMenu.addMenuOption( new MenuOption("A", "Aisle only") );
        customSeatMenu.addMenuOption( new MenuOption("M", "Middle only") );
        customSeatMenu.addMenuOption( new MenuOption("W", "Window only") );
    }

    /**
     * Distributes the user's choice and initializes the required systems
     * @param option is the option from the menu that the user has requested be proccessed
     */
    private void initializeChoice(char option)
    {
        comm.clearBlueJTerminal();
        try
        {
            if ( option == 'l' )
            {
                if ( !planeOpen )
                {
                    load();

                }
                else
                {
                    boolean result = comm.yesNo("A plane file is already open.\nDo you want to save and close it first?");

                    if ( !result )
                    {
                        load();
                    }
                    else
                    {
                        save();
                        load();
                    }
                }
            }
            else if ( option == 's' )
            {
                if ( currentPlane != null && planeOpen )
                {
                    save();
                }
                else
                {
                    comm.println("No plane file to save");
                }
            }
            else if ( option == 'v' )
            {
                if ( planeOpen )
                {
                    printView();
                }
                else
                {
                    comm.println("No Plane file open");
                }
            }
            else if ( option == 'e' )
            {
                if ( currentPlane != null && planeOpen )
                {
                    edit();
                }
                else
                {
                    comm.println("No plane file to edit");
                }
            }
            else if ( option == 'a' )
            {
                if ( planeOpen )
                {
                    comm.print(currentPlane.printView());
                    makeRez();
                }
                else
                {
                    comm.println("No plane loaded");
                }
            }
            else if ( option == 'r' )
            {
                if ( planeOpen )
                {
                    remove();
                }
                else
                {
                    comm.println("No plane loaded");
                }
            }
            else if ( option == 'c' )
            {
                if ( planeOpen )
                {
                    customSeat();
                }
                else
                {
                    comm.println("No plane loaded");
                }
            }
            else if ( option == 'm' )
            {
                if ( planeOpen )
                {
                    comm.print(currentPlane.printFRez());
                    comm.pause();
                }
                else
                {
                    comm.println("No plane file loaded");
                }
            }
            else if ( option == 'g' )
            {
                if ( planeOpen && !report )
                {
                    currentPlane.writeRez();
                    comm.println("Report created successfully!");
                    report = true;
                }
                else
                {
                    if (report && planeOpen)
                    {
                        comm.println("report already printed");
                    }
                    else
                    {
                        comm.println("No plane file to write report for");
                    }
                }
            }
        }
        catch (Exception IOException)
        {
            comm.println("Error occured while writing file");
        }
    }

    /**
     * gathers the file name and distributes it to the plane object once a valid file is found
     */
    private void load()
    {
        boolean valid = false;

        while (!valid)
        {
            String fileName = comm.getInput_String("Please enter the name of the file: ");

            try
            {
                currentPlane = new Plane(fileName);
                valid = true;
                planeOpen = true;
            }
            catch (Exception FileNotFoundException)
            {
                comm.println("File not found");
            }
        }

        comm.println("File loaded successfully!");
        comm.pause();        
    }

    /**
     * controls the editing function, gathers vars that will be used once passed onto the plane object
     */
    private void edit()
    {
        comm.println(currentPlane.printRez());

        comm.println("Please enter row number: ");
        int row = comm.getInput_Int();
        comm.println("Please enter seat number: ");
        int seat = comm.getInput_Int();

        if ( row < currentPlane.getTotalRows() && currentPlane.validSeat(row, seat) )
        {
            choice = editMenu.getUserChoice();
            char change = choice.getOption().charAt(0);

            currentPlane.editRez(row, seat, change, comm, mealMenu);
        }
        else
        {
            comm.println("not a valid seat");
        }
        comm.println("Edit system completed");
        comm.pause();
    }

    /**
     * gatehrs a desired row and seat number to pass to the plane to create a reservation
     */
    private void makeRez()
    {    
        comm.println("Please enter row number: ");
        int row = comm.getInput_Int();
        comm.println("Please enter seat number: ");
        int seat = comm.getInput_Int();

        if ( row < currentPlane.getTotalRows() && currentPlane.validSeat(row, seat) )
        {
            currentPlane.makeReservation(row, seat, comm, mealMenu);
        }
        else
        {
            comm.println("not a valid seat");
        }
        comm.println("Reservation system complete");
        comm.pause();
    }

    /**
     * gathers one aditional option for narrowing choice for seat selection, then initializes standed makeRez()
     */
    private void customSeat()
    {
        choice = customSeatMenu.getUserChoice();
        char type = choice.getOption().charAt(0);

        comm.print(currentPlane.printCSeat(type));

        makeRez();
    }

    /**
     * gathers information that plane needs to find and asses and possibly remove a given seat 
     */
    private void remove()
    {
        comm.print(currentPlane.printRez());

        comm.println("Please enter row number: ");
        int row = comm.getInput_Int();
        comm.println("Please enter seat number: ");
        int seat = comm.getInput_Int();

        if ( row < currentPlane.getTotalRows() && currentPlane.validSeat(row, seat) )
        {
            currentPlane.removeReservation(row, seat, comm);
        }
        else
        {
            comm.println("not a valid seat");
        }
        comm.println("Removal system complete");
        comm.pause();
    }

    private void printView()
    {
        comm.println(currentPlane.printView());
        comm.pause();
    }

    /**
     * saves the chnages of the original file to a file of the same name, overwritting the previous contents
     */
    private void save()
    {
        try
        {
            currentPlane.save();
            planeOpen = false;
        }
        catch (Exception IOException)
        {
            comm.println("Error occured in saving file");
        }
        comm.println("File saved successfully!");
        comm.pause();
    }
}
