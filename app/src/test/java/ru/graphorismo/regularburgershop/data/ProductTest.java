package ru.graphorismo.regularburgershop.data;

import org.junit.Assert;
import org.junit.Test;

public class ProductTest {

    @Test
    public void whenCreateWithNegativePriceThenRuntimeException(){
        Assert.assertThrows(RuntimeException.class, ()->{
            Product product = new Product("test", "test", -1, "");
        });
    }

    @Test
    public void whenCreateWithEmptyNameThenRuntimeException(){
        Assert.assertThrows(RuntimeException.class, ()->{
            Product product = new Product("title", "", 0, "");
        });
    }

    @Test
    public void whenCompareDummyProductsThenEqual(){
        Assert.assertEquals(new Product(), new Product());
    }

    @Test
    public void whenFieldsEqualThenProductsEqual(){
        Assert.assertEquals(
                new Product("", "name1", 0, ""),
                new Product("", "name1", 0, ""));
    }

    @Test
    public void whenFieldsNotEqualThenProductsNotEqual(){
        Assert.assertNotEquals(
                new Product("", "name", 0, ""),
                new Product("", "name1", 0, ""));
        Assert.assertNotEquals(
                new Product("", "name", 0, ""),
                new Product("title", "name", 0, ""));
        Assert.assertNotEquals(
                new Product("", "name", 0, ""),
                new Product("", "name", 1, ""));
        Assert.assertNotEquals(
                new Product("", "name", 0, ""),
                new Product("", "name", 0, "url"));
    }


}
