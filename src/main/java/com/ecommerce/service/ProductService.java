package com.ecommerce.service;

import com.ecommerce.dto.VendorProductCountDto;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.model.Vendor;
import com.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Category getCategoryById(int categoryId) {
        return productRepository.getCategoryById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Vendor getVendorById(int vendorId) {
        return productRepository.getVendorById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public Product getProductById(int productId) {
        return productRepository.getProductById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void updateStockQuantity(int productId, int stockQuantity) {
        Product product = getProductById(productId);
        product.setStockQuantity(stockQuantity);
        productRepository.updateStockQuantity(product);
    }

    public List<VendorProductCountDto> getProductCountByVendors() {
        return productRepository.getProductCountByVendors();
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }
}
