import java.util.Scanner;
import java.util.ArrayList;
public class Seeyes {
    public static String divider = "============================================================";
    public static ArrayList<Task> list = new ArrayList<>();

    public static void printDivider() {
        System.out.println(divider);
    }

    public static void handleUserInput(String input) {
        if (input.equals("list")) {
            printList();
        } else if (input.startsWith("mark")) {
            
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
        System.out.println("Hello, I'm Seeyes!\n" + "What can I do for you?");
        printDivider();
        while (true) {
            String userInput = scanner.nextLine();
            handleUserInput(userInput);
            if (userInput.equals("bye")) {
                break;
            }
            printDivider();
        }
        printDivider();
        System.out.println("Bye. Hope to see you again soon!");
        printDivider();

        // Terminate
        scanner.close();
    }
}
