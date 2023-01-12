package ru.graphorismo.regularburgershop.data.remote.retrofit;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IBurgershopApi {

    @GET("products/ids")
    Observable<Response<List<Integer>>> requestProductsIds();

    @GET("product")
    Observable<Response<List<ProductResponse>>> requestProductUnderId(@Query("id") Integer id);

    @GET("coupons/ids")
    Observable<Response<List<Integer>>> requestCouponsIds();

    @GET("coupon")
    Observable<Response<List<CouponResponse>>> requestCouponUnderId(@Query("id") Integer id);

}
