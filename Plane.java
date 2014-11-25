/**
 * Represents the plane that will be taking reservations, contains information based on 
 * reservations and locations.
 * @author Daniel Tetzlaff
 * @version 1.1
 * Last Modified: Mar  9, 2014 - Created (By Daniel Tetzlaff)
 *                Mar 10, 2014 - read in file info (By Daniel Tetzlaff)  
 *                Mar 11, 2014 - cleaned debris from massive self-destruction (By Daniel Tetzlaff)
 *                Mar 12, 2014 - completed main functionalities
 *                             - removal of reservations, adding, and custom seat selections
 *                             - improved formtting of all outputs to terminal and files (By Daniel Tetzlaff)
 */

import java.io.*;
import fileControl.*;
import userComunication.*;
public class Plane 
{
    private newFile plane;
    private int totalRows;
    private int totalSeat;
    private int[][] seats;
    private int[] mealNums = new int[6];
    private Reservation[] rez;
    private String[] rowPattern;
    private int totalRez = 0;

    public Plane(String fileName) throws FileNotFoundException, IOException
    {
        plane = new newFile(fileName);
        totalRows = plane.nextInt();
        setArrays();
    }
    
    public int getTotalRows()
    {
        return totalRows;
    }
    
    public boolean validSeat(int row, int seat)
    {
        int location = getLocation(seat, row);
        boolean result = false;
        
        if (seats[location][0] == row)
        {
            if (seats[location][1] == seat)
            {
                result = true;
            }
        }
        
        return result;
    }

    /**
     * Operates the process for making changes to a reservation
     * @param row is for the seat to be edited
     * @param seat is the specific seat being edited
     * @param chnage is a char determining which of the three possible changes will occur
     * @param comm is passed to allow communication for gathering vars
     * @param mealMenu is diplayed for ease of choices between meals
     */
    public void editRez(int row, int seat, char change, UserInteraction comm, Menu mealMenu)
    {
        int location = getLocation(seat, row);
        Reservation temp = rez[location];

        switch (change)
        {
            case 's':
            int newRow = comm.getInput_Int("enter desired row number ");
            int newSeat = comm.getInput_Int("enter desired seat number ");    

            if (checkSeat(newRow, newSeat))
            {
                temp.changeSeat(newSeat, newRow);
                rez[getLocation(newSeat, newRow)] = temp;
                rez[location] = null;
            }
            else
            {
                comm.println("cannot reserve already reserved seat");
            }
            break;
            case 'p':
            String name = comm.getInput_String("Enter full name: ");
            String age = comm.getInput_String("Enter age: ");

            temp.changePass(name, age);
            break;
            default:
            MenuOption choice = mealMenu.getUserChoice();
            String meal = getChoiceDescrip(choice.getOption().charAt(0));

            temp.changeMeal(meal);
        }
    }

    /**
     * Makes a new reservation object and places it in its corresponding array
     * @param row is the reservations row location
     * @param seat is the specific seat location
     * @param comm used to communicate for gathering details for reservation
     * @param mealMenu is used to allow user to pick a meal from the menu list 
     */
    public void makeReservation(int row, int seat, UserInteraction comm, Menu mealMenu)
    {
        if ( checkSeat(row, seat) )
        {
            String info = "";
            int location = getLocation(seat, row);

            info += row + "," + seat + ",";
            info += comm.getInput_String("Enter full name: ") + ",";
            info += comm.getInput_String("Enter age: ") + ",";

            MenuOption choice = mealMenu.getUserChoice();
            info += getChoiceDescrip(choice.getOption().charAt(0));

            rez[location] = new Reservation(info);
        }
        else
        {
            comm.println("cannot reserve already reserved seat");
        }
    }

    /**
     * finds location of requested seat, confirms choice and removes
     * @param row is seats row number, used for finding location
     * @param seat will be used to find the specific seat in question
     * @param comm is used to confirm the users choice to remove reservation
     */
    public void removeReservation(int row, int seat, UserInteraction comm)
    {
        int location = getLocation(seat, row);
        comm.println("This is the file that will be removed:\n" + rez[location].toString());

        boolean result = comm.yesNo("Are sure you want to remove this reservation?");
        if (result == true)
        {
            rez[location] = null;
        }
    }

    private String getChoiceDescrip(char choice)
    {
        String result = "";

        if (choice == 'k')
        {
            result = "Kosher";
        }
        else if (choice == 'v')
        {
            result = "Vegan";
        }
        else if (choice == 'f')
        {
            result = "Fish";
        }
        else if (choice == 'c')
        {
            result = "Chiken";
        }
        else if (choice == 'b')
        {
            result = "Beef";
        }
        else
        {
            result = "Halal";
        }

        return result;
    }

    /**
     * checks the specific seat for its availability
     * @param row is specific row to the seat, finds location
     * @param seat is the specific seat in question, finds location
     * @return the boolean results based on if a reservation exists for the seat in question
     */
    private boolean checkSeat(int row, int seat)
    {
        boolean result = false;

        int location = getLocation(seat, row);

        if (rez[location] == null)
        {
            result = true;
        }

        return result;
    }
    
    /**
     * sets arrays for the seats, seats are organized in a 2D-array and parallel to an array of reservations
     * coordnated with the seats
     */
    private void setArrays()
    {
        String rowPattern = "";

        for (int index = 0; index < totalRows; index++)
        {
            String currentRow = plane.nextLine();
            rowPattern += currentRow + ",";
        }

        totalSeat = rowPattern.length() - totalRows;
        rez = new Reservation[totalSeat];
        seats = new int[totalSeat][4];

        fillSeats(rowPattern);
        fillRez();
    }

    /**
     * fills seats with their information based on the lines in the file 
     * @param allRowPattern is a string containing all row patterns seperated by commas
     */
    private void fillSeats(String allRowPattern)
    {
        rowPattern = separate(allRowPattern);
        int curSeat = 0;

        for ( int arrayIndex = 0; arrayIndex < totalRows; arrayIndex++ )
        {
            for ( int seat = 0; seat < rowPattern[arrayIndex].length(); seat++ , curSeat++ )
            {
                seats[curSeat][0] = arrayIndex + 1;
                seats[curSeat][1] = seat + 1;

                char locale = rowPattern[arrayIndex].charAt(seat);
                seats[curSeat][2] = setValFromChar(locale);
                seats[curSeat][3] = curSeat;

                // next line for debugging, comment out for regular use
                // System.out.println("seat " + seats[curSeat][1] + " row " + seats[curSeat][0] + " type " + seats[curSeat][2] + " / " + locale);
            }
        }

        //Debugging tool
        //printarray();
    }

    /**
     * Method used for debugging purposes to see contents of arrays
     */
    private void printarray()
    {
        for (int r=0;r<seats.length;r++)
        {
            for(int c=0;c<seats[r].length;c++)
            {
                System.out.print(seats[r][c]);
            }
            System.out.println();
        }

        for(int r=0;r<rez.length;r++)
        {
            System.out.println(rez[r]);
        }
    }

    /**
     * sets reservations based on the contents of a file
     */
    private void fillRez()
    {
        while ( plane.hasNext() )
        {
            String info = "";
            info = plane.nextLine();
            Reservation temp = new Reservation(info);

            int seat = temp.getSeat();
            int row = temp.getRow();
            int location = getLocation(seat, row);

            rez[location] = temp;
            // Debugging tool
            // System.out.println("reservation set: " + info);
        }
    }

    /**
     * keeps a tally of the amount of each type of meal, used with the report
     * @param meal is the description for a meal used to match where the tally should go
     */
    private void storeMeal(String meal)
    {
        if (meal.equals("Kosher"))
        {
            mealNums[0]++;
        }
        else if (meal.equals("Vegan"))
        {
            mealNums[1]++;
        }
        else if (meal.equals("Fish"))
        {
            mealNums[2]++;
        }
        else if (meal.equals("Chiken"))
        {
            mealNums[3]++;
        }
        else if (meal.equals("Beef"))
        {
            mealNums[4]++;
        }
        else
        {
            mealNums[5]++;
        }
    }

    /**
     * based on the row and seat finds the true location of a seat, commoly used to coordinate
     * with the array of reservations
     * @param row specific row in question
     * @param seat is specific seat in question
     */
    private int getLocation(int seat, int row)
    {
        boolean end = false;
        int index = 0;

        for (int rowDex = 0; rowDex < totalSeat && !end; rowDex++)
        {
            if (seats[rowDex][0] == row)
            {
                while ( rowDex < totalSeat && !end )
                {
                    if (seats[rowDex][1] == seat && seats[rowDex][0] == row)
                    {
                        index = seats[rowDex][3];
                        end = true;
                    }
                    rowDex++;
                }
            }
        }

        return index;
    }
    
    /**
     * prints specific list of available seats in the custom seat search
     * @param type determines what types of seats will be displayed
     */
    public String printCSeat(char type)
    {
        int typeVal = setValFromChar(type);
        String result = String.format("|%5s| %5s| %10s| %n", "Row", "Seat", "Type");
        
        for ( int index = 0; index < totalSeat; index++ )
        {
            if ( seats[index][2] == typeVal && rez[index] == null )
            {
                result += String.format("|%5s| %5s| %10s| %n", seats[index][0], seats[index][1], fullType(type));
            }
        }
        
        return result;
    }
    
    private String fullType(char type)
    {
        String result = "";
        
        if ( type == 'a' )
        {
            result = "Aisle";
        }
        else if ( type == 'm' )
        {
            result = "Middle";
        }
        else
        {
            result = "Window";
        }
        
        return result;
    }

    private int setValFromChar(char locale)
    {
        int value;

        if ( locale == 'A' || locale == 'a')
        {
            value = 1;
        }
        else if ( locale == 'M' || locale == 'm' )
        {
            value = 2;
        }
        else
        {
            value = 3;
        }

        return value;
    }

    private char charFromInt(int num)
    {
        char result;

        if ( num == 1 )
        {
            result = 'A';
        }
        else if ( num == 2 )
        {
            result = 'M';
        }
        else
        {
            result = 'W';
        }

        return result;
    }

    public void save() throws IOException
    {
        plane.writeSave(rowPattern, totalRows, printRez());
    }

    private String[] separate(String original)
    {
        return original.split(",");
    }

    public void writeRez() throws IOException
    {
        String temp = printMealNums();
        plane.writeLine("Total number of Reservations: " + totalRez + "\n");
        plane.writeLine(temp);
        plane.writeLine(printFRez());
        plane.close();
    }

    /** 
     * sets and stores the totals for the number of each meal type for use in report
     */
    private void setMealNums()
    {
        for (int index = 0; index < rez.length; index++)
        {
            if (rez[index] != null)
            {
                String meal = rez[index].getMeal();

                storeMeal(meal);
                totalRez++;
            }
        }
    }

    /**
     * prints a formatted chart showing the number of each type of meal
     */
    private String printMealNums()
    {
        setMealNums();
        String result = String.format("%10s %n", "Meal Numbers:");

        for (int index = 0; index < mealNums.length; index++)
        {
            switch (index)
            {
                case 0:
                result += String.format("%7s- %3s %n", "Kosher", mealNums[index]);
                break;
                case 1:
                result += String.format("%7s- %3s %n", "Vegan", mealNums[index]);
                break;
                case 2:
                result += String.format("%7s- %3s %n", "Fish", mealNums[index]);
                break;
                case 3:
                result += String.format("%7s- %3s %n", "Chiken", mealNums[index]);
                break;
                case 4:
                result += String.format("%7s- %3s %n", "Beef", mealNums[index]);
                break;
                default:
                result += String.format("%7s- %3s %n", "Halal", mealNums[index]);
            }
        }

        return result;
    }

    /**
     * prints the current status of every seat in the plane, prints formatted based on seat row and number
     */
    public String printView()
    {
        String result = "";
        int curRow = 1;
        int index = 0;
        int test = 0;

        while ( index < totalSeat)
        {
            result += "Row " + String.format("%2d", curRow) + ":";

            do 
            {
                result += " [" + String.format("%2d", seats[index][1]) + "," + charFromInt(seats[index][2]) + ",";

                if (rez[seats[index][3]] == null)
                {
                    result += "+]";
                }
                else
                {
                    result += "*]";
                }

                index++;
                if (index == totalSeat)
                {
                    test++;
                }
                else
                {
                    test = seats[index][0];
                }
            }
            while ( index < totalSeat && curRow == test);

            curRow++;
            result += "\n";
        }

        return result;
    }

    /**
     * prints an unformatted version of reservation contents, used when saving
     */
    public String printRez()
    {
        String result = "";

        for (int index = 0; index < totalSeat; index++ )
        {
            if ( rez[index] != null )
            {
                result += rez[index].toString() + "\n";
            }
        }

        return result;
    }

    /**
     * prints a formatted chart showing all reservation and their containing information
     */
    public String printFRez()
    {
        String result = String.format("%5s| %5s| %25s| %5s| %10s| %n", "Row", "Seat", "PassangerName", "Age", "MealChoice");

        for ( int index = 0; index < totalSeat; index++ )
        {
            if ( rez[index] != null )
            {
                Reservation r = rez[index];
                result += String.format("%5s| %5s| %25s| %5s| %10s| %n", r.getRow(), r.getSeat(), r.getName(), r.getAge(), r.getMeal());
            }
        }

        return result;
    }
}
