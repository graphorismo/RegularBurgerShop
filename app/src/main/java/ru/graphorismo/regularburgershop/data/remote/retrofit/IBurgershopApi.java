package ru.graphorismo.regularburgershop.data.remote.retrofit;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IBurgershopApi {

    @GET("titles")
    Observable<Response<List<String>>> requestTitles();

    @GET("products")
    Observable<Response<List<ProductResponse>>> requestProductsWithTitle(@Query("title") String title);
}
