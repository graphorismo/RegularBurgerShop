package ru.graphorismo.regularburgershop.domain;

import org.junit.Test;
import org.junit.Assert;
import ru.graphorismo.regularburgershop.data.Product;

public class ProductTest {

    @Test
    public void getNameEqualsConstructorName(){
        String name = "test";
        Product product = new Product(name, 100);
        Assert.assertEquals(product.getName(), name);
    }

    @Test
    public void constructorNameCantBeEmpty(){
        Assert.assertThrows(RuntimeException.class, () -> {
           new Product("",1);
        });
    }

    @Test
    public void getPriceEqualsConstructorPrice(){
        Integer price = 100;
        Product product = new Product("test", price);
        Assert.assertEquals(product.getPrice(),price);
    }

    @Test
    public void constructorPriceCantBeNegative(){
        Assert.assertThrows(RuntimeException.class, () -> {
            new Product("name",-1);
        });
    }

    @Test
    public void differentProductsAreNotEqual(){
        Assert.assertNotEquals(
                new Product("name1",1),
                new Product("name2", 2));
    }

    @Test
    public void sameProductsAreEqual(){
        Assert.assertEquals(
                new Product("name",1),
                new Product("name", 1));
    }

}
