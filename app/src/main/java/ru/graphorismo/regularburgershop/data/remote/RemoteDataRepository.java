package ru.graphorismo.regularburgershop.data.remote;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import ru.graphorismo.regularburgershop.data.remote.retrofit.ConverterProductResponseToProduct;
import ru.graphorismo.regularburgershop.data.remote.retrofit.IBurgershopApi;
import ru.graphorismo.regularburgershop.data.remote.retrofit.ProductResponse;

public class RemoteDataRepository implements  IRemoteDataRepository{

    private final IBurgershopApi api;

    @Inject
    public RemoteDataRepository(IBurgershopApi api)
    {
        this.api = api;
    }

    @Override
    public Observable<Response<List<Integer>>> getIds(){
        return api.requestIds();
    }

    @Override
    public Observable<Response<List<ProductResponse>>> getProductUnderId(Integer id) {
        return api.requestProductUnderId(id);
    }
}
