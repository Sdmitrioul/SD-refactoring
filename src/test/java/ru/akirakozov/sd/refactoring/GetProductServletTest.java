package ru.akirakozov.sd.refactoring;

import org.junit.Test;
import ru.akirakozov.sd.refactoring.base.TestBase;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.util.Pair;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class GetProductServletTest extends TestBase {
    public GetProductServletTest() {
        super(new GetProductsServlet());
    }
    
    @Test
    public void testGetMethod() throws ServletException, IOException, SQLException {
        final List<Pair<String, Integer>> products = getProducts();
        
        insertProduct(products);
        
        when(request.getMethod()).thenReturn("GET");
        
        servlet.service(request, response);
        
        assertEquals(htmlResult(products), writer.toString());
    }
    
    private static List<Pair<String, Integer>> getProducts() {
        final List<Pair<String, Integer>> products = new ArrayList<>(4);
        
        products.add(Pair.of("iphone", 100));
        products.add(Pair.of("mac", 1000));
        products.add(Pair.of("airpods", 10));
        products.add(Pair.of("watch", 50));
        
        return products;
    }
}
