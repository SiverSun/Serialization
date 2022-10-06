import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Boolean load = true;
        String textFile = null;
        String fileForm = null;
        String[] products = {"Молоко", "Хлеб", "Гречка"};
        int[] prices = {50, 20, 100};
        Basket basket = new Basket(products, prices);
        Scanner scanner = new Scanner(System.in);
        ClientLog clientLog = new ClientLog();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("shop.xml");
        XPath xPath = XPathFactory.newInstance().newXPath();
        String loadFileName = xPath.compile("/config/load/fileName").evaluate(doc);
        boolean loadFileEnabled = Boolean.parseBoolean(xPath.compile("/config/load/enabled").evaluate(doc));
        String loadFileLog = xPath.compile("/config/log/fileName").evaluate(doc);
        boolean logFileEnabled = Boolean.parseBoolean(xPath.compile("/config/log/enabled").evaluate(doc));

        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + " " + products[i] + " " + prices[i] + " руб/шт");
        }
        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                basket.saveJson(new File("basket.json"));
                clientLog.exportAsCSV(new File("log.csv"));
                break;
            }
            String[] parts = input.split(" ");


            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
            clientLog.log(productNumber, productCount);
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