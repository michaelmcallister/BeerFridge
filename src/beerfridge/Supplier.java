package beerfridge;

import javax.swing.JLabel;
/** @author Michael McAllister
 ** @version 5
 ** This class contains the getters and the setters for the Suppliers.
 ** it sets the constraints for the suppliers aswell, such as maximum number of
 ** beers in one carton, and the max    
 **/
public class Supplier extends JLabel implements Runnable
{
    //--------START STATIC DATA-------------------------//
    private static final int MAXIMUM_NUMBER_CARTONS = 5;
    private static final int BEERS_IN_CARTON = 25;
    //----------END STATIC DATA-----------------------//
    
    Fridge theFridge;
    private  int currentNumberCartons;
    
    //--------START STATIC METHODS--------------------//
    /** Get the beers in the carton, use the setters so the value isn't changed
    ** @return
    **/
    static int getBeersInCarton()
    {
        return BEERS_IN_CARTON;
    }
    //--------END STATIC METHODS---------------------//
    
    Supplier(String newName, Fridge newFridge)
    {
        setName(newName);
        theFridge = newFridge;
    }
    
    /** Add all the beers, (or as much as possible) then sleep for a random time
     ** then notifiy everybody that there is some beers in this bitch  
     **/
    public void run()
    {
        while (currentNumberCartons++ < MAXIMUM_NUMBER_CARTONS)
        {
            try 
            {
                theFridge.add(getName());
                setText(getName()+" Cartons: " + currentNumberCartons );
                int randomNumber = (int)( Math.random() * 2000);
                Thread.sleep(randomNumber);
            } 
            catch (InterruptedException ex)
            { }
        }
    }
}//END OF SUPPLIER CLASS
