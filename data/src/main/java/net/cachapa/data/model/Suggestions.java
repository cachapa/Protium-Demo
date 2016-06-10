package net.cachapa.data.model;

import java.util.ArrayList;
import java.util.List;

public class Suggestions {
    private Result result;

    public List<String> getSuggestions() {
        ArrayList<String> suggestions = new ArrayList<>(result.objects.size());
        for (Object object : result.objects) {
            suggestions.add(object.name);
        }
        return suggestions;
    }

    private class Result {
        private List<Object> objects;
    }

    private class Object {
        private String name;
    }
}
