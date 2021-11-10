package com.example.product_catalog.service.response;

import java.io.Serializable;
import java.util.ArrayList;

public class ResCategories implements Serializable {

    ArrayList<ResultCategories> categories = new ArrayList<>();

    public ArrayList<ResultCategories> getCategories() {
        return categories;
    }

    public static class ResultCategories {
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
