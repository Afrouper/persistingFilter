package de.afrouper.filter.persisting.api;

import java.io.Serializable;
import java.util.Set;

public interface DataLoader {

    Serializable getData(String name);

    void setData(String name, Serializable data);

    void deleteData(String name);

    boolean containsData(String name);

    Set<String> getDataNames();

    String getId();

    void delete();

    void flush();
}
