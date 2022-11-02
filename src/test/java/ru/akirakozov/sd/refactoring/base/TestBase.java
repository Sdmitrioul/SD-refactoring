package ru.akirakozov.sd.refactoring.base;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ru.akirakozov.sd.refactoring.util.Pair;

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

import static org.mockito.Mockito.when;
import static ru.akirakozov.sd.refactoring.Main.DB_URL;

public abstract class TestBase {
    private static final String PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";
    protected final StringWriter writer = new StringWriter();
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    protected HttpServletRequest request;
    @Mock
    protected HttpServletResponse response;
    
    @Before
    public void before() throws SQLException, IOException {
        execute(PRODUCT_TABLE);
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }
    
    protected void execute(String sql) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);
            }
        }
    }
    
    @After
    public void after() throws SQLException {
        execute("DELETE FROM PRODUCT WHERE True");
    }
    
    protected String htmlResult(Collection<Pair<String, String>> items) {
        final StringBuilder result = new StringBuilder();
        
        result.append("<html><body>\n");
        
        items.forEach(item -> result.append(item.getFirst())
                .append("\t")
                .append(item.getSecond())
                .append("</br>\n"));
        
        result.append("</body></html>\n");
        return result.toString();
    }
}
