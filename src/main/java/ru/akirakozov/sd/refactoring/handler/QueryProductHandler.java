package ru.akirakozov.sd.refactoring.handler;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.exception.DaoException;
import ru.akirakozov.sd.refactoring.model.Product;

public enum QueryProductHandler {
    MIN("min", "<h1>Product with min price: </h1>", (dao, builder) -> {
        final Product product = dao.getProductWithMinCost();
        builder.append(product.toString("", "\t", "</br>"));
    }),
    MAX("max", "<h1>Product with max price: </h1>", (dao, builder) -> {
        final Product product = dao.getProductWithMaxCost();
        builder.append(product.toString("", "\t", "</br>"));
    }),
    SUM("sum", "Summary price: ", (dao, builder) -> {
        final long sumCost = dao.getSumCost();
        builder.append(sumCost);
    }),
    COUNT("count", "Number of products: ", (dao, builder) -> {
        final long count = dao.getCount();
        builder.append(count);
    });
    
    private final ResultConsumer consumer;
    private final String command;
    private final String header;
    
    QueryProductHandler(final String command, final String header, final ResultConsumer consumer) {
        this.command = command;
        this.header = header;
        this.consumer = consumer;
    }
    
    public static QueryProductHandler getCommand(final String value) throws HandlerException {
        for (QueryProductHandler type : QueryProductHandler.values()) {
            if (type.command.equals(value)) {
                return type;
            }
        }
        
        throw new HandlerException("Unsupported command");
    }
    
    public void consume(ProductDao dao, StringBuilder builder) throws DaoException {
        builder.append(header)
                .append("\n");
        
        consumer.consume(dao, builder);
        
        builder.append("\n");
    }
    
    @FunctionalInterface
    private interface ResultConsumer {
        void consume(ProductDao dao, StringBuilder builder) throws DaoException;
    }
}
