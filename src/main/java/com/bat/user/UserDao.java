package com.bat.user;

import com.bat.util.BaseDao;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao {
    public void insert(User user) {
        em.persist(user);
    }

    public User getById(Integer mapID) {
        return em.find(User.class, mapID);
    }

    public User getByEmail(String email) {
        Query<User> theQuery = getCurrentSession().createQuery("from User where email=:email", User.class);
        theQuery.setParameter("email", email);
        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }
        return theUser;
    }
}
