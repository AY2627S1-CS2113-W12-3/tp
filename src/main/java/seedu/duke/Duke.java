package seedu.duke;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showBanner();
        String input;
        while (true) {
            input = ui.readLine();
            if (input.equals("exit")) {
                ui.showGoodbye();
                break;
            }
            ui.showMessage(input);
        }
    }
}
