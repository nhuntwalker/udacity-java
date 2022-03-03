package ui;

import java.util.Scanner;

public class InputDevice {
    private final Scanner scanner;

    public InputDevice(){
        this.scanner = new Scanner(System.in);
    }
    public String getInput(){
        return scanner.nextLine().strip();
    }
}
