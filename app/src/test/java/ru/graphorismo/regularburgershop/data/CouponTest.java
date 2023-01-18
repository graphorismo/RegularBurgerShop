package ru.graphorismo.regularburgershop.data;

import org.junit.Assert;
import org.junit.Test;

public class CouponTest {

    @Test
    public void whenCompareDummyCouponsThenEqual(){
        Assert.assertEquals(
                new Coupon(),
                new Coupon()
        );
    }

    @Test
    public void whenFieldsEqualThenEqual(){
        Assert.assertEquals(
                new Coupon("name", new Product(), 0),
                new Coupon("name", new Product(), 0)
        );
    }

    @Test
    public void whenFieldsNotEqualThenNotEqual(){
        Assert.assertNotEquals(
                new Coupon("name", new Product(), 0),
                new Coupon("name1", new Product(), 0)
        );
        Assert.assertNotEquals(
                new Coupon("name", new Product("test", "test", 0, ""), 0),
                new Coupon("name1", new Product(), 0)
        );
        Assert.assertNotEquals(
                new Coupon("name", new Product(), 1),
                new Coupon("name", new Product(), 0)
        );
    }
}
