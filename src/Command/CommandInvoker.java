package Command;

import java.util.ArrayDeque;
import java.util.Deque;

public final class CommandInvoker {
    private Command current;
    private final Deque<Command> history = new ArrayDeque<>();
    public void setCommand(Command cmd) { this.current = cmd; }
    public void execute() {
        if (current != null) { current.execute(); history.push(current); }
    }
    public Deque<Command> history() { return history; }
}
