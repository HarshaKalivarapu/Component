import java.lang.reflect.InvocationTargetException;

import components.map.Map;
import components.map.Map1L;

/**
 * {@code NaturalNumber} represented as a {@link java.math.BigInteger
 * java.math.BigInteger} with implementations of primary methods.
 *
 *
 * @convention taskMap is not null AND
 * every key k in taskMap maps to a Task v such that
 * v.getName().equals(k) AND v.getPriority() >= 0
 * @correspondence this = the set of all Tasks in taskMap.values()
 * modeled as (name, priority, dueDate, isComplete)
 */
public class TaskManager1 extends TaskManagerSecondary {
    /**
     * Nested Task interface representing a single task.
     *
     * @mathsubtypes <pre>
     * TASK is (name: STRING, priority: INTEGER, dueDate: STRING, isComplete: BOOLEAN)
     *  exemplar t
     *  constraint priority >= 0
     * </pre>
     * @mathmodel type Task is modeled by TASK
     */
    private final class Task implements TaskManagerKernel.Task {

        private String name;

        private int priority;

        private String dueDate;

        private boolean isComplete;

        private void createNewRep() {
            this.name = "";
            this.priority = -1;
            this.dueDate = "00/00/0000";
            this.isComplete = false;
        }

        public Task(String name, int priority, String dueDate) {
            this.name = name;
            this.priority = priority;
            this.dueDate = dueDate;
            this.isComplete = false;
        }

        /**
         * Gets the name of this task.
         *
         * @return the task name
         * @ensures getName = this.name
         */
        @Override
        public String getName() {
            return this.name;
        }

        /**
         * Gets the priority level of this task.
         *
         * @return the task priority
         * @ensures getPriority = this.priority
         */
        @Override
        public int getPriority() {
            return this.priority;
        }

        /**
         * Gets the due date of this task.
         *
         * @return the task due date
         * @ensures getDueDate = this.dueDate
         */
        @Override
        public String getDueDate() {
            return this.dueDate;
        }

        /**
         * Checks if this task is complete.
         *
         * @return true if the task is complete, false otherwise
         * @ensures isComplete = this.isComplete
         */
        @Override
        public boolean isComplete() {
            return this.isComplete;
        }

        /**
         * Sets the priority of this task.
         *
         * @param priority the new priority level
         * @requires priority >= 0
         * @ensures this.priority = priority
         */
        @Override
        public void setPriority(int priority) {
            this.priority = priority;
        }

        /**
         * Sets the due date of this task.
         *
         * @param dueDate the new due date
         * @ensures this.dueDate = dueDate
         */
        @Override
        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        /**
         * Marks this task as complete.
         *
         * @ensures this.isComplete = true
         */
        @Override
        public void markComplete() {
            this.isComplete = true;
        }
    }

    private Map<String, Task> tasks;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.tasks = new Map1L<>();
    }


    @Override
    public final void addTask(String taskName, int priority, String dueDate) {
        this.tasks.add(taskName, new Task(taskName, priority, dueDate));
    }

    /**
     * Removes the task with the given name.
     *
     * @param taskName
     *              the name of the task to be removed
     * @updates this
     * @requires taskName is in this
     * @return the task removed
     * @ensures this = #this - { (taskName, priority, dueDate, isComplete) }
     */
    @Override
    public final Task removeTask(String taskName) {
        return this.tasks.remove(taskName).value();
    }

    /**
     * Checks if the given task is complete.
     *
     * @param taskName
     *              the name of the task
     * @return true if the task is complete, false otherwise
     * @requires taskName is in this
     * @ensures isTaskComplete = (completion status of taskName)
     */
    @Override
    public final boolean isTaskComplete(String taskName) {
        return this.tasks.value(taskName).isComplete();
    }

    /**
     * Marks the given task as complete.
     *
     * @param taskName
     *              the name of the task to be marked as complete
     * @updates this
     * @requires taskName is in this
     * @ensures taskName's completion status is set to true
     */
    @Override
    public final void markTaskComplete(String taskName) {
        this.tasks.value(taskName).markComplete();
    }
    /**
     * Checks if a task exists in the Task Manager.
     *
     * @param taskName the name of the task to check
     * @return true if the task exists, false otherwise
     * @ensures hasTask = (taskName is in this)
     */
    @Override
    public final boolean hasTask(String taskName) {
        return this.tasks.hasKey(taskName);
    }


    @Override
    public void clear() {
        this.createNewRep();
    }

    @SuppressWarnings("unchecked")
    @Override
    public TaskManager newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }


    @Override
    public void transferFrom(TaskManager source) {
        assert source instanceof TaskManager1 : ""
                + "Violation of: source is of dynamic type TaskManager1";

        TaskManager1 localSource = (TaskManager1) source;
        this.tasks.transferFrom(localSource.tasks);
        localSource.createNewRep();
    }

}

