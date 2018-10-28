package com.getgreetapp.greetapp.specification;

public interface SpecificationInterface<T> {
    public boolean isSatisfiedBy(T t);
}
