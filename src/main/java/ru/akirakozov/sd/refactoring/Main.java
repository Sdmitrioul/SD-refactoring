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

/**
 * @author akirakozov
 */
public class Main {
    public static final String DB_URL = "jdbc:sqlite:test.db";
    
    public static void main(String[] args) throws Exception {
        final ProductDao dao = new ProductDaoSQLite(DB_URL);
        
        try {
            dao.createTableIfNotExist();
        } catch (DaoException e) {
            System.err.println("Can't create product table: " + e.getMessage());
            return;
        }
        
        final Server server = new Server(8081);
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        
        context.addServlet(new ServletHolder(new AddProductServlet()), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet()), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet()), "/query");
        
        server.start();
        server.join();
    }
}
