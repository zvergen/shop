package ru.learndev.auth;

import ru.learndev.auth.domain.User;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {UserLoginFilter.USER_FILTER_URI + "*",
        UserLoginFilter.ADMIN_FILTER_URI + "*"})
public class UserLoginFilter implements Filter {

    public static final String USER_FILTER_URI = "/user/";
    public static final String ADMIN_FILTER_URI = "/admin/";


    @Inject
    private AuthBean authBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        User.Role role = authBean.getRole();
        if (role != null) {
            String uri = httpRequest.getRequestURI();
            String adminUri = httpRequest.getContextPath() + ADMIN_FILTER_URI;
            if (uri.startsWith(adminUri) && role != User.Role.ADMIN) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/errors.xhtml");
            }

            chain.doFilter(request, response);
            return;
        }

        authBean.setRequestedPage(httpRequest.getRequestURI());
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.xhtml");
    }

    @Override
    public void destroy() {
    }
}
