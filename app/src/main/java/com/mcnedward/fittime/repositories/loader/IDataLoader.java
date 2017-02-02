package com.mcnedward.fittime.repositories.loader;

/**
 * Created by Edward on 2/13/2016.
 */
public interface IDataLoader<T> {

    void insert(T data);

    void delete(T data);

    void update(T data);

}
