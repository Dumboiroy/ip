import java.util.Scanner;
import java.util.ArrayList;
public class Seeyes {
    public static String divider = "============================================================";
    public static ArrayList<Task> list = new ArrayList<>();

    public static void printDivider() {
        System.out.println(divider);
    }

    public static void handleUserInput(String input) {
        // print the list of tasks
        if (input.equals("list")) {
            printList();
        // mark or unmark tasks
        } else if (input.startsWith("mark") || input.startsWith("unmark")) {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            if (index >= 0 && index < list.size()) {
                if (input.startsWith("mark")) {
                    list.get(index).markAsDone();
                    System.out.println("Poggers. Let's check this off:\n" + list.get(index) + "\nKeep it up!");
                } else {
                    list.get(index).markAsNotDone();
                    System.out.println("Shag. Ok, I've unmarked this task:\n " + list.get(index) + "\nKeep your head up king.");
                }
            }
        // add task to list of tasks
        } else {
            addToList(input);
        }
    }

    public static void printList() {
        printDivider();
        System.out.println("Here are the items in your list:");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                System.out.println((i + 1) + ". " + list.get(i));
            }
        }
    }

    public static void addToList(String name) {
        Task item = new Task(name);
        list.add(item);
        System.out.println("Added: " + item);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printDivider();
        System.out.println("Yo, I'm Seeyes!\n" + "How can I help?");
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
        System.out.println("See you around bro!");
        printDivider();

        // Terminate
        scanner.close();
    }
}
