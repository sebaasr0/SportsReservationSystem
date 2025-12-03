package Command;

import java.util.ArrayDeque;
import java.util.Deque;

public final class CommandInvoker {
    // The command that will be executed
    private Command current;
    private final Deque<Command> history = new ArrayDeque<>();
    // Sets the current command to be executed
    public void setCommand(Command cmd) { this.current = cmd; }
    public void execute() {
        if (current != null) { current.execute(); history.push(current); }
    }// Returns the history of executed commands
    public Deque<Command> history() { return history; }
}
