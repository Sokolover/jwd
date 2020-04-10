package by.training.sokolov.oop.basic_of_oop.task2;

import java.util.Scanner;

public class Payment {
    private String name;
    private int overallCost;
    private Product[] products;

    public Payment(String name) {
        this.name = name;
        System.out.println("\n" + this.name);
        System.out.println("_______________");
    }

    public Payment(){

    }

    public void collectProducts() {
        PaymentUI paymentUI = new PaymentUI();
        int productCounter = paymentUI.enterProductCounter();
        this.products = new Product[productCounter];

        for (int i = 0; i < productCounter; i++) {
            System.out.println("");
            products[i] = new Product();
            products[i].setProductName(paymentUI.enterProductName());
            products[i].setProductCost(paymentUI.enterProductCost());
            overallCost += products[i].getProductCost();
        }
    }

    class Product {
        private String productName;
        private int productCost;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductCost() {
            return productCost;
        }

        public void setProductCost(int productCost) {
            this.productCost = productCost;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOverallCost() {
        return overallCost;
    }

    public void setOverallCost(int overallCost) {
        this.overallCost = overallCost;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }
}
