package ru.graphorismo.regularburgershop.data.remote;

import java.util.ArrayList;
import java.util.List;

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
    private final ConverterProductResponseToProduct converterProductResponseToProduct;

    public RemoteDataRepository(IBurgershopApi api,
                                ConverterProductResponseToProduct converterProductResponseToProduct)
    {
        this.api = api;
        this.converterProductResponseToProduct = converterProductResponseToProduct;
    }

    @Override
    public List<String> getTitles(){
        Response<List<String>> response = api.requestTitles();

        if( response == null )
            throw new NullNetworkResponseException("Receive a null network response.");
        if( ! response.isSuccessful() )
            throw new UnsuccessfulResponseException("Receive an unsuccessful network response.");
        if( response.body() == null)
            throw new EmptyResponseException("Receive an empty network response.");

        return response.body();
    }

    @Override
    public List<Product> getProductsWithTitle(String title) {
        Response<List<ProductResponse>> response = api.requestProductsWithTitle(title);

        if( response == null )
            throw new NullNetworkResponseException("Receive a null network response.");
        if( ! response.isSuccessful() )
            throw new UnsuccessfulResponseException("Receive an unsuccessful network response.");
        if( response.body() == null)
            throw new EmptyResponseException("Receive an empty network response.");

        List<Product> products = new ArrayList<>();
        List<ProductResponse> responseProducts = response.body();

        for (ProductResponse responseProduct: responseProducts) {
            products.add(converterProductResponseToProduct.convertProductResponseToProduct(responseProduct));
        }

        return products;
    }
}
