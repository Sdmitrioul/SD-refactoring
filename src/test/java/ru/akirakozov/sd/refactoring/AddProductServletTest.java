package ru.akirakozov.sd.refactoring;

import org.junit.Test;
import ru.akirakozov.sd.refactoring.base.TestBase;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.util.Pair;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AddProductServletTest extends TestBase {
    
    public AddProductServletTest() {
        super(new AddProductServlet(DAO));
    }
    
    @Test
    public void addProductTest() throws ServletException, IOException, SQLException {
        final Pair<String, Integer> product = new Pair<>("iphone", 10000);
        
        when(request.getMethod()).thenReturn("GET");
        when(request.getParameter("name")).thenReturn(product.getFirst());
        when(request.getParameter("price")).thenReturn(String.valueOf(product.getSecond()));
        
        servlet.service(request, response);
        
        assertEquals("OK\n", writer.toString());
        
        int success = 0;
        
        try (final Connection connection = DriverManager.getConnection(DB_URL)) {
            try (final Statement statement = connection.createStatement()) {
                try (final ResultSet result = statement.executeQuery("SELECT name, price FROM PRODUCT")) {
                    while (result.next()) {
                        if (product.getFirst()
                                .equals(result.getString("name")) && product.getSecond() == result.getInt(
                                "price")) {
                            success++;
                        }
                    }
                }
            }
        }
        
        assertEquals(1, success);
    }
}
