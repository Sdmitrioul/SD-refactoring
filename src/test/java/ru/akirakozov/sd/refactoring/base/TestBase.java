package ru.akirakozov.sd.refactoring.base;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.dao.ProductDaoSQLite;
import ru.akirakozov.sd.refactoring.exception.DaoException;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import static org.mockito.Mockito.when;

public abstract class TestBase {
    protected static final String DB_URL = "test.db";
    protected static final ProductDao DAO = new ProductDaoSQLite(DB_URL);
    protected final StringWriter writer = new StringWriter();
    
    protected final HttpServlet servlet;
    @Mock
    protected HttpServletRequest request;
    @Mock
    protected HttpServletResponse response;
    
    public TestBase(final HttpServlet servlet) {
        this.servlet = servlet;
    }
    
    protected static String htmlResult(String value) {
        return htmlResult(result -> result.append(value));
    }
    
    private static String htmlResult(Consumer<StringBuilder> consumer) {
        final StringBuilder result = new StringBuilder();
        
        result.append("<html><body>\n");
        
        consumer.accept(result);
        
        result.append("</body></html>\n");
        return result.toString();
    }
    
    protected static String htmlResult(Collection<Product> items) {
        return htmlResult(result -> {
            items.forEach(item -> result.append(item.getName())
                    .append("\t")
                    .append(item.getCost())
                    .append("</br>\n"));
        });
    }
    
    protected static List<Product> getProducts() {
        final List<Product> products = new ArrayList<>(4);
        
        products.add(Product.of("iphone", 100));
        products.add(Product.of("mac", 1000));
        products.add(Product.of("airpods", 10));
        products.add(Product.of("watch", 50));
        
        return products;
    }
    
    @Before
    public void before() throws IOException {
        DAO.createTableIfNotExist();
        DAO.executeStatement("DELETE FROM PRODUCT");
        MockitoAnnotations.openMocks(this);
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }
    
    @After
    public void after() throws DaoException {
        DAO.executeStatement("DELETE FROM PRODUCT");
    }
}
