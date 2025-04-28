package org.example;

public class IsNumeric {
    static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        // Удаляем все пробелы для упрощения проверки
        String normalizedStr = str.replaceAll("\\s+", "");

        // Проверка на строку, состоящую только из #
        if (normalizedStr.matches("^#+$")) {
            return true;
        }

        // Основное регулярное выражение теперь включает:
        // 1. Стандартные числа
        // 2. Форматы XX.XX/YY.YY
        // 3. Форматы с символом #
        // 4. Новые форматы с комбинациями # и чисел
        return normalizedStr.matches("^[-+]?[0-9]*([.,][0-9]+)?([eE][-+]?[0-9]+)?$") ||  // стандартные числа
                normalizedStr.matches("^([-+]?[0-9]+([.,][0-9]+)?)/([-+]?[0-9]+([.,][0-9]+)?)$") ||  // формат число/число
                normalizedStr.matches("^#?[-+]?[0-9]+([.,][0-9]+)?$") ||  // числа с # в начале
                normalizedStr.matches("^[-+]?[0-9]+([.,][0-9]+)?#?$") ||  // числа с # в конце
                normalizedStr.matches("^#+/#+$") ||  // формат #####/#####
                normalizedStr.matches("^#+/([-+]?[0-9]+([.,][0-9]+)?)$") ||  // формат ####/0.00
                normalizedStr.matches("^([-+]?[0-9]+([.,][0-9]+)?)/#+$");    // формат 0.00/####
    }
}