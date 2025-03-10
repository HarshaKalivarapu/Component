import components.map.Map;
import components.map.Map1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * @author Harsha Kalivarapu
 */
public class TaskManagerComp2 {
    // This is an internal Task class to make each task
    private static class Task {

        /**
         * Represents the task's name as {@code name}
         */
        private String name;

        /**
         * Represents the task's priority level as {@code priority}
         */
        private int priority;

        /**
         * Represents the task's due date as {@code dueDate}
         */
        private String dueDate;

        /**
         * Represents the task's completion status as {@code isComplete}
         */
        private boolean isComplete;

        /**
         * Creates a Task object
         */
        Task(String name, int priority, String dueDate) {
            this.name = name;
            this.priority = priority;
            this.dueDate = dueDate;
            this.isComplete = false;
        }

        //These are getter methods
        public String getName() {
            return this.name;
        }

        public int getPriority() {
            return this.priority;
        }

        public String getDueDate() {
            return this.dueDate;
        }

        public boolean isComplete() {
            return this.isComplete;
        }

        //These are setter methods
        public void setPriority(int priority) {
            this.priority = priority;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        public void markComplete() {
            this.isComplete = true;
        }
    }

    //This is a map that stores all of the Tasks, labeled by their name
    private final Map<String, Task> tasks;

    //This is a sequence to store a list of tasks (may not be needed)
    private final Sequence<Task> taskList;


    //Constructor
    public TaskManagerComp2() {
        this.tasks = new Map1L<>();
        this.taskList = new Sequence1L<>(); //this may not be needed
    }

    //These are kernel methods
    //adds a task to the tasks Map and taskList sequence
    public void addTask(String taskName, int priority, String dueDate) {
        if (!this.tasks.hasKey(taskName)) {
            Task newTask = new Task(taskName, priority, dueDate);
            this.tasks.add(taskName, newTask);
            this.taskList.add(this.taskList.length(), newTask);
        }
    }

    //removes a task from tasks Map and taskList sequence
    public void removeTask(String taskName) {
        if (this.tasks.hasKey(taskName)) {
            this.tasks.remove(taskName).value();

            for (int i = 0; i < this.taskList.length(); i++) {
                if (this.taskList.entry(i).getName().equals(taskName)) {
                    this.taskList.remove(i);
                }
            }
        }
    }

    /*
     * checks if the given task is complete. Precondition would be that the given
     * task must exist in the map
     */
    public boolean isTaskComplete(String taskName) {
        boolean result = this.tasks.value(taskName).isComplete();;
        return result;
    }

    //marks the given task as complete. Precondition will be that the given task must exist in the map
    public void markTaskComplete(String taskName) {
        this.tasks.value(taskName).markComplete();
    }


    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        TaskManager tm = new TaskManager();

        // Adding tasks
        tm.addTask("Task1", 5, "02-20-2025");
        tm.addTask("Task2", 3, "02-21-2025");

        // Marking Task1 as complete
        tm.markTaskComplete("Task1");

        // Checking if tasks are complete
        out.println("Is Task1 Complete? " + tm.isTaskComplete("Task1"));
        out.println("Is Task2 Complete? " + tm.isTaskComplete("Task2"));

        // Removing Task1
        tm.removeTask("Task1");

        // Trying to check status of removed task
        out.println("Is Task1 Still Present In Map? " + tm.tasks.hasKey("Task1"));

        out.close();
    }
}
