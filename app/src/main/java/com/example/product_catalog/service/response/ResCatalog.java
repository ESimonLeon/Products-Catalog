package com.example.product_catalog.service.response;

import java.io.Serializable;
import java.util.ArrayList;

public class ResCatalog implements Serializable {

    ResultCatalog results;

    public ResultCatalog getResults() {
        return results;
    }

    public static class ResultCatalog {

        ArrayList<ResultCatalogProducts> products;

        public ArrayList<ResultCatalogProducts> getProducts() {
            return products;
        }

        public static class ResultCatalogProducts {
            private Integer id;
            private String product_name;
            private Integer category_id;
            private String description;
            private Integer price;
            private String product_image;

            public Integer getId() {
                return id;
            }

            public String getProduct_name() {
                return product_name;
            }

            public Integer getCategory_id() {
                return category_id;
            }

            public String getDescription() {
                return description;
            }

            public Integer getPrice() {
                return price;
            }

            public String getProduct_image() {
                return product_image;
            }
        }
    }
}

