package ru.fedorov.application.work;

import ru.fedorov.application.Main;
import ru.fedorov.application.comparators.MyComparator;
import ru.fedorov.application.converter.InputDataToDataConverter;
import ru.fedorov.application.domian.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Work {

    private final MyComparator comparator = new MyComparator();
    private final InputDataToDataConverter inputDataToDataConverter = new InputDataToDataConverter();
    private final Map<String, TreeSet<String>> keywordToSiteHosts = new HashMap<>();

    public String work(String inputData) {
        final Data data = inputDataToDataConverter.convert(inputData);
        switch (data.getOperation()) {
            case ADD:
                return getAddResult(data);
            case REMOVE:
                return getRemoveResult(data);
            case SEARCH:
                return getSearchResult(data);
        }
        throw new RuntimeException();
    }

    private String getAddResult(Data data) {
        final String keyword = data.getKeyword();
        final String siteHost = data.getSiteHost();
        final boolean isSuccessAction = keywordToSiteHosts.computeIfAbsent(keyword, (u) -> new TreeSet<>(comparator)).add(siteHost);
        return isSuccessAction ? "OK" : "Already exists";
    }

    private String getRemoveResult(Data data) {
        final String keyword = data.getKeyword();
        final String siteHost = data.getSiteHost();
        final Set<String> siteHosts = keywordToSiteHosts.get(keyword);
        boolean isSuccessAction = false;
        if (siteHosts != null) {
            isSuccessAction = siteHosts.remove(siteHost);
        }
        return isSuccessAction ? "OK" : "Not found";
    }

    private String getSearchResult(Data data) {
        final String keyword = data.getKeyword();
        final Set<String> siteHosts = keywordToSiteHosts.getOrDefault(keyword, new TreeSet<>(comparator));
        StringBuilder result = new StringBuilder("Results: ");
        result.append(siteHosts.size());
        result.append(" site(s) found");
        int siteCount = 0;
        for (String siteHost : siteHosts) {
            result.append("\n");
            result.append(++siteCount);
            result.append(") ");
            result.append(siteHost);
            if (siteCount == 10) {
                break;
            }
        }
        return result.toString();
    }
}
