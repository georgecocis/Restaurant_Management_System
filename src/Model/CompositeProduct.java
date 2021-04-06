package Model;
import java.util.ArrayList;

public class CompositeProduct extends MenuItem{
    private int price;
    public ArrayList<MenuItem> composition;


    public int computePrice(){
        price = 0;
        for (MenuItem item : composition)
            price += item.getPrice();
        return price;
    }

    public ArrayList<MenuItem> getComposition(){
        return this.composition;
    }

    public void addComposition(MenuItem item){
        composition.add(item);
    }

    public void setComposition(){
        composition = new ArrayList<>();
    }
}
