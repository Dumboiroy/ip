import java.util.Scanner;
public class Seeyes {
    public static String divider = "============================================================";

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
            printDivider();
            System.out.println("You said: " + userInput + ". What do you want me to do with that?");
            printDivider();
        }
        printDivider();
        System.out.println("Bye. Hope to see you again soon!");
        printDivider();
        

    }
}
