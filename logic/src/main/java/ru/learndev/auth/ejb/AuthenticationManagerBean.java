package ru.learndev.auth.ejb;

import org.apache.commons.lang3.StringUtils;
import ru.learndev.auth.domain.Credentials;
import ru.learndev.auth.domain.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class AuthenticationManagerBean {

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    public User.Role login(String email, String password) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            return null;
        }
        Credentials credentials = entityManager.find(Credentials.class, email);
        if (credentials == null) {
            return null;
        }
        if (!password.equals(credentials.getPassword())) {
            return null;
        }
        User user = credentials.getUser();
        if (user == null) {
            return null;
        }

        return user.getRole();
    }
}