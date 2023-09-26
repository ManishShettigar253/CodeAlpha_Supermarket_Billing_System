import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class ShoppingCart {
    private List<Product> items = new ArrayList<>();

    public void addItem(Product product) {
        items.add(product);
    }

    public double calculateTotal() {
        double total = 0;
        for (Product item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public List<Product> getItems() {
        return items;
    }
}

public class SupermarketBillingSystem {
    private static List<Product> products = new ArrayList<>();
    private static ShoppingCart cart = new ShoppingCart();

    public static void main(String[] args) {
        initializeProducts();

        Scanner scanner = new Scanner(System.in);
        boolean doneShopping = false;

        while (!doneShopping) {
            displayMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayProducts();
                    break;
                case 2:
                    addToCart(scanner);
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    checkout();
                    doneShopping = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void initializeProducts() {
        products.add(new Product("Item 1", 10.0));
        products.add(new Product("Item 2", 5.0));
        products.add(new Product("Item 3", 8.0));
    }

    private static void displayMenu() {
        System.out.println("\nSupermarket Billing System Menu:");
        System.out.println("1. Display Products");
        System.out.println("2. Add to Cart");
        System.out.println("3. View Cart");
        System.out.println("4. Checkout and Exit");
        System.out.print("Enter your choice: ");
    }

    private static void displayProducts() {
        System.out.println("\nAvailable Products:");
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i).getName() + " - $" + products.get(i).getPrice());
        }
    }

    private static void addToCart(Scanner scanner) {
        displayProducts();
        System.out.print("Enter the product number to add to cart: ");
        int productNumber = scanner.nextInt();

        if (productNumber >= 1 && productNumber <= products.size()) {
            Product selectedProduct = products.get(productNumber - 1);
            cart.addItem(selectedProduct);
            System.out.println(selectedProduct.getName() + " added to cart.");
        } else {
            System.out.println("Invalid product number. Please try again.");
        }
    }

    private static void viewCart() {
        List<Product> items = cart.getItems();
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("\nCart Contents:");
            for (Product item : items) {
                System.out.println(item.getName() + " - $" + item.getPrice());
            }
            System.out.println("Total: $" + cart.calculateTotal());
        }
    }

    private static void checkout() {
        double total = cart.calculateTotal();
        System.out.println("\nThank you for shopping with us!");
        System.out.println("Total Amount: $" + total);
    }
}
