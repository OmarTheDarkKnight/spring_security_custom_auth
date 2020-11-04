package com.bat.util;

import com.bat.role.RoleDao;
import com.bat.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService {
    @Autowired
    public UserDao userDao;

    @Autowired
    public RoleDao roleDao;
}
