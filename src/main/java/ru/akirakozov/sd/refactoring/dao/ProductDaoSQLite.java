package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.model.Product;

import java.util.List;

public class ProductDaoSQLite extends AbstractDao implements ProductDao {
    public ProductDaoSQLite(final String dbName) {
        super(dbName);
    }
    
    @Override
    public List<Product> getAllProducts() {
        return null;
    }
    
    @Override
    public int getCount() {
        return 0;
    }
    
    @Override
    public long getSumCost() {
        return 0;
    }
    
    @Override
    public Product getProductWithMinCost() {
        return null;
    }
    
    @Override
    public Product getProductWithMaxCost() {
        return null;
    }
    
    @Override
    public void addProduct(Product product) {
    }
}
