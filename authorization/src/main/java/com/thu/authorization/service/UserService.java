package com.thu.authorization.service;

import com.thu.authorization.dao.UserDao;
import com.thu.authorization.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public int getUserIdByUsername(String email) {
        return userDao.getUserIdByUsername(email);
    }

}
