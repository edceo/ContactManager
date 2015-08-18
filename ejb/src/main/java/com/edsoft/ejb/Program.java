package com.edsoft.ejb;

import com.edsoft.modal.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by edsoft on 8/16/15.
 */
public class Program {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("obss").createEntityManager();

        em.getTransaction().begin();
        em.persist(new User("Ysuf", "asdasd", "sdafad", "ds", true));
        em.getTransaction().commit();
        em.close();

    }
}
