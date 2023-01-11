package ru.graphorismo.regularburgershop.data.remote.retrofit;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IBurgershopApi {

    @GET("products/ids")
    Observable<Response<List<Integer>>> requestIds();

    @GET("product")
    Observable<Response<List<ProductResponse>>> requestProductUnderId(@Query("id") Integer id);
}
