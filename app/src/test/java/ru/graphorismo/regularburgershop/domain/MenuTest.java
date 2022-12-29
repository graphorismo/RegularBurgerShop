package ru.graphorismo.regularburgershop.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import ru.graphorismo.regularburgershop.data.Menu;
import ru.graphorismo.regularburgershop.data.Product;

public class MenuTest {

    @Test
    public void addTitleAddingTitle(){
        Menu menu = new Menu();
        menu.addTitle("test");
        Assert.assertTrue(menu.getProductsUnderTitle("test").size() == 0);
    }

    @Test
    public void noTitleReturnNull(){
        Menu menu = new Menu();
        Assert.assertNull(menu.getProductsUnderTitle("test"));
    }

    @Test
    public void addProductAddingProduct(){
        Menu menu = new Menu();
        menu.addProductUnderTitle(new Product("product", 1), "test");
        ArrayList<Product> products = menu.getProductsUnderTitle("test");
        Assert.assertTrue(products.get(0).equals(new Product("product", 1)));

    }

    @Test
    public void addProductsAddingProducts(){
        Menu menu = new Menu();
        ArrayList<Product> productsIn = new ArrayList<>();
        productsIn.add(new Product("product1", 1));
        productsIn.add(new Product("product2", 2));
        menu.addManyProductsUnderTitle(productsIn, "test");
        ArrayList<Product> productsOut = menu.getProductsUnderTitle("test");
        Assert.assertTrue(productsOut.get(0).equals(new Product("product1", 1)));
        Assert.assertTrue(productsOut.get(1).equals(new Product("product2", 2)));
    }
}
