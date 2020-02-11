package de.afrouper.filter.persisting;

import de.afrouper.filter.persisting.api.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;

class WrappedSession implements HttpSession {
    private static final Logger LOGGER = LoggerFactory.getLogger(WrappedSession.class);

    private final HttpSession delegate;
    private final DataLoader dataLoader;

    WrappedSession(HttpSession delegate, DataLoader dataLoader) {
        this.delegate = delegate;
        this.dataLoader = dataLoader;
    }

    @Override
    public long getCreationTime() {
        return delegate.getCreationTime();
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public long getLastAccessedTime() {
        return delegate.getLastAccessedTime();
    }

    @Override
    public ServletContext getServletContext() {
        return delegate.getServletContext();
    }

    @Override
    public int getMaxInactiveInterval() {
        return delegate.getMaxInactiveInterval();
    }

    @Override
    public void setMaxInactiveInterval(int i) {
        delegate.setMaxInactiveInterval(i);
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return delegate.getSessionContext();
    }

    @Override
    public Object getAttribute(String s) {
        Object attribute = delegate.getAttribute(s);
        if (attribute != null && dataLoader.containsData(s)) {
            attribute = dataLoader.getData(s);
        }
        return attribute;
    }

    @Override
    public Object getValue(String s) {
        return getAttribute(s);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return new SessionEnumerator(delegate.getAttributeNames(), dataLoader.getDataNames().iterator());
    }

    @Override
    public String[] getValueNames() {
        String[] names = delegate.getValueNames();
        HashSet<String> set = new HashSet<>(dataLoader.getDataNames());

        Arrays.stream(names).forEach(set::add);

        return set.toArray(new String[set.size()]);
    }

    @Override
    public void setAttribute(String s, Object o) {
        delegate.setAttribute(s, o);
        if(o instanceof Serializable) {
            dataLoader.setData(s, (Serializable) o);
        }
        else {
            LOGGER.warn("Attribute {} is not serializable. Will not store it in DataLoader!", s);
        }
    }

    @Override
    public void putValue(String s, Object o) {
        setAttribute(s, o);
    }

    @Override
    public void removeAttribute(String s) {
        delegate.removeAttribute(s);
        dataLoader.deleteData(s);
    }

    @Override
    public void removeValue(String s) {
        delegate.removeValue(s);
        dataLoader.deleteData(s);
    }

    @Override
    public void invalidate() {
        delegate.invalidate();
        dataLoader.delete();
    }

    @Override
    public boolean isNew() {
        return delegate.isNew();
    }

}
