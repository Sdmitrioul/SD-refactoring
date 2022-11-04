package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;

import javax.servlet.http.HttpServlet;

public abstract class AbstractProductServlet extends HttpServlet {
    protected final ProductDao dao;
    
    public AbstractProductServlet(final ProductDao dao) {
        this.dao = dao;
    }
}
