package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Greet;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

public class IsGreetPublic extends AbstractSpecification<Greet> {
    public boolean isSatisfiedBy(Greet greet) {
        return greet.isPublic();
    }
}
