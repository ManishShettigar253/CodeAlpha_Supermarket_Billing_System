import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

    public void clearItems() {
        items.clear();
    }
}

public class SupermarketBillingSystemGUI {
    private static List<Product> products = new ArrayList<>();
    private static ShoppingCart cart = new ShoppingCart();

    private static JTextArea cartTextArea;
    private static JPanel productPanel;

    public static void main(String[] args) {
        initializeProducts();

        JFrame frame = new JFrame("Supermarket Billing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(153, 204, 255));
        buttonPanel.setPreferredSize(new Dimension(150, 0));

        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));

        cartTextArea = new JTextArea(10, 30);
        cartTextArea.setEditable(false);
        cartTextArea.setBackground(new Color(240, 240, 240));
        cartTextArea.setFont(new Font("Arial", Font.PLAIN, 24)); // Increase font size

        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(productPanel, BorderLayout.CENTER);
        mainPanel.add(cartTextArea, BorderLayout.EAST);

        frame.add(mainPanel);

        addButtons(buttonPanel);

        frame.setVisible(true);
    }

    private static void initializeProducts() {
        products.add(new Product("Item 1", 10.0));
        products.add(new Product("Item 2", 5.0));
        products.add(new Product("Item 3", 8.0));
    }

    private static void addButtons(JPanel panel) {
        addButton(panel, "Display Products", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearDisplay();
                displayProducts();
            }
        });

        addButton(panel, "Add to Cart", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });

        addButton(panel, "View Cart", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearDisplay();
                viewCart();
            }
        });

        addButton(panel, "Remove from Cart", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeFromCart();
            }
        });

        addButton(panel, "Checkout", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkout();
            }
        });
    }

    private static void addButton(JPanel panel, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        panel.add(button);
    }

    private static void displayProducts() {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            JLabel label = new JLabel((i + 1) + ". " + product.getName() + " - $" + product.getPrice());
            label.setFont(new Font("Arial", Font.PLAIN, 24)); // Increase font size
            productPanel.add(label);
        }
    }

    private static void addToCart() {
        int selectedProductIndex = Integer.parseInt(JOptionPane.showInputDialog("Enter the product number to add to cart: ")) - 1;
        if (selectedProductIndex >= 0 && selectedProductIndex < products.size()) {
            Product selectedProduct = products.get(selectedProductIndex);
            cart.addItem(selectedProduct);
            JOptionPane.showMessageDialog(null, selectedProduct.getName() + " added to cart.");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid product number. Please try again.");
        }
    }

    private static void viewCart() {
        List<Product> items = cart.getItems();
        if (items.isEmpty()) {
            cartTextArea.setText("Your cart is empty.");
        } else {
            StringBuilder cartInfo = new StringBuilder("Cart Contents:\n");
            for (Product item : items) {
                cartInfo.append(item.getName()).append(" - $").append(item.getPrice()).append("\n");
            }
            cartInfo.append("Total: $").append(cart.calculateTotal());
            cartTextArea.setText(cartInfo.toString());
        }
    }

    private static void removeFromCart() {
        if (cart.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Your cart is empty.");
            return;
        }

        int selectedProductIndex = Integer.parseInt(JOptionPane.showInputDialog("Enter the item number to remove from the cart: ")) - 1;

        if (selectedProductIndex >= 0 && selectedProductIndex < cart.getItems().size()) {
            Product removedProduct = cart.getItems().remove(selectedProductIndex);
            JOptionPane.showMessageDialog(null, removedProduct.getName() + " removed from the cart.");
            viewCart();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid item number. Please try again.");
        }
    }

    private static void checkout() {
        double total = cart.calculateTotal();
        JOptionPane.showMessageDialog(null, "Thank you for shopping with us!\nTotal Amount: $" + total);
        System.exit(0);
    }

    private static void clearDisplay() {
        productPanel.removeAll();
        productPanel.revalidate();
        productPanel.repaint();
        cartTextArea.setText("");
    }
}
