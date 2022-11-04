package ru.akirakozov.sd.refactoring.model;

import java.util.Objects;

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
    
    @Override
    public int hashCode() {
        return Objects.hash(name, cost);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final Product product = (Product) o;
        return cost == product.cost && name.equals(product.name);
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
                .append(cost)
                .append(append);
        
        return result.toString();
    }
    
    public String getName() {
        return name;
    }
    
    public int getCost() {
        return cost;
    }
}
