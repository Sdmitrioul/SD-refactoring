package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.exception.DaoException;
import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProductDaoSQLite extends AbstractDao implements ProductDao {
    private static final FunctionWithException<ResultSet, Product, SQLException> getProductFromResultSet = resultSet -> {
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            int price = resultSet.getInt("price");
            return Product.of(name, price);
        }
        
        return null;
    };
    
    private static final String SQLITE_PREFIX = "jdbc:sqlite:";
    
    public ProductDaoSQLite(final String dbName) {
        super(SQLITE_PREFIX + dbName);
    }
    
    @Override
    public List<Product> getAllProducts() throws DaoException {
        return execute("SELECT * FROM PRODUCT", resultSet -> {
            final List<Product> products = new ArrayList<>();
            
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                products.add(Product.of(name, price));
            }
            
            return products;
        });
    }
    
    @Override
    public int getCount() throws DaoException {
        return execute("SELECT COUNT(*) FROM PRODUCT", resultSet -> {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            
            return 0;
        });
    }
    
    @Override
    public int executeStatement(final String sql) throws DaoException {
        return execute(sql);
    }
    
    @Override
    public long getSumCost() throws DaoException {
        return execute("SELECT SUM(price) FROM PRODUCT", resultSet -> {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            
            return 0L;
        });
    }
    
    @Override
    public Product getProductWithMinCost() throws DaoException {
        return execute("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", getProductFromResultSet);
    }
    
    @Override
    public Product getProductWithMaxCost() throws DaoException {
        return execute("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", getProductFromResultSet);
    }
    
    @Override
    public void addProduct(Product product) throws DaoException {
        addProduct(Collections.singleton(product));
    }
    
    @Override
    public void addProduct(final Collection<Product> products) throws DaoException {
        StringBuilder sql = new StringBuilder();
        
        sql.append("INSERT INTO PRODUCT (NAME, PRICE) VALUES ");
        
        products.forEach(product -> sql.append(product.toString("(\"", "\", ", "), ")));
        
        sql.deleteCharAt(sql.length() - 2);
        
        execute(sql.toString());
    }
    
    @Override
    public void createTableIfNotExist() throws DaoException {
        execute("CREATE TABLE IF NOT EXISTS PRODUCT " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "NAME TEXT NOT NULL, " +
                "PRICE INT NOT NULL)");
    }
}
