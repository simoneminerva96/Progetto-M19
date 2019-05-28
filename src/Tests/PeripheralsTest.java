package Tests;

import main.Manager.Manager;
import main.Peripherals.Columns.EntryColumn;
import main.Peripherals.Columns.EntryColumnGUI;

import java.awt.*;

public class PeripheralsTest {
    public static void main(String[] arg){
       /*ExitColumn exit = new ExitColumn("IT1224LK");
        EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run() {
                new ExitColumnGUI(exit);
            }
        });*/

        Manager man = new Manager();
        EntryColumn entry = new EntryColumn("IT1224LK", man);
        EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run() {
                new EntryColumnGUI(entry);
            }
        });
    }
}