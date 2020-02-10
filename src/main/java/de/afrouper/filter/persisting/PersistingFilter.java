package de.afrouper.filter.persisting;

import javax.servlet.*;
import java.io.IOException;

public class PersistingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Not implemented yet.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //Not implemented yet.
    }
}
