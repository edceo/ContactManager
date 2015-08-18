package com.edsoft.ejb;

import javax.ejb.Local;
import javax.transaction.SystemException;
import java.util.List;

/**
 * Created by edsoft on 8/15/15.
 */
@Local
public interface BasicOperations {

    /**
     * Create a value
     *
     * @param object
     */
    void createOperation(Object object);

    /**
     * Delete a value
     *
     * @param id value id
     * @return boolean
     */
    boolean deleteOperation(int id);

    /**
     * Update a value
     *
     * @param newObject
     */
    void updateOperation(Object newObject);

    /**
     * Return all values
     *
     * @return List
     */
    <T> List<T> readOperation();

    /**
     * Return value by id
     *
     * @param id value id
     * @return Object
     */
    <T> T readOperation(int id);

    /**
     * Find value by username
     *
     * @param <T>
     * @param username
     * @return Object
     */
    <T> T findOperation(String username);
}
