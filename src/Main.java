import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String[] products = {"Молоко", "Хлеб", "Гречка"};
        int[] prices = {50, 20, 100};
        Basket basket = new Basket(products, prices);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + " " + products[i] + " " + prices[i] + " руб/шт");
        }
        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                basket.saveBin(new File("basket.bin"));
                break;
            }
            String[] parts = input.split(" ");


            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
            if ((productNumber + 1) < 0 || (productNumber + 1) > products.length || productCount < 0) {
                System.out.println("Ведите корректные числа");
            }
            if (parts.length != 2) {
                basket.addToCart(productNumber, productCount);

            }

        }

        basket.printCart();
    }
}