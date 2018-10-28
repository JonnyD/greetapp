package com.getgreetapp.greetapp.specification;

import java.util.HashSet;
import java.util.Set;

public class OrSpecification<T> extends AbstractSpecification<T> {

    private Set<SpecificationInterface<T>> set = new HashSet<SpecificationInterface<T>>();

    public OrSpecification(SpecificationInterface<T> a, SpecificationInterface<T> b) {
        set.add(a);
        set.add(b);
    }

    public boolean isSatisfiedBy(T t) {
        for( SpecificationInterface<T> s : set ) {
            if( s.isSatisfiedBy(t) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AbstractSpecification<T> or(SpecificationInterface<T> s) {
        set.add(s);
        return this;
    }

}
