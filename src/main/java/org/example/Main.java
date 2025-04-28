package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws Exception {
        String filepath = "D:\\temp\\JV\\data.xls";

        List<List<String>> excelData = ReadExcelToList.readExcelToList(filepath);

        List<String> flatList = excelData.stream()
                .flatMap(List::stream)
                .toList();

        List<String> headersList = flatList.stream()
                .filter(Objects::nonNull)
                .filter(item -> !IsNumeric.isNumeric(item.trim()))
                .filter(item -> !ContainsPK.containsPK(item))
                .distinct()
                .collect(Collectors.toList());
        headersList.addFirst("ПК");

        List<Integer> pcIndices = IntStream.range(0, flatList.size())
                .filter(i -> flatList.get(i).contains("ПК"))
                .boxed()
                .collect(Collectors.toList());
        pcIndices.add(flatList.size());

        List<Map<String, Object>> listOfMaps = getMaps(pcIndices, flatList);

        ExcelExporter.exportToExcel(listOfMaps, headersList, filepath);
    }


    private static List<Map<String, Object>> getMaps(List<Integer> pcIndices, List<String> flatList) {
        // Ваша существующая реализация метода
        List<Map<String, Object>> listOfMaps = new ArrayList<>();

        for (int i = 0; i < pcIndices.size()-1 ; i++) {
            if (pcIndices.get(i + 1) != null) {
                Map<String, Object> newMap = new HashMap<>();
                int difference = pcIndices.get(i + 1) - pcIndices.get(i)-1;
                int counter = 0;
                while (counter < (difference/2+1)){
                    if (flatList.get(pcIndices.get(i)+counter).contains("ПК")){
                        newMap.put("ПК", flatList.get(pcIndices.get(i)+counter));
                    } else {
                        newMap.put(flatList.get(pcIndices.get(i)+counter),
                                flatList.get(pcIndices.get(i)+counter + difference/2));
                    }
                    counter++;
                }
                listOfMaps.add(newMap);
            }
        }
        return listOfMaps;
    }
}