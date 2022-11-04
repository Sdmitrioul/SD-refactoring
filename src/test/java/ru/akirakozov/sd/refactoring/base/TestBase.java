package ru.akirakozov.sd.refactoring.base;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.dao.ProductDaoSQLite;
import ru.akirakozov.sd.refactoring.util.Pair;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.mockito.Mockito.when;

public abstract class TestBase {
    protected static final String DB_URL = "jdbc:sqlite:test.db";
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
    
    protected static String htmlResult(Collection<Pair<String, Integer>> items) {
        return htmlResult(result -> {
            items.forEach(item -> result.append(item.getFirst())
                    .append("\t")
                    .append(item.getSecond())
                    .append("</br>\n"));
        });
    }
    
    protected static List<Pair<String, Integer>> getProducts() {
        final List<Pair<String, Integer>> products = new ArrayList<>(4);
        
        products.add(Pair.of("iphone", 100));
        products.add(Pair.of("mac", 1000));
        products.add(Pair.of("airpods", 10));
        products.add(Pair.of("watch", 50));
        
        return products;
    }
    
    @Before
    public void before() throws SQLException, IOException {
        DAO.createTableIfNotExist();
        execute("DELETE FROM PRODUCT");
        MockitoAnnotations.openMocks(this);
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }
    
    protected int execute(String sql) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (Statement statement = connection.createStatement()) {
                return statement.executeUpdate(sql);
            }
        }
    }
    
    @After
    public void after() throws SQLException {
        execute("DELETE FROM PRODUCT");
    }
    
    protected void insertProduct(Pair<String, Integer> product) throws SQLException {
        insertProduct(Collections.singleton(product));
    }
    
    protected void insertProduct(Collection<Pair<String, Integer>> products) throws SQLException {
        final StringBuilder sql = new StringBuilder("INSERT INTO Product (name, price) VALUES ");
        
        products.forEach(product -> sql.append("(\"")
                .append(product.getFirst())
                .append(
                        "\", ")
                .append(product.getSecond())
                .append("), "));
        
        sql.deleteCharAt(sql.length() - 2);
        
        execute(sql.toString());
    }
}
