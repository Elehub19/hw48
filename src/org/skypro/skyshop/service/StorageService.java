package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class StorageService {

    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        this.products = new HashMap<>();
        this.articles = new HashMap<>();
        fillTestData();
    }

    private void fillTestData() {
        Product apple = new SimpleProduct(UUID.randomUUID(), "Яблоко", 50);
        Product banana = new SimpleProduct(UUID.randomUUID(), "Банан", 70);
        Product milk = new DiscountedProduct(UUID.randomUUID(), "Молоко", 120, 20);
        Product tea = new FixPriceProduct(UUID.randomUUID(), "Чай");

        products.put(apple.getId(), apple);
        products.put(banana.getId(), banana);
        products.put(milk.getId(), milk);
        products.put(tea.getId(), tea);

        Article article1 = new Article(
                UUID.randomUUID(),
                "Польза яблок",
                "Яблоки полезны и содержат витамины."
        );

        Article article2 = new Article(
                UUID.randomUUID(),
                "Польза молока",
                "Молоко содержит кальций."
        );

        articles.put(article1.getId(), article1);
        articles.put(article2.getId(), article2);
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Article> getAllArticles() {
        return articles.values();
    }

    public Collection<Searchable> getAllSearchables() {
        List<Searchable> searchables = new ArrayList<>();

        searchables.addAll(products.values());
        searchables.addAll(articles.values());

        return searchables;
    }
}
