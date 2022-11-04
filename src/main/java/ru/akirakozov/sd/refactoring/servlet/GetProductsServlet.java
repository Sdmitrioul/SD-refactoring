package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.exception.DaoException;
import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.util.Html;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractProductServlet {
    public GetProductsServlet(final ProductDao dao) {
        super(dao);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            final List<Product> products = dao.getAllProducts();
            
            final String responseHTML = Html.writeBody(builder -> {
                products.forEach(product ->
                        builder
                                .append(
                                        product.toString("",
                                                "\t",
                                                "</br>"
                                        ))
                                .append("\n"));
            });
            
            response.getWriter()
                    .print(responseHTML);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
