package org.example.mapper;

public interface Mapper<F, T> {

    T map(F obj);
}
