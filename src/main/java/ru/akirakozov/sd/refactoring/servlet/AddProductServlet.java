package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {
    private final ProductDao dao;
    
    public AddProductServlet(final ProductDao dao) {
        this.dao = dao;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Product product = Product.of(
                request.getParameter("name"),
                Long.parseLong(request.getParameter("price"))
        );
        
        try {
            dao.addProduct(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter()
                .println("OK");
    }
}
