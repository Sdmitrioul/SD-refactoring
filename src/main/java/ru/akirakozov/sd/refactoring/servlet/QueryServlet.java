package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.exception.DaoException;
import ru.akirakozov.sd.refactoring.handler.HandlerException;
import ru.akirakozov.sd.refactoring.handler.QueryProductHandler;
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
        
        try {
            QueryProductHandler handler = QueryProductHandler.getCommand(command);
            
            final String responseHtml = Html.writeBody(builder -> {
                try {
                    handler.consume(dao, builder);
                } catch (DaoException e) {
                    throw new RuntimeException(e);
                }
            });
            
            response.getWriter()
                    .print(responseHtml);
        } catch (HandlerException e) {
            response.getWriter()
                    .println("Unknown command: " + command);
        }
        
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
