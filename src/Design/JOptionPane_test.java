package Design;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class JOptionPane_test {

     public JOptionPane_test(String s,String a, int c)	// 
     {
//      ImageIcon icon = new ImageIcon("C:\\NCS\\workspace(java)\\13_GUI\\images\\apple.jpg");
        JOptionPane op = new JOptionPane(s, c, JOptionPane.DEFAULT_OPTION);
        JDialog dlog = op.createDialog(null,a);
                          changeColor(dlog.getComponents());
        dlog.setVisible(true);
     
       System.exit(0);
     }
     
     public void changeColor(Component[] comp)
     {
       for(int x = 0; x < comp.length; x++)
       {
         if(comp[x] instanceof Container) changeColor(((Container)comp[x]).getComponents());
         try
         {
           comp[x].setBackground(Color.WHITE);
         }
         catch(Exception e){}
       }
     }
}
