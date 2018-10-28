package com.getgreetapp.greetapp.specification;


public class NotSpecification<T> extends AbstractSpecification<T> {

    private SpecificationInterface<T> spec;

    public NotSpecification(SpecificationInterface<T> s) {
        this.spec = s;
    }

    @Override
    public boolean isSatisfiedBy(T t) {
        return !spec.isSatisfiedBy(t);
    }

}
