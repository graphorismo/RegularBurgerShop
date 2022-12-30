package ru.graphorismo.regularburgershop.data.remote;

import java.util.List;

import ru.graphorismo.regularburgershop.data.Product;

public interface IRemoteDataRepository {

    public List<String> getTitles();
    public List<Product> getProductsWithTitle(String title);
}
