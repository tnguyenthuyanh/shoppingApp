//package com.thu.authorization.dao;
//
//import com.thu.auth.domain.entity.Permission;
//import com.thu.auth.domain.entity.User;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public class UserDao extends AbstractHibernateDao<User> {
//
//    public UserDao() {
//        setClazz(User.class);
//    }
//
//    public Optional<User> loadUserByUsername(String email) {
//        return this.findByEmail(email);
//    }
//
//    public void createNewUser(String email, String password) {
////        this.createNewUser(email, password);
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = null;
//            transaction = session.beginTransaction();
//
//            User user = User.builder().email(email).password(password).build();
////            int user_id = (Integer) session.save(user); // get back user generated id
//            session.save(user);
//            Permission permission = Permission.builder().user(user).build();
//            session.save(permission);
//            transaction.commit();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
