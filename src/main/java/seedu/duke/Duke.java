package seedu.duke;

import java.util.Scanner;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        String banner = "    ____  __                __        __  _                   ___ \n"
                + "   / __ \\/ /___ ___  ______/ /_____ _/ /_(_)___  ____   _   <  / \n"
                + "  / /_/ / / __ `/ / / / __/ __/ __ `/ __/ / __ \\/ __ \\ |_|  / /  \n"
                + " / ____/ / /_/ / /_/ /\\__ \\ / /_/ / /_/ / /_/ / / / /      / /   \n"
                + "/_/   /_/\\__,_/\\__, /___/\\__\\__,_/\\__/_/\\____/_/ /_/      /_/    \n"
                + "              /____/\n";
        System.out.println(banner);
        System.out.println("What is your name?");

        Scanner in = new Scanner(System.in);
        System.out.println("Hello " + in.nextLine());
    }
}
