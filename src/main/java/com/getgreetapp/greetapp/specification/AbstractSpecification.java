package com.getgreetapp.greetapp.specification;

public abstract class AbstractSpecification<T> implements SpecificationInterface<T> {

    public abstract boolean isSatisfiedBy(T t);

    public AbstractSpecification<T> or(SpecificationInterface<T> s) {
        return new OrSpecification<T>(this,s);
    }

    public AbstractSpecification<T> and(SpecificationInterface<T> s) {
        return new AndSpecification<T>(this,s);
    }

    public AbstractSpecification<T> not() {
        return new NotSpecification<T>(this);
    }

}
