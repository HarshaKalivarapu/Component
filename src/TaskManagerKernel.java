import components.standard.Standard;

/**
 * Task Manager kernel component with primary methods.
 * (Note: by package-wide convention, all references are non-null.)
 *
 * @mathmodel type TaskManagerKernel is modeled by a finite set of tasks
 * @initially <pre>
 * ():
 *  ensures
 *   this = {}
 * </pre>
 */
public interface TaskManagerKernel extends Standard<TaskManager> {

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
    interface Task {

        /**
         * Gets the name of this task.
         *
         * @return the task name
         * @ensures getName = this.name
         */
        String getName();

        /**
         * Gets the priority level of this task.
         *
         * @return the task priority
         * @ensures getPriority = this.priority
         */
        int getPriority();

        /**
         * Gets the due date of this task.
         *
         * @return the task due date
         * @ensures getDueDate = this.dueDate
         */
        String getDueDate();

        /**
         * Checks if this task is complete.
         *
         * @return true if the task is complete, false otherwise
         * @ensures isComplete = this.isComplete
         */
        boolean isComplete();

        /**
         * Sets the priority of this task.
         *
         * @param priority the new priority level
         * @requires priority >= 0
         * @ensures this.priority = priority
         */
        void setPriority(int priority);

        /**
         * Sets the due date of this task.
         *
         * @param dueDate the new due date
         * @ensures this.dueDate = dueDate
         */
        void setDueDate(String dueDate);

        /**
         * Marks this task as complete.
         *
         * @ensures this.isComplete = true
         */
        void markComplete();
    }

    /**
     * Adds a new task with the given name, priority, and due date.
     *
     * @param taskName
     *              the name of the task
     * @param priority
     *              the priority level of the task
     * @param dueDate
     *              the due date of the task
     * @updates this
     * @requires taskName is not in DOMAIN(this)
     * @ensures this = #this union { (taskName, priority, dueDate, false) }
     */
    void addTask(String taskName, int priority, String dueDate);

    /**
     * Removes the task with the given name.
     *
     * @param taskName
     *              the name of the task to be removed
     * @updates this
     * @requires taskName is in this
     * @ensures this = #this - { (taskName, priority, dueDate, isComplete) }
     */
    void removeTask(String taskName);

    /**
     * Checks if the given task is complete.
     *
     * @param taskName
     *              the name of the task
     * @return true if the task is complete, false otherwise
     * @requires taskName is in this
     * @ensures isTaskComplete = (completion status of taskName)
     */
    boolean isTaskComplete(String taskName);

    /**
     * Marks the given task as complete.
     *
     * @param taskName
     *              the name of the task to be marked as complete
     * @updates this
     * @requires taskName is in this
     * @ensures taskName's completion status is set to true
     */
    void markTaskComplete(String taskName);

    /**
     * Checks if a task exists in the Task Manager.
     *
     * @param taskName the name of the task to check
     * @return true if the task exists, false otherwise
     * @ensures hasTask = (taskName is in this)
     */
    boolean hasTask(String taskName);
}
