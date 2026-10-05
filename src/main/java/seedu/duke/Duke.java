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
            String[] parsed = Parser.parse(input);
            String command = parsed[0];
            String arguments = parsed[1];

            if (command.equals("exit")) {
                ui.showGoodbye();
                break;
            } else if (command.isEmpty()) {
                continue;
            } else {
                ui.showMessage("Command: " + command + " | Args: " + arguments);
            }
        }
    }
}
