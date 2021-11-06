package org.thehive.hiveserver.validation.filter;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public interface ValidationFilterChain<T> {

    void applyFilters(T object, BindingResult bindingResult) throws BindException;

    void addFilter(ValidationFilterUnit<? super T> filter);

}
