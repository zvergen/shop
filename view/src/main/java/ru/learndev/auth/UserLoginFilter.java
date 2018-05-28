package ru.learndev.auth;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/user/*")
public class UserLoginFilter implements Filter {

    @Inject
    private AuthBean authBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResp = (HttpServletResponse) response;
        HttpServletRequest httpReq = (HttpServletRequest) request;

        if (authBean.isLoggedIn()) {
            chain.doFilter(request, response);
            return;
        }

        authBean.setRequestedPage(httpReq.getRequestURI());
        httpResp.sendRedirect(httpReq.getContextPath() + "/login.xhtml");
    }

    @Override
    public void destroy() {
    }
}
