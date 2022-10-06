import com.google.gson.Gson;

import java.io.*;
import java.util.Scanner;

public class Basket implements Serializable {
    private String[] products;
    private int[] prices;
    private int[] basket;

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
        try (BufferedWriter out = new BufferedWriter(new FileWriter("basket.json"))) {
            for (int i = 0; i < products.length; i++) {
                out.write(products[i] + " " + basket[i] + " " + prices[i] + "\r\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromTxtFile() throws Exception {
        Basket result = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("basket.json"));) {
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
    public void saveJson(File jsonFile) throws IOException {
        try (PrintWriter out = new PrintWriter(jsonFile)){
            Gson gson = new Gson();
            String json = gson.toJson(this);
            out.println(json);
        }
    }
    public static Basket loadFromJson(File jsonFile)  throws IOException {
        try (Scanner scanner = new Scanner(jsonFile)){
            String json = scanner.nextLine();
            Gson gson = new Gson();
            return gson.fromJson(json, Basket.class);
        }
    }
}
