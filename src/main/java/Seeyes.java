import java.util.Scanner;
import java.util.ArrayList;
public class Seeyes {
    public static final String divider = "============================================================";
    public static ArrayList<Task> list = new ArrayList<>();

    public static void printDivider() {
        System.out.println(divider);
    }

    public static void say(String str) {
        System.out.println("> " + str);
    }

    public static void handleUserInput(String input) {
        String[] split = input.split(" ", 2);
        if (split.length < 2) {
            if (split[0].equals("list")) {
                // print the list of tasks
                printList();
                return;
            }
            say("Invalid command/task type: '" + input + "'");
            return;
        }
        String command = split[0].trim();
        if (command.equals("mark") || command.equals("unmark") || command.equals("delete")) {
            // mark, unmark or delete tasks
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            if (index >= 0 && index < list.size()) {
                if (command.equals("mark")) {
                    list.get(index).markAsDone();
                    say("Poggers. Let's check this off:\n" + list.get(index) + "\nKeep it up!");
                } else if (command.equals("unmark")) {
                    list.get(index).markAsNotDone();
                    say("Shag. Ok, I've unmarked this task:\n " + list.get(index) + "\nKeep your head up king.");
                } else if (command.equals("delete")) {
                    Task toBeRemovedTask = list.get(index);
                    list.remove(index);
                    say("Ok bro let's get rid of it. REMOVED: " + toBeRemovedTask);
                }
            } else {
                say("invalid task number. type 'list' to see list of tasks.");
            }
        } else {
            String paramsString = split[1].trim();
            // add task to list of tasks
            String taskType = command;
            switch(taskType) {
                case "todo":
                    addToList(new ToDoTask(paramsString.trim()));
                    break;
                case "deadline":
                    System.out.println(paramsString);
                    String[] params = paramsString.split("/by");
                    if (params.length < 2) {
                        say("new deadline not added. needs at least a name and a due date.");
                        return;
                    }
                    String name = params[0].trim();
                    String by = params[1].trim();
                    addToList(new DeadlineTask(name, by));
                    break;
                case "event":
                    String[] extracted_name = paramsString.split("/from");
                    if (extracted_name.length < 2) {
                        say("new event not added. specify a start and end date for this event.");
                        return;
                    }
                    String[] extracted_from = extracted_name[1].split("/to");
                    if (extracted_from.length < 2) {
                        say("new event not added. specify a start and end date for this event.");
                        return;
                    }
                    addToList(new EventTask(extracted_name[0].trim(), extracted_from[0].trim(), extracted_from[1].trim()));
                    break;
                default:
                    say("> Invalid command/task type: '" + input + "'");
                    return;
            };
        }
    }

    public static void printList() {
        printDivider();
        if (list.size() == 0) {
            say("list is empty! add your first item with 'todo [item]'.");
        }
        say("Here are the items in your list:");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                say((i + 1) + ". " + list.get(i));
            }
        }
    }

    public static void addToList(Task task) {
        list.add(task);
        say("Added: " + task);
        say("You now have " + list.size() + " items in your list.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printDivider();
        say("Yo, I'm Seeyes!");
        say("How can I help?");
        printDivider();
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                break;
            }
            handleUserInput(userInput);
            printDivider();
        }
        printDivider();
        say("See you around bro!");
        printDivider();

        // Terminate
        scanner.close();
    }
}
