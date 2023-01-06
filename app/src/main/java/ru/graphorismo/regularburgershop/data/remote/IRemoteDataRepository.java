package ru.graphorismo.regularburgershop.data.remote;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import ru.graphorismo.regularburgershop.data.remote.retrofit.ProductResponse;

public interface IRemoteDataRepository {

    public Observable<Response<List<Integer>>> getIds();
    public Observable<Response<List<ProductResponse>>> getProductUnderId(Integer id);
}
