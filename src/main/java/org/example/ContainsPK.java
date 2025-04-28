package org.example;

public class ContainsPK {
    static boolean containsPK(String str) {
        if (str == null) {
            return false;
        }
        return str.toLowerCase().contains("пк");
    }
}
