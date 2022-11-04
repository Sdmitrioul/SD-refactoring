package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.exception.DaoException;
import ru.akirakozov.sd.refactoring.model.Product;

import java.util.Collection;
import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts() throws DaoException;
    
    int getCount() throws DaoException;
    
    int executeStatement(String sql) throws DaoException;
    
    long getSumCost() throws DaoException;
    
    Product getProductWithMinCost() throws DaoException;
    
    Product getProductWithMaxCost() throws DaoException;
    
    void addProduct(Product product) throws DaoException;
    
    void addProduct(Collection<Product> products) throws DaoException;
    
    void createTableIfNotExist() throws DaoException;
}
