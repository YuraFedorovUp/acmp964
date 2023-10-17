package ru.fedorov.application;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


public class Main {
    private final MyComparator comparator = new MyComparator();
    private final Start START = new Start();

    public static void main(String[] args) {
        Main main = new Main();
        main.go();
    }

    private void go() {
        START.start();
    }

    enum Operation {
        ADD("Add keyword ", " to "),
        SEARCH("Search ", ""),
        REMOVE("Remove keyword ", " from ");
        private String operationNameStart;
        private String divider;

        Operation(String operationNameStart, String divider) {
            this.operationNameStart = operationNameStart;
            this.divider = divider;
        }

        public String getOperationNameStart() {
            return operationNameStart;
        }

        public String getDivider() {
            return divider;
        }
    }

    public class Start {

        private final Work work = new Work();

        public void start() {
            try (Scanner sc = new Scanner(System.in)) {
                final String quantityResponseString = sc.nextLine().trim();
                final int quantityResponse = Integer.parseInt(quantityResponseString);
                for (int i = 0; i < quantityResponse; i++) {
                    final String input = sc.nextLine().trim();
                    String output = work.work(input);
                    if (i != quantityResponse - 1) {
                        output += "\n=====";
                    }
                    System.out.println(output);
                    if (i == quantityResponse - 1) {
                        return;
                    }
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new RuntimeException();
        }
    }

    class Work {
        private final InputDataToDataConverter inputDataToDataConverter = new InputDataToDataConverter();
        private final Map<String, TreeSet<String>> keywordToSiteHosts = new HashMap<>();

        public String work(String inputData) {
            final Data data = inputDataToDataConverter.convert(inputData);
            return getResult(data);
        }

        private String getResult(Data data) {
            return getFirstResult(data);
        }

        private String getFirstResult(Data data) {
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

    class InputDataToDataConverter {

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

    class Data {
        private final Operation operation;
        private final String keyword;
        private final String siteHost;

        public Data(Operation operation, String keyword, String siteHost) {
            this.operation = operation;
            this.keyword = keyword;
            this.siteHost = siteHost;
        }

        public Operation getOperation() {
            return operation;
        }

        public String getKeyword() {
            return keyword;
        }

        public String getSiteHost() {
            return siteHost;
        }
    }

    class MyComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }

        private String getOrdinalString(String s) {
            char[] a = s.toCharArray();
            Arrays.sort(a);
            return new String(a);
        }
    }
}
