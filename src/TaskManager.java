import components.sequence.Sequence;

/**
 * {@code TaskManagerKernel} with enhanced secondary methods.
 */
public interface TaskManager extends TaskManagerKernel {

    /**
     * Updates the priority of an existing task.
     *
     * @param taskName
     *            the name of the task to update
     * @param newPriority
     *            the new priority level for the task
     * @updates this
     * @requires taskName is in this
     * @ensures priority of taskName = newPriority
     */
    void changeTaskPriority(String taskName, int newPriority);

    /**
     * Changes the due date of an existing task.
     *
     * @param taskName
     *            the name of the task to reschedule
     * @param newDueDate
     *            the new due date for the task
     * @updates this
     * @requires taskName is in this
     * @ensures due date of taskName = newDueDate
     */
    void rescheduleTask(String taskName, String newDueDate);

    /**
     * Returns all tasks sorted by priority and due date.
     *
     * @return a sequence of tasks sorted by priority (higher first) and due
     *         date (earlier first)
     * @ensures listTasksByPriority contains all tasks in this, sorted by
     *          priority and due date
     */
    Sequence<Task> listTasksByPriority();

    /**
     * Returns all completed tasks.
     *
     * @return a sequence of tasks that are marked as complete
     * @ensures listCompletedTasks contains all tasks t in this where t.isComplete = true
     */
    Sequence<Task> listCompletedTasks();

    /**
     * Returns all incomplete tasks.
     *
     * @return a sequence of tasks that are not yet complete
     * @ensures listIncompleteTasks contains all tasks t in this where
     * t.isComplete = false
     */
    Sequence<Task> listIncompleteTasks();

    /**
     * Returns the highest-priority incomplete task. If multiple tasks have the
     * same priority, the earliest due date is used as a tiebreaker.
     *
     * @return the name of the highest-priority incomplete task
     * @requires there exists at least one incomplete task in this
     * @ensures nextTask is the name of the highest-priority incomplete task,
     *          with the earliest due date as a tiebreaker
     */
    String nextTask();
}
