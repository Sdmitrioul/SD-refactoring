package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractProductServlet {
    public QueryServlet(final ProductDao dao) {
        super(dao);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        
        if ("max".equals(command)) {
            response.getWriter()
                    .println("<html><body>");
            response.getWriter()
                    .println("<h1>Product with max price: </h1>");
            
            final Product product = dao.getProductWithMaxCost();
            
            response.getWriter()
                    .println(product.toString("", "\t", "</br>"));
            
            response.getWriter()
                    .println("</body></html>");
        } else if ("min".equals(command)) {
            response.getWriter()
                    .println("<html><body>");
            response.getWriter()
                    .println("<h1>Product with min price: </h1>");
            
            final Product product = dao.getProductWithMinCost();
            
            response.getWriter()
                    .println(product.toString("", "\t", "</br>"));
            
            response.getWriter()
                    .println("</body></html>");
        } else if ("sum".equals(command)) {
            response.getWriter()
                    .println("<html><body>");
            response.getWriter()
                    .println("Summary price: ");
            
            final long sumCost = dao.getSumCost();
            
            response.getWriter()
                    .println(sumCost);
            
            response.getWriter()
                    .println("</body></html>");
        } else if ("count".equals(command)) {
            
            response.getWriter()
                    .println("<html><body>");
            response.getWriter()
                    .println("Number of products: ");
            
            final long count = dao.getCount();
            
            response.getWriter()
                    .println(count);
            
            response.getWriter()
                    .println("</body></html>");
        } else {
            response.getWriter()
                    .println("Unknown command: " + command);
        }
        
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
}
