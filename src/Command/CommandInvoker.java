//Jose Araya
// This class manages command execution and maintains a history of executed commands.
package Command;

import java.util.ArrayDeque;
import java.util.Deque;

public final class CommandInvoker {

    private Command current;
    // History stack of executed commands (useful for undo functionality)
    // Uses Deque as a stack - last in, first out (LIFO)
    private final Deque<Command> history = new ArrayDeque<>();
    // Sets the current command to be executed
    public void setCommand(Command cmd) {
        this.current = cmd;
    }
    public void execute() {
        if (current != null) { current.execute(); history.push(current); }
    }
    // Returns the history of executed commands
    public Deque<Command> history() {
        return history;
    }
}
