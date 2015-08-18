package com.edsoft.ejb;

import com.edsoft.modal.Contact;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by edsoft on 8/15/15.
 */
@Stateless(name = "ContactEJB")
public class ContactBean implements BasicOperations {


    private EntityManager em;

    public ContactBean() {
    }

    @PostConstruct
    public void init() {

    }

    /**
     * Create a value
     *
     * @param object
     */
    @Override
    public void createOperation(Object object) {

        em = Persistence.createEntityManagerFactory("obss").createEntityManager();
        Contact contact = (Contact) object;
        em.getTransaction().begin();
        em.persist(contact);
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
        Contact contact = em.find(Contact.class, id);
        if (null == contact) {
            return false;
        }
        em.getTransaction().begin();
        em.remove(contact);
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
        Contact contact = (Contact) newObject;
        em.getTransaction().begin();
        em.merge(contact);
        em.getTransaction().commit();
        em.close();

    }

    /**
     * Find value by username
     *
     * @param email
     * @return Object
     */
    @Override
    public Contact findOperation(String email) {

        em = Persistence.createEntityManagerFactory("obss").createEntityManager();
        Query query = em.createNamedQuery("Contact.findByEmail");
        query.setParameter("email", email);
        if (0 == query.getResultList().size()) {
            return null;
        }
        Contact con = (Contact) query.getResultList().get(0);
        em.close();
        return con;
    }


    /**
     * Return all values
     *
     * @return List
     */
    @Override
    public List<Contact> readOperation() {

        em = Persistence.createEntityManagerFactory("obss").createEntityManager();
        Query query = em.createNamedQuery("Contact.findAll");
        List<Contact> list = query.getResultList();
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
    public Contact readOperation(int id) {

        em = Persistence.createEntityManagerFactory("obss").createEntityManager();
        Contact con = em.find(Contact.class, id);
        em.close();
        return con;
    }
}
