package beerfridge;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
       
/** @version 5
 ** @author  Michael McAllister
 ** This class creates the GUI for the Fridge, it defines the size and layouts
 ** used. 
 **/
public class FridgeGUI extends JFrame
{
    //--------------START STATIC DATA--------------//
    private static final int GUI_LENGTH = 800;
    private static final int GUI_HEIGHT = 700;
    //--------------END STATIC DATA---------------//
    
    JLabel[] beerLabels       = new JLabel [Fridge.getMaxBeers()];

    JPanel   beerPanel        = new JPanel (new GridLayout (10,10));
    JPanel   supplierPanel    = new JPanel (new GridLayout (10,1));
    JPanel   drinkerPanel     = new JPanel (new GridLayout (10,1));
    JTextField debugField     = new JTextField();   
    
    //set the icons for the beers
     
    Icon     FULL             = new ImageIcon("res/guinness.gif");
    Icon     EMPTY            = new ImageIcon("res/guinnessempty.gif");

    //top Buttons and panel for adding new suppliers/drinkers
    JPanel   topPanel         = new JPanel();
    JPanel   bottomPanel      = new JPanel();
    
    //top and bottom layouts
    BorderLayout topLayout    = new BorderLayout();
    BorderLayout bottomLayout = new BorderLayout();
   
    //add 'Add Supplier and Drinker Buttons and Collapsable side panels'
    JButton supplierButton    = new JButton("Add Supplier");
    JButton drinkerButton     = new JButton("Add Drinker");
    JButton supplierCollapse  = new JButton("<");
    JButton drinkerCollapse   = new JButton(">");
    
    //Current Beers title
    JLabel currentBeerLabel   = new JLabel("Current Beers:",JLabel.CENTER);
    
    /** The FridgeGUI() constructor sets up the borderlayout for the form
     ** Adds all the action listeners and sets the size. 
     **/
    FridgeGUI()
    {
        //add all the Beer Labels (100 of them)
        for (int i = 0; i < beerLabels.length ; i++ )
        {
            beerLabels[i] = new JLabel();
            beerLabels[i].setIcon(EMPTY);
            beerLabels[i].setOpaque(true);
            beerLabels[i].setBackground(Color.white);
            beerPanel.add(beerLabels[i]);
        }
        
         //add all the Panels to their respective borders.
         add(supplierPanel, BorderLayout.WEST);
         add(drinkerPanel, BorderLayout.EAST);
	 add(beerPanel, BorderLayout.CENTER);
         add(topPanel,BorderLayout.NORTH);
         add(bottomPanel,BorderLayout.SOUTH);
         
         //set the topPanel & bottom layout to a new borderlayout
         topPanel.setLayout(topLayout);
         bottomPanel.setLayout(bottomLayout);
         
         //add the supplierButton to the west of the borderlayout
         topPanel.add(supplierButton,BorderLayout.WEST);
         bottomPanel.add(supplierCollapse,BorderLayout.WEST);

         //add the drinkerButton to the east of the borderlayout
         topPanel.add(drinkerButton, BorderLayout.EAST);
         bottomPanel.add(drinkerCollapse, BorderLayout.EAST);
        
         
         //centre the label (not the text though)
         topPanel.add(currentBeerLabel,BorderLayout.CENTER);
         
         //Add action listeners to button.
         supplierButton.addActionListener(new addNewSupplier());
         supplierCollapse.addActionListener(new collapseSupplier());
         drinkerButton.addActionListener(new addNewDrinker());
         drinkerCollapse.addActionListener(new collapseDrinker());
         
         bottomPanel.add(debugField,BorderLayout.CENTER);
         
         //close when its crossed off
         setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
         //set the size and visibility
	 setSize(GUI_LENGTH,GUI_HEIGHT);
	 setVisible(true);
    }//END OF FRIDGEGUI() CONSTRUCTOR
    
    /** adds a new drinker to the drinkerPanel (the Panel on the right hand side) 
     ** andd substantiates a new drinker 
     **/
    public void addDrinker(Drinker newDrinker)
    {
        drinkerPanel.add(newDrinker);
    }
   
    /** @param newSupplier
     ** adds a new supplier to the supplierPanel (the panel on the left hand side)
     ** and substantiates a new supplier
     **/
    public void addSupplier(Supplier newSupplier)
    {
        supplierPanel.add(newSupplier);
    }
   
    /** @param Index
    ** takes an index in the beerLabel array (of a 100) and sets the icon (the picture)
    ** to the full guinness picture 
    **/
    public void AddBeerLabel(int Index)
     {
        beerLabels[Index].setIcon(FULL);
     }
   
    /** @param Index
     ** takes an index in the beerLabel array and sets the icon (the picture)
     ** to the empty guinness picture 
     **/
    public void TakeBeerLabel(int Index)
    {
        beerLabels[Index].setIcon(EMPTY);
    }
    
    //---------------------INNER CLASSES------------------------//
    /** actionlistener for adding a new supplier
     ** checks of the supplier name is not null and the length is greater than nothing
     ** if everything is dandy, make a new thread and start it.    
     **/
    class addNewSupplier implements ActionListener
    {
        public void actionPerformed(ActionEvent ev)
        {
            String newSupplierName = JOptionPane.showInputDialog("Enter Name:");

            if((newSupplierName != null)&& (newSupplierName.length() > 0))
            {
                Supplier theSupplier = new Supplier(newSupplierName,Fridge.getTheFridge());
                addSupplier(theSupplier);
                Thread theThread = new Thread();
                theThread = new Thread(theSupplier);
                theThread.start();
            }
        }
    }
    
    /** actionlistener for adding a new drinker
     ** checks of the drinker name is not null and the length is greater than nothing
     ** if everything is fine again, make a new thread and start it
     **/
    class addNewDrinker implements ActionListener
    {
        public void actionPerformed(ActionEvent ev)
        {
           String newDrinkerName = JOptionPane.showInputDialog("Enter Name:");
           
           if((newDrinkerName != null)&& (newDrinkerName.length() > 0))
           {
               Drinker theDrinker = new Drinker(newDrinkerName,Fridge.getTheFridge());
               addDrinker(theDrinker);
               Thread theThread = new Thread();
               theThread =  new Thread(theDrinker);
               theThread.start();
           }
        }
    }
    
    /** checks if the drinker panel is visible, if it is collapse it
     ** if isn't show it. 
     **/
    class collapseDrinker implements ActionListener
    {
        public void actionPerformed(ActionEvent ev)
        {
            if (drinkerPanel.isVisible() == true)
            {
                drinkerPanel.setVisible(false);
                drinkerCollapse.setText("<");
            } else
            {
                drinkerPanel.setVisible(true);
                drinkerCollapse.setText(">");
            }
        }
    }
    
    /** checks if the supplier panel is visible, if it is collapse it
     ** if isn't show it. 
     **/
    class collapseSupplier implements ActionListener
    {
        public void actionPerformed(ActionEvent ev)
        {
            if (supplierPanel.isVisible() == true)
            {
                supplierPanel.setVisible(false);
                supplierCollapse.setText(">");
            } else
            {
              supplierPanel.setVisible(true);
              supplierCollapse.setText("<");
            }
        }
    }
}//END OF FRIDGEGUI CLASS
