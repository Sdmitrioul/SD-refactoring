package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.dao.ProductDaoSQLite;
import ru.akirakozov.sd.refactoring.exception.DaoException;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import javax.servlet.http.HttpServlet;

/**
 * @author akirakozov
 */
public class Main {
    private static final String DB_URL = "product.db";
    
    private static final String ADD_PRODUCT_PATH = "/add-product";
    private static final String GET_PRODUCTS_PATH = "/get-products";
    private static final String QUERY_PRODUCTS_PATH = "/query";
    
    private static final int PORT = 8081;
    
    public static void main(String[] args) throws Exception {
        final ProductDao dao = new ProductDaoSQLite(DB_URL);
        
        try {
            dao.createTableIfNotExist();
        } catch (DaoException e) {
            System.err.println("Can't create product table: " + e.getMessage());
            return;
        }
        
        final Server server = new Server(PORT);
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        
        context.addServlet(servletOf(new AddProductServlet(dao)), ADD_PRODUCT_PATH);
        context.addServlet(servletOf(new GetProductsServlet(dao)), GET_PRODUCTS_PATH);
        context.addServlet(servletOf(new QueryServlet(dao)), QUERY_PRODUCTS_PATH);
        
        server.start();
        server.join();
    }
    
    private static ServletHolder servletOf(final HttpServlet servlet) {
        return new ServletHolder(servlet);
    }
}
