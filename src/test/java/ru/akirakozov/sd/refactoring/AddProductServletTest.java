package ru.akirakozov.sd.refactoring;

import org.junit.Test;
import ru.akirakozov.sd.refactoring.base.TestBase;
import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AddProductServletTest extends TestBase {
    
    public AddProductServletTest() {
        super(new AddProductServlet(DAO));
    }
    
    @Test
    public void addProductTest() throws ServletException, IOException {
        final Product product = Product.of("iphone", 10000);
        
        when(request.getMethod()).thenReturn("GET");
        when(request.getParameter("name")).thenReturn(product.getName());
        when(request.getParameter("price")).thenReturn(String.valueOf(product.getCost()));
        
        servlet.service(request, response);
        
        assertEquals("OK\n", writer.toString());
        
        final AtomicInteger success = new AtomicInteger();
        
        final List<Product> products = DAO.getAllProducts();
        
        products.forEach(it -> {
            if (it.equals(product)) {
                success.getAndIncrement();
            }
        });
        
        assertEquals(1, success.get());
    }
}
