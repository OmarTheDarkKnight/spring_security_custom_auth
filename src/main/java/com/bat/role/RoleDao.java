package com.bat.role;

import com.bat.util.BaseDao;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao extends BaseDao {
    public Role findByRole(String role) {
        Query<Role> theQuery = getCurrentSession().createQuery("from Role where role=:role", Role.class);
        theQuery.setParameter("role", role);

        Role theRole = null;
        try {
            theRole = theQuery.getSingleResult();
        } catch (Exception ignored) {}
        return theRole;
    }
}
