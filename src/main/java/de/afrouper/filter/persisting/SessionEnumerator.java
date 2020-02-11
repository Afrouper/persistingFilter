package de.afrouper.filter.persisting;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

class SessionEnumerator implements Enumeration<String> //NOSONAR - API gibt Enumeration vor!
{
    private Enumeration<String> enumerationSession;
    private Iterator<String> iteratorDataLoader;

    public SessionEnumerator(Enumeration<String> enumerationSession, Iterator<String> iteratorDataLoader) {
        this.enumerationSession = enumerationSession;
        this.iteratorDataLoader = iteratorDataLoader;
    }

    @Override
    public boolean hasMoreElements() {
        return enumerationSession.hasMoreElements() || iteratorDataLoader.hasNext();
    }

    @Override
    public String nextElement() {
        if (enumerationSession.hasMoreElements()) {
            return enumerationSession.nextElement();
        }
        if (iteratorDataLoader.hasNext()) {
            return iteratorDataLoader.next();
        }
        throw new NoSuchElementException("Sessionenumerator");
    }
}
