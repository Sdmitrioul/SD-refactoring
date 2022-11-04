package ru.akirakozov.sd.refactoring;

import org.junit.Test;
import ru.akirakozov.sd.refactoring.base.TestBase;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;
import ru.akirakozov.sd.refactoring.util.Pair;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class QueryServletTest extends TestBase {
    public QueryServletTest() {
        super(new QueryServlet(DAO));
    }
    
    @Test
    public void testMinProduct() throws SQLException, ServletException, IOException {
        final List<Pair<String, Integer>> products = getProducts();
        
        insertProduct(products);
        
        when(request.getParameter("command")).thenReturn("min");
        when(request.getMethod()).thenReturn("GET");
        
        servlet.service(request, response);
        
        assertEquals(htmlResult("<h1>Product with min price: </h1>\nairpods\t10</br>\n"), writer.toString());
    }
    
    @Test
    public void testMaxProduct() throws SQLException, ServletException, IOException {
        final List<Pair<String, Integer>> products = getProducts();
        
        insertProduct(products);
        
        when(request.getParameter("command")).thenReturn("max");
        when(request.getMethod()).thenReturn("GET");
        
        servlet.service(request, response);
        
        assertEquals(htmlResult("<h1>Product with max price: </h1>\nmac\t1000</br>\n"), writer.toString());
    }
    
    @Test
    public void testSumProduct() throws SQLException, ServletException, IOException {
        final List<Pair<String, Integer>> products = getProducts();
        
        insertProduct(products);
        
        when(request.getParameter("command")).thenReturn("sum");
        when(request.getMethod()).thenReturn("GET");
        
        servlet.service(request, response);
        
        assertEquals(htmlResult("Summary price: \n1160\n"), writer.toString());
    }
    
    @Test
    public void testCountProduct() throws SQLException, ServletException, IOException {
        final List<Pair<String, Integer>> products = getProducts();
        
        insertProduct(products);
        
        when(request.getParameter("command")).thenReturn("count");
        when(request.getMethod()).thenReturn("GET");
        
        servlet.service(request, response);
        
        assertEquals(htmlResult("Number of products: \n4\n"), writer.toString());
    }
    
    @Test
    public void unknownMethodTest() throws ServletException, IOException {
        final String command = "jwefnjs";
        when(request.getParameter("command")).thenReturn(command);
        when(request.getMethod()).thenReturn("GET");
        
        servlet.service(request, response);
        
        assertEquals("Unknown command: " + command + "\n", writer.toString());
    }
}
