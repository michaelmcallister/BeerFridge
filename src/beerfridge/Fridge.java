package beerfridge;
/** The Fridge Class determines how many beers it can hold and deals with
 ** adding and taking them. 
 ** @author Michael McAllister
 ** @version 5  
 **/
public class Fridge 
{
    //-------------STATIC DATA----------------------//
    private static final int MAXIMUM_BEERS = 100;
    private static final int MINIMUM_BEERS = 0;
    private static int currentBeers;
    private static final Fridge theFridge = new Fridge();
    //-------------END STATIC DATA-----------------//
    
    private int beersConsumed;
    private int beersSupplied;
    private FridgeGUI theGUI;
    
    //--------------START STATIC METHODS------------//
    /** Returns the fridge, so they can't change my fridge and ruin things.
     ** @return theFridge()
     **/
    public static Fridge getTheFridge()
    {
        return theFridge;
    }
    //--------------END STATIC METHODS--------------//
    
    /**Make sure no-one else can make another fridge, or it will cause problems 
     **/
    private Fridge()
    {}
    
    /** set up the GUI
     ** @param newFridgeGUI
     **/
    public void setGUI(FridgeGUI newFridgeGUI)
    {
        theGUI = newFridgeGUI;
    }
    
    /** get the max beers by this getter, so they can't change things and ruin me
     ** @return MAXIMUM_BEERS
     **/
    static int  getMaxBeers()
    {
        return MAXIMUM_BEERS;
    }
   
    /** Takes a String (ie. The Beer to add) and adds it
     ** @param name
     **/
    synchronized void add(String name)
    {
        while (currentBeers > MAXIMUM_BEERS - Supplier.getBeersInCarton())
        {
            try 
            {
                wait();
            }
            catch (InterruptedException ex) 
            { }
        }

        for (int i = 0 ; i < Supplier.getBeersInCarton() ; i++)
        {
             currentBeers++;
             beersSupplied++;
             theGUI.AddBeerLabel(currentBeers - 1);
        }

         theGUI.debugField.setText("Beers Added By: " + name);
         theGUI.currentBeerLabel.setText("Current Beers:" + currentBeers);
         theGUI.setTitle("Beers Supplied:" + beersSupplied + " " +
                       "| Beers Consumed:" + beersConsumed);
         notifyAll();    
    }
    
    /** Takes a string (ie. the Beers to take) and removes it
     ** @param name
     */
    synchronized void remove(String name)
    {
        while (currentBeers < 1)
        {
            try 
            {
                wait();
            } 
            catch (InterruptedException ex) 
            {}
        }
        theGUI.TakeBeerLabel(currentBeers -1);

        currentBeers--;
        beersConsumed++;
        
        theGUI.debugField.setText("Beer Drunk By: " + name);
        theGUI.currentBeerLabel.setText("Current Beers:" + currentBeers);
        theGUI.setTitle("Beers Supplied:" + beersSupplied + " " +
                      "| Beers Consumed:" + beersConsumed);
        notifyAll();
    }
    
   /** Returns the current beers in the fridge 
    ** @return currentBeers
    **/
    int getBeers()
    {
        return currentBeers;
    }
}
