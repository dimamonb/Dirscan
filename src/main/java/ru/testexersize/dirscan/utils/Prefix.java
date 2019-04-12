package ru.testexersize.dirscan.utils;

public enum Prefix {
        DASH("-"),
        SLASH("F");
        private String c;
        private Prefix(String c) {
            this.c = c;
        }
        public String getName() {
            return c;
        }
}
