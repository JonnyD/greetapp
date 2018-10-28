package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

public class IsGangPublic extends AbstractSpecification<Gang> {
    public boolean isSatisfiedBy(Gang gang) {
        return gang.isPublic();
    }
}
