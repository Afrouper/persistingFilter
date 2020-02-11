package de.afrouper.filter.persisting;

import de.afrouper.filter.persisting.api.DataLoaderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

class PersistingHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private DataLoaderFactory dataLoaderFactory;

    public PersistingHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public HttpSession getSession() {
        return getSession(true);
    }

    @Override
    public HttpSession getSession(boolean create) {
        HttpSession session = super.getSession(create);
        if (session != null) {
            return new WrappedSession(session, dataLoaderFactory.create());
        } else {
            return session;
        }
    }
}
