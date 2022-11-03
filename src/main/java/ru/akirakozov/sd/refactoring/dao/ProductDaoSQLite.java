package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.exception.DaoException;
import ru.akirakozov.sd.refactoring.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDaoSQLite extends AbstractDao implements ProductDao {
    public ProductDaoSQLite(final String dbName) {
        super(dbName);
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
    public long getSumCost() throws DaoException {
        return execute("SELECT SUM(price) FROM PRODUCT", resultSet -> {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            
            return 0L;
        });
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
    public void addProduct(Product product) throws DaoException {
        StringBuilder sql = new StringBuilder();
        
        sql.append("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"")
                .append(product.getName())
                .append("\", ")
                .append(product.getCost())
                .append(")");
        
        execute(sql.toString());
    }
}
