import java.io.*;

public class Basket {
    private String[] products;
    private int[] prices;
    private int[] basket;

   Basket basket1 = loadFromTxtFile(new File("basket.txt"));

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.basket = new int[products.length];

    }

    public Basket(String[] products, int[] prices, int[] basket) {
      this.products = products;
        this.prices = prices;
        this.basket = basket;
    }

    public void addToCart(int productNumber, int productCount) {
        basket[productNumber] += productCount;

    }

    public void printCart() {
        System.out.println("Ваша корзина: ");
        int sumProducts = 0;
        for (int i = 0; i < basket.length; i++) {
            if (basket[i] > 0) {
                System.out.println(products[i] + " " + basket[i] + " шт " + prices[i] + " руб/шт " + "в сумме: " + (basket[i] * prices[i]) + " руб");
                sumProducts += basket[i] * prices[i];
            }
        }
        System.out.println("Итог: " + sumProducts + " руб.");
    }

    public void saveTxt(File textFile) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(textFile))) {
            for (int i = 0; i < products.length; i++) {
                out.write(products[i] + " " + basket[i] + " " + prices[i] + "\r\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromTxtFile(File textFile)  {
        Basket result = null;
        if (textFile.exists() & textFile.length() != 0) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));) {
                int count = 0;
                int size = 3;
                String[] products = new String[size];
                int[] basket = new int[size];
                int[] prices = new int[size];
                String read;
                while ((read = bufferedReader.readLine()) != null) {
                    String[] strings = read.split(" ");
                    products[count] = strings[0];
                    basket[count] = Integer.parseInt(strings[1]);
                    prices[count] = Integer.parseInt(strings[2]);
                    count++;
                }
                result = new Basket(products, prices, basket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getPrices() {
        return prices;
    }

    public int[] getBasket() {
        return basket;
    }
}
