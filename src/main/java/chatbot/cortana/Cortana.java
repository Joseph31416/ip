package chatbot.cortana;

import java.io.IOException;
import java.util.ArrayList;

import chatbot.storage.Storage;
import chatbot.task.Task;
import chatbot.task.TaskList;
import chatbot.ui.Ui;


/**
 * Represents the chatbot Cortana.
 */
public class Cortana {

    private static final String DATA_FOLDER = "data";
    private static final String SAVE_FILENAME = "tasks.csv";
    private static final String NAME = "Cortana";
    private TaskList taskList;
    private final String baseDir;
    private final String saveDirPath;
    private Storage storage;

    /**
     * Constructor for Cortana.
     * @param baseDir The base directory of the chatbot.
     */
    public Cortana(String baseDir) {
        this.baseDir = baseDir;
        this.saveDirPath = java.nio.file.Paths.get(this.baseDir, DATA_FOLDER).toString();
        this.storage = new Storage(saveDirPath, SAVE_FILENAME);
        this.taskList = new TaskList();
    }

    public String getGreetString() {
        return Ui.greet(Cortana.NAME);
    }

    public String getResponse(String input) {
        Command command = Parser.parseCommand(input);
        ArrayList<Task> tasks;
        int numTasks;
        Task currTask;
        String response;
        try {
            Parser.validateInput(command, input, this.taskList);
            switch (command) {
            case TODO:
                currTask = Parser.parseTodoTask(input);
                this.taskList.addTask(currTask);
                response = Ui.addTaskSuccess(currTask, this.taskList.getNumTasks());
                break;
            case DEADLINE:
                currTask = Parser.parseDeadlineTask(input);
                this.taskList.addTask(currTask);
                response = Ui.addTaskSuccess(currTask, this.taskList.getNumTasks());
                break;
            case EVENT:
                currTask = Parser.parseEventTask(input);
                this.taskList.addTask(currTask);
                response = Ui.addTaskSuccess(currTask, this.taskList.getNumTasks());
                break;
            case MARK:
                currTask = this.taskList.markTask(Parser.parseIndex(command, input));
                response = Ui.markTask(currTask);
                break;
            case UNMARK:
                currTask = this.taskList.unmarkTask(Parser.parseIndex(command, input));
                response = Ui.unmarkTask(currTask);
                break;
            case DELETE:
                currTask = this.taskList.deleteTask(Parser.parseIndex(command, input));
                response = Ui.deleteTask(currTask, this.taskList.getNumTasks());
                break;
            case LIST:
                tasks = this.taskList.getTasks();
                numTasks = this.taskList.getNumTasks();
                response = Ui.listTasks(tasks, numTasks);
                break;
            case FIND:
                tasks = this.taskList.findTasks(Parser.parseFindString(input));
                numTasks = tasks.size();
                response = Ui.listFindTasks(tasks, numTasks);
                break;
            default:
                response = "I'm sorry, but I don't know what that means :-(";
            }
            if (!(this.taskList.isSaved())) {
                this.storage.saveTaskList(this.taskList);
            }
        } catch (IOException e) {
            response = e.getMessage();
        }
        return response;
    }

}
