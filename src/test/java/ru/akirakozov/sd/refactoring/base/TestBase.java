package ru.akirakozov.sd.refactoring.base;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static ru.akirakozov.sd.refactoring.Main.DB_URL;

public abstract class TestBase {
    private static final String PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";
    protected final StringWriter writer = new StringWriter();
    
    protected final HttpServlet servlet;
    @Mock
    protected HttpServletRequest request;
    @Mock
    protected HttpServletResponse response;
    
    public TestBase(final HttpServlet servlet) {
        this.servlet = servlet;
    }
    
    @Before
    public void before() throws SQLException, IOException {
        execute(PRODUCT_TABLE);
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
    
    protected String htmlResult(Collection<Pair<String, Integer>> items) {
        final StringBuilder result = new StringBuilder();
        
        result.append("<html><body>\n");
        
        items.forEach(item -> result.append(item.getFirst())
                .append("\t")
                .append(item.getSecond())
                .append("</br>\n"));
        
        result.append("</body></html>\n");
        return result.toString();
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
