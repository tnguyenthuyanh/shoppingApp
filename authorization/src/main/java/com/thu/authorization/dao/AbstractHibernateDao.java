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

//    public void createNewUser(String email, String password) {
//
//        try (Session session = getCurrentSession()) {
//            Transaction transaction = null;
//            transaction = session.beginTransaction();
//
//            User user = User.builder().email(email).password(password).build();
////            int user_id = (Integer) session.save(user); // get back user generated id
//            session.save(user);
////            Permission permission = Permission.builder().user(user).build();
////            session.save(permission);
//            transaction.commit();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


//    public void add(T item) {
//        getCurrentSession().save(item);
//    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
