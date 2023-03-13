package com.thu.authorization.dao;

import com.thu.authorization.domain.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDao extends AbstractHibernateDao<User> {

    public UserDao() {
        setClazz(User.class);
    }

    public Optional<User> loadUserByUsername(String email) {
        return this.findByEmail(email);
    }

    public int getUserIdByUsername(String email) {
        Optional<User> user = this.findByEmail(email);
        return user.get().getUser_id();
    }
}
