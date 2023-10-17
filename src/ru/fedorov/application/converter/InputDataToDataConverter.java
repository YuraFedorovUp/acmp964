package ru.fedorov.application.converter;

import ru.fedorov.application.domian.Data;
import ru.fedorov.application.domian.Operation;

import java.util.Arrays;

public class InputDataToDataConverter {

    public Data convert(String inputData) {
        final Operation operation = Arrays.stream(Operation.values())
                .filter(it -> inputData.startsWith(it.getOperationNameStart()))
                .findFirst()
                .orElseThrow();
        String currentInputData = inputData.substring(operation.getOperationNameStart().length());
        return getKeyword(operation, currentInputData);
    }

    private Data getKeyword(Operation operation, String currentInputData) {
        String keyword;
        String siteHost = null;
        if (operation != Operation.SEARCH) {
            final String[] s = currentInputData.split(operation.getDivider());
            keyword = getWoldInDividers(s[0]);
            siteHost = s[1];
        } else {
            keyword = getWoldInDividers(currentInputData);
        }
        return new Data(operation, keyword, siteHost);
    }

    private String getWoldInDividers(String world) {
        return world.substring(1, world.length() - 1);
    }
}
