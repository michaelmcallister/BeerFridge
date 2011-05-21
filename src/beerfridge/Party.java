package beerfridge;
 /** This Party class sets up the GUI, and also checks/gets the input parameters
  ** If any at all. 
  ** @author Michael McAllister
  ** @version 5
  **/
public class Party
{
     public static void main (String[] args)
    {
        Fridge theFridge =  Fridge.getTheFridge();
        FridgeGUI theGUI = new FridgeGUI();
        theFridge.setGUI(theGUI);
        
        Supplier[] theSupplier = new Supplier[args.length];
        Drinker[] theDrinker = new Drinker[args.length];
        Thread[] theThreads = new Thread[args.length];
       
        for (int i = 0, f = 0 ; i < args.length ; i +=2, f++)
        {
            if (args[i].equals("Supplier"))
            {
                theSupplier[f] = new Supplier(args[i+1],theFridge);
                theGUI.addSupplier(theSupplier[f]);
                theThreads[f] = new Thread(theSupplier[f]);
            }
            else
            {
                if (args[i].equals("Drinker"))
                {
                    theDrinker[f] = new Drinker(args[i+1],theFridge);
                    theGUI.addDrinker(theDrinker[f]);
                    theThreads[f] = new Thread(theDrinker[f]);
                }
            }
        }
        
    
        for (int i = 0; i < args.length /2 ; i ++)
        {
            theThreads[i].start(); //start the threads
        }
    }
} //END OF PARTY CLASS


