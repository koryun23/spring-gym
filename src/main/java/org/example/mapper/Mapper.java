package org.example.mapper;

public interface Mapper<From, To> {

    To map(From obj);
}
