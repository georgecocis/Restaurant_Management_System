package Start;

import Control.Restaurant;
import View.MainGUI;

public class Main {
    public static void main(String args[]){
        MainGUI mgui = new MainGUI();
        mgui.setVisible(true);
        Restaurant cont = new Restaurant(mgui);
    }
}
