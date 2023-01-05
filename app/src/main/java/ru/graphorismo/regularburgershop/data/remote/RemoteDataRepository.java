package ru.graphorismo.regularburgershop.data.remote;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.remote.retrofit.ConverterProductResponseToProduct;
import ru.graphorismo.regularburgershop.data.remote.retrofit.IBurgershopApi;
import ru.graphorismo.regularburgershop.data.remote.retrofit.ProductResponse;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.EmptyResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.NullNetworkResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.UnsuccessfulResponseException;

public class RemoteDataRepository implements  IRemoteDataRepository{

    private final IBurgershopApi api;
    private final ConverterProductResponseToProduct converterProductResponseToProduct =
            new ConverterProductResponseToProduct();

    @Inject
    public RemoteDataRepository(IBurgershopApi api)
    {
        this.api = api;
    }

    @Override
    public Observable<Response<List<String>>> getTitles(){
        return api.requestTitles();
    }

    @Override
    public Observable<Response<List<ProductResponse>>> getProductsWithTitle(String title) {
        return api.requestProductsWithTitle(title);
    }
}
