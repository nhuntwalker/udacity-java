package utils;

public class BaseTestRunner {
    public static void print(String text){
        System.out.println(text);
    }
    public static void pass(String text) {
        print("TEST PASS: " + text);
    }
    public static void fail(String text) {
        print("TEST FAIL: " + text);
        throw new AssertionError("Tests failed");
    }
}
