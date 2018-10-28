package com.getgreetapp.greetapp.specification;

import java.util.HashSet;
import java.util.Set;

public class AndSpecification<T> extends AbstractSpecification<T> {

    private Set<SpecificationInterface<T>> set = new HashSet<SpecificationInterface<T>>();

    public AndSpecification(SpecificationInterface<T> a, SpecificationInterface<T> b) {
        set.add(a);
        set.add(b);
    }

    public boolean isSatisfiedBy(T t) {
        for( SpecificationInterface<T> s : set ) {
            if( !s.isSatisfiedBy(t) ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public AbstractSpecification<T> and(SpecificationInterface<T> s) {
        set.add(s);
        return this;
    }

}
