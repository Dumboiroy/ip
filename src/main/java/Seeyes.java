import java.util.Scanner;
import java.util.ArrayList;
public class Seeyes {
    public static String divider = "============================================================";
    public static ArrayList<String> list = new ArrayList<>();

    public static void printDivider() {
        System.out.println(divider);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printDivider();
        System.out.println("Hello, I'm Seeyes!\n" + "What can I do for you?");
        printDivider();
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                break;
            }
            if (userInput.equals("list")) {
                printDivider();
                System.out.println("Here are the items in your list:");
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) != null) {
                        System.out.println((i + 1) + ". " + list.get(i));
                    }
                }
                printDivider();
                continue;
            }
            printDivider();
            list.add(userInput);
            System.out.println("Added: " + userInput);
            printDivider();
        }
        printDivider();
        System.out.println("Bye. Hope to see you again soon!");
        printDivider();
        

    }
}
