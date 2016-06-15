package ru.danilov.st.data;


import ru.danilov.st.trading.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class LoaderPairs {
    private List<Pair> pairs;

    public LoaderPairs(Serializer serializer) {
        this.pairs = new LinkedList<>();
        for(int i = 0; i < serializer.getQuotes().size(); i++) {
            for (int j = i + 1; j < serializer.getQuotes().size(); j++) {
                pairs.add(new Pair(serializer.getQuotes().get(i), serializer.getQuotes().get(j)));
            }
        }
    }

    public Vector<String> getNamesPair() {
        Vector<String> names = new Vector();
        for (int i = 0; i < pairs.size(); i++) {
            names.add(pairs.get(i).getName());
        }
        return names;
    }

    public Vector<String> getNamesGoodPair() {
        Vector<String> names = new Vector();
        for (int i = 0; i < pairs.size(); i++) {
            if (pairs.get(i).isCointegrated()) {
                names.add(pairs.get(i).getName());
            }
        }
        return names;
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public List<Pair> getGoodPairs() {
        List<Pair> list = new LinkedList<>();
        for (int i = 0; i < pairs.size(); i++) {
            if (pairs.get(i).isCointegrated()) {
                list.add(pairs.get(i));
            }
        }
        return list;
    }
}
