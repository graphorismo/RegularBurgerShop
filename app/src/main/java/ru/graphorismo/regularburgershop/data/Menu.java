package ru.graphorismo.regularburgershop.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Menu {

    private HashMap<String, ArrayList<Product>> titleToProducts = new HashMap<>();

    public void addTitle(String title){
        titleToProducts.put(title, new ArrayList<>());
    }

    public void addProductUnderTitle(Product product, String title){
        if( ! titleToProducts.containsKey(title) ) addTitle(title);
        ArrayList<Product> list = titleToProducts.get(title);
        list.add(product);
    }

    public void addManyProductsUnderTitle(Collection<Product> products, String title){
        if( ! titleToProducts.containsKey(title) ) addTitle(title);
        ArrayList<Product> list = titleToProducts.get(title);
        list.addAll(products);
    }

    public ArrayList<Product> getProductsUnderTitle(String title){
        return titleToProducts.get(title);
    }

    public Set<String> getTitles() {return titleToProducts.keySet();}
}