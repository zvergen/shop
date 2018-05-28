package ru.learndev.auth;

import org.apache.commons.lang3.StringUtils;
import ru.learndev.auth.domain.User;
import ru.learndev.auth.ejb.AuthenticationManagerBean;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class AuthBean implements Serializable {

    private User.Role role;
    private String login;
    private String password;
    private String requestedPage;

    @EJB
    private AuthenticationManagerBean authenticationManagerBean;

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRequestedPage() {
        return requestedPage;
    }

    public void setRequestedPage(String requestedPage) {
        this.requestedPage = requestedPage;
    }

    public void doLogin() {
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            role = null;
            return;
        }

        role = authenticationManagerBean.login(login, password);

        if (role.equals(User.Role.USER)) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(requestedPage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
