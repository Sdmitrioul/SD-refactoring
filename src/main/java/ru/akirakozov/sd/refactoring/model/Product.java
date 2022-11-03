package ru.akirakozov.sd.refactoring.model;

public final class Product {
    private final String name;
    private final int cost;
    
    public Product(final String name, final int cost) {
        this.name = name;
        this.cost = cost;
    }
    
    public static Product of(final String name, final int price) {
        return new Product(name, price);
    }
    
    public String getName() {
        return name;
    }
    
    public int getCost() {
        return cost;
    }
    
    @Override
    public String toString() {
        return toString("(", ", ", ")");
    }
    
    public String toString(String prepend, String delimiter, String append) {
        StringBuilder result = new StringBuilder();
        
        result.append(prepend)
                .append(name)
                .append(delimiter)
                .append(append);
        
        return result.toString();
    }
}
