package com.thu.authorization.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public abstract class AbstractHibernateDao<T> {
    @Autowired
    protected SessionFactory sessionFactory;

    protected Class<T> clazz;

    protected final void setClazz(final Class<T> clazzToSet) {
        clazz = clazzToSet;
    }
    protected Class<T> getClazz() {return clazz;}

//    public List<T> getAll() {
//        Session session = getCurrentSession();
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<T> criteria = builder.createQuery(clazz);
//        criteria.from(clazz);
//        return session.createQuery(criteria).getResultList();
//    }

    public T findById(int id) {
        return getCurrentSession().get(clazz, id);
    }

    public Optional<T> findByEmail(String email) {
        Session session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        Root<T> root = criteria.from(clazz);
        criteria.select(root);
        criteria.where(builder.equal(root.get("email"), email));
        Optional<T> possibleObject = Optional.empty();
        if (session.createQuery(criteria).getResultList().size() != 0) {
            possibleObject = Optional.of(session.createQuery(criteria).getResultList().get(0));
        }

        return possibleObject;
    }


    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
