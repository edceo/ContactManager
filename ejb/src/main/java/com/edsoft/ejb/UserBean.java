package com.edsoft.ejb;


import com.edsoft.modal.User;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by edsoft on 8/15/15.
 */
@Stateless(name = "UserEJB")

public class UserBean implements BasicOperations {


    private EntityManager em;

    @PostConstruct
    public void init() {

    }

    public UserBean() {
    }

    /**
     * Create a value
     *
     * @param object
     */
    @Override
    public void createOperation(Object object) {
        User user = (User) object;
        em = Persistence.createEntityManagerFactory("obss").createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();

    }

    /**
     * Delete a value
     *
     * @param id value id
     * @return boolean
     */
    @Override
    public boolean deleteOperation(int id) {
        em = Persistence.createEntityManagerFactory("obss").createEntityManager();
        User user = em.find(User.class, id);
        if (null == user) {
            return false;
        }
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    /**
     * Update a value
     *
     * @param newObject
     */
    @Override
    public void updateOperation(Object newObject) {
        em = Persistence.createEntityManagerFactory("obss").createEntityManager();
        User newUser = (User) newObject;
        em.getTransaction().begin();
        em.merge(newUser);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Find value by username
     *
     * @param username
     * @return Object
     */
    @Override
    public User findOperation(String username) {
        em = Persistence.createEntityManagerFactory("obss").createEntityManager();
        Query query = em.createNamedQuery("User.findByUsername");
        query.setParameter("username", username);
        if (0 == query.getResultList().size()) {
            return null;
        }
        User us = (User) query.getResultList().get(0);
        em.close();
        return us;
    }

    /**
     * Return all values
     *
     * @return List
     */
    @Override
    public List<User> readOperation() {
        em = Persistence.createEntityManagerFactory("obss").createEntityManager();
        Query query = em.createNamedQuery("User.findAll");
        List<User> list = query.getResultList();
        em.close();
        return list;
    }

    /**
     * Return value by id
     *
     * @param id value id
     * @return Objects
     */
    @Override
    public User readOperation(int id) {

        em = Persistence.createEntityManagerFactory("obss").createEntityManager();
        User user = em.find(User.class, id);
        em.close();
        return user;
    }
}
