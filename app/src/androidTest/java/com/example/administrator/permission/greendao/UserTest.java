package com.example.administrator.permission.greendao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.example.administrator.permission.greendao.User;
import com.example.administrator.permission.greendao.UserDao;

public class UserTest extends AbstractDaoTestLongPk<UserDao, User> {

    public UserTest() {
        super(UserDao.class);
    }

    @Override
    protected User createEntity(Long key) {
        User entity = new User();
        entity.setId(key);
        entity.setId(100);
        entity.setName("A");
        return entity;
    }

}
