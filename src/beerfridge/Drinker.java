package beerfridge;

import javax.swing.JLabel;

/** This class sets the properties (max drinks someone can drink) of the drinkers
 ** And sorts out all the drinking to be done
 ** @Version 5
 ** @author Michael McAllister
 **/
public class Drinker extends JLabel implements Runnable
{
    Fridge theFridge;
    private int maxDrinks = 15;
    private int currentDrinks = 0;
    
    /** set the Drinkers name and tell it which fridge to use
     ** @param newName
     ** @param newFridge
     **/
    Drinker (String newName, Fridge newFridge)
    {
        setName(newName);
        theFridge = newFridge;
    }
    
    /** Drink all the availabe drinks (up to 25)
     **/
    public void run()
    {

        while (currentDrinks++ <maxDrinks)
        {
            try 
            {
                int randomNumber = (int)( Math.random() *1500);
                theFridge.remove(getName());
                setText(getName()+" Drinks: " + currentDrinks );
                Thread.sleep(randomNumber);
            }
            catch (InterruptedException ex) 
            { }
        }
    }
}
