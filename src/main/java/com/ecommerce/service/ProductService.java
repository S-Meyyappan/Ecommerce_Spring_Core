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

    public Category getOrCreateCategoryByName(String categoryName) {
        if(productRepository.getCategoryByName(categoryName).isPresent()) {
            System.out.println("Category found");
            return productRepository.getCategoryByName(categoryName).get();
        } else {
            System.out.println("Category not found");
            System.out.println("Creating new Category....");
            return productRepository.createCategory(categoryName);
        }
    }

    public Vendor getOrCreateVendorByName(String vendorName) {
        if(productRepository.getVendorByName(vendorName).isPresent()) {
            System.out.println("Vendor found");
            return productRepository.getVendorByName(vendorName).get();
        } else {
            System.out.println("Vendor not found");
            System.out.println("Creating new Vendor....");
            return productRepository.createVendor(vendorName);
        }
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
}
