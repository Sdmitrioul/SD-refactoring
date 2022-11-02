package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts();
    int getCount();
    long getSumCost();
    Product getProductWithMinCost();
    Product getProductWithMaxCost();
    void addProduct();
}
