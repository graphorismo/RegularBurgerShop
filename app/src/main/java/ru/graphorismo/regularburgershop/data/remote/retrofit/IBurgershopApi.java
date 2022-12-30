package ru.graphorismo.regularburgershop.data.remote.retrofit;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IBurgershopApi {

    @GET("titles")
    Response<List<String>> requestTitles();

    @GET("products")
    Response<List<ProductResponse>> requestProductsWithTitle(@Query("title") String title);
}
