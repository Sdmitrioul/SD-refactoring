package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.util.Html;

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
            final Product product = dao.getProductWithMaxCost();
            final String responseHtml =
                    Html.writeBody(builder -> builder
                            .append("<h1>Product with max price: </h1>")
                            .append("\n")
                            .append(product.toString("", "\t", "</br>"))
                            .append("\n"));
            
            response.getWriter()
                    .print(responseHtml);
        } else if ("min".equals(command)) {
            final Product product = dao.getProductWithMinCost();
            final String responseHtml =
                    Html.writeBody(builder -> builder
                            .append("<h1>Product with min price: </h1>")
                            .append("\n")
                            .append(product.toString("", "\t", "</br>"))
                            .append("\n"));
            
            response.getWriter()
                    .print(responseHtml);
        } else if ("sum".equals(command)) {
            final long sumCost = dao.getSumCost();
            final String responseHtml =
                    Html.writeBody(builder -> builder
                            .append("Summary price: ")
                            .append("\n")
                            .append(sumCost)
                            .append("\n"));
            
            response.getWriter()
                    .print(responseHtml);
        } else if ("count".equals(command)) {
            final long count = dao.getCount();
            final String responseHtml =
                    Html.writeBody(builder -> builder
                            .append("Number of products: ")
                            .append("\n")
                            .append(count)
                            .append("\n"));
            
            response.getWriter()
                    .print(responseHtml);
        } else {
            response.getWriter()
                    .println("Unknown command: " + command);
        }
        
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
