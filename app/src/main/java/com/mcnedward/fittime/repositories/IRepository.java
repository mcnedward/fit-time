package com.mcnedward.fittime.repositories;

import com.mcnedward.fittime.exceptions.EntityAlreadyExistsException;
import com.mcnedward.fittime.exceptions.EntityDoesNotExistException;

import java.util.List;

/**
 * Created by Edward on 2/8/2016.
 */
public interface IRepository<T> {

    T get(long id);

    T get(String... args);

    T save(T entity) throws EntityAlreadyExistsException;

    boolean update(T entity) throws EntityDoesNotExistException;

    T saveOrUpdate(T entity);

    boolean delete(T entity) throws EntityDoesNotExistException;

    List<T> getAll();

    List<T> getAll(String groupBy, String having, String orderBy);

    void close();
}
