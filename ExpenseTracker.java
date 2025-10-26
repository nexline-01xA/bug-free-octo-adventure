// File: ExpenseTracker.java
import java.io.*;
import java.util.*;

class Expense {
    private String category;
    private double amount;

    public Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() { return category; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return category + " - $" + amount;
    }
}

public class ExpenseTracker {
    private static final String FILE_NAME = "expenses.txt";
    private static List<Expense> expenses = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadExpenses();
        int choice;
        do {
            System.out.println("\n===== Expense Tracker =====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Total");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> viewExpenses();
                case 3 -> viewTotal();
                case 4 -> saveExpenses();
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    private static void addExpense() {
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        expenses.add(new Expense(category, amount));
        System.out.println("Expense added successfully!");
    }

    private static void viewExpenses() {
        System.out.println("\n--- Expense List ---");
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    private static void viewTotal() {
        double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        System.out.println("Total Expenses: $" + total);
    }

    private static void saveExpenses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                writer.println(e.getCategory() + "," + e.getAmount());
            }
            System.out.println("Expenses saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    private static void loadExpenses() {
        try (Scanner fileScanner = new Scanner(new File(FILE_NAME))) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                expenses.add(new Expense(data[0], Double.parseDouble(data[1])));
            }
        } catch (FileNotFoundException ignored) {
        }
    }
}
