package org.thehive.hiveserver.validation.filter;

import org.springframework.validation.BindingResult;

public interface ValidationFilterUnit<T> {

    void applyFilter(T object, BindingResult bindingResult);

}
