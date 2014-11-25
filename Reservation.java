/**
 * Holds informations for passangers/seats for a reservation
 * @author Daniel Tetzlaff
 * @version 1.0
 * Last Modified: Mar 10, 2014 - Created (By Daniel Tetzlaff)
 *                Mar 11, 2014 - Editing methods (By Daniel Tetzlaff)
 *                Mar 12, 2014 - Completed adding get methods (By Daniel Tetzlaff)
 */

import userComunication.*;
public class Reservation
{ 
    final private int UNI_CONVERT = 48;
    private boolean status = true;
    private int absoluteSeatNum;
    private int rowNum;
    private int seatNum;
    private String passName;
    private String age;
    private String meal;

    public Reservation (String info)
    {
        String[] details = separate(info);
        setAllVars(details);
        status = false;
    }
    
    public String getName()
    {
        return passName;
    }
    
    public String getAge()
    {
        return age;
    }
    
    public String getMeal()
    {
        return meal;
    }
    
    public int getRow()
    {
        return rowNum;
    }
    
    public int getSeat()
    {
        return seatNum;
    }
    
    public void changeSeat(int seat, int row)
    {
        rowNum = row;
        seatNum = seat;
    }
    
    public void changePass(String name, String age)
    {
        passName = name;
        this.age = age;
    }
    
    public void changeMeal(String meal)
    {
        this.meal = meal;
    }

    private void setAllVars(String[] details)
    {
        rowNum = convertToInt(details[0]);
        seatNum = convertToInt(details[1]);
        passName = details[2];
        age = details[3];
        meal = details[4];
    }
    
    private int convertToInt(String string)
    {
        char temp = string.charAt(0);
        return (int)(temp) - UNI_CONVERT;
    }

    private String[] separate(String original)
    {
        return original.split(",");
    }
    
    public String toString()
    {
        String string = "";
        
        string += rowNum + ",";
        string += seatNum + ",";
        string += passName + ",";
        string += age + ",";
        string += meal;
        
        return string;
    }
}
