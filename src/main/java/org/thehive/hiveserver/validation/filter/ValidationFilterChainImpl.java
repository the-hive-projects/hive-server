package org.thehive.hiveserver.validation.filter;

import lombok.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.Set;

public class ValidationFilterChainImpl<T> implements ValidationFilterChain<T> {

    private final Set<ValidationFilterUnit<? super T>> validationFilterSet;

    public ValidationFilterChainImpl() {
        this.validationFilterSet = new HashSet<>();
    }

    @Override
    public void applyFilters(T object, BindingResult bindingResult) throws BindException {
        validationFilterSet.parallelStream().forEach(vf -> vf.applyFilter(object, bindingResult));
        if (bindingResult.hasErrors())
            throw new BindException(bindingResult);
    }

    @Override
    public void addFilter(@NonNull ValidationFilterUnit<? super T> filter) {
        validationFilterSet.add(filter);
    }

}
