import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * Layered implementations of secondary methods for {@code TaskManager}.
 */
public abstract class TaskManagerSecondary implements TaskManager {
    @Override
    public final void changeTaskPriority(String taskName, int newPriority) {
        assert this.hasTask(taskName) : "Violation of: taskName is in this";

        Task temp = this.removeTask(taskName);
        this.addTask(taskName, newPriority, temp.getDueDate());
    }

    @Override
    public final void rescheduleTask(String taskName, String newDueDate) {
        assert this.hasTask(taskName) : "Violation of: taskName is in this";

        Task temp = this.removeTask(taskName);
        this.addTask(taskName, temp.getPriority(), newDueDate);
    }

    @Override
    public final Sequence<Task> listTasksByPriority() {
        Sequence<Task> list = new Sequence1L<>();
        Sequence<Task> sortedList = new Sequence1L<>();


        // this WILL BE ITERABLE
        for (Task task : this) {
            list.add(list.length(), task);
        }
        int indexOfHighestPriority = 0;
        while (list.length() > 0) {
            for (int i = 1; i < list.length(); i++) {
                if (list.entry(i - 1).getPriority() >= list.entry(i).getPriority()) {
                    indexOfHighestPriority = i - 1;
                }
            }
            sortedList.add(sortedList.length(), list.remove(indexOfHighestPriority));
        }

        return sortedList;
    }

    @Override
    public final Sequence<Task> listCompletedTasks() {
        Sequence<Task> completedTasks = new Sequence1L<>();

        for (Task task : this) {
            if (this.isTaskComplete(task.getName())) {
                completedTasks.add(completedTasks.length(), task);
            }
        }

        return completedTasks;
    }

    @Override
    public final Sequence<Task> listIncompleteTasks() {
        Sequence<Task> completedTasks = new Sequence1L<>();

        for (Task task : this) {
            if (!this.isTaskComplete(task.getName())) {
                completedTasks.add(completedTasks.length(), task);
            }
        }

        return completedTasks;
    }

    @Override
    public final String nextTask() {
        String result = "";

        int highestPriority = 0;
        for (Task task : this) {
            if (!this.isTaskComplete(task.getName())) {
                if (task.getPriority() >= highestPriority) {
                    highestPriority = task.getPriority();
                    result = task.getName();
                }
            }
        }

        for (Task task : this) {
            if (!this.isTaskComplete(task.getName())) {
                if (task.getPriority() == highestPriority) {
                    Task temp = this.removeTask(result);
                    //due date has to be in a format where compareTo works correctly here
                    if (task.getDueDate().compareTo(temp.getDueDate()) > 0) {
                        result = task.getName();
                    }
                }
            }
        }

        return result;
    }


}
