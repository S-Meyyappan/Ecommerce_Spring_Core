package com.ecommerce;

import com.ecommerce.dto.VendorProductCountDto;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.model.Vendor;
import com.ecommerce.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Scanner in = new Scanner(System.in);

        ProductService productService = context.getBean(ProductService.class);

        while(true) {
            System.out.println("---------------------Ecommerce Application---------------------");
            System.out.println("1. Create new Product");
            System.out.println("2. Get Product by Id");
            System.out.println("3. Update StockQuantity");
            System.out.println("4. Get Product Count by Vendors");
            System.out.println("0. Exit");
            System.out.println("---------------------------------------------------------------");
            System.out.println("Enter your choice: ");
            int choice = in.nextInt();

            if(choice == 0) {
                break;
            }

            switch (choice){
                case 1 -> {
                    System.out.println("---------------------Add new Product---------------------");
                    System.out.println("Enter Product Name: ");
                    String name = in.next();
                    System.out.println("Enter Product Price: ");
                    BigDecimal price = in.nextBigDecimal();
                    System.out.println("Enter Product Stock Quantity: ");
                    int stockQuantity = in.nextInt();
                    in.nextLine();
                    System.out.println("Enter Category Name");
                    String categoryName = in.nextLine();
                    System.out.println("Enter Vendor Name: ");
                    String vendorName = in.nextLine();

                    Category category = null;
                    Vendor vendor = null;
                    try {
                        category = productService.getOrCreateCategoryByName(categoryName);
                        vendor = productService.getOrCreateVendorByName(vendorName);
                    } catch (Exception e) {
                        System.out.println("Category or Vendor not found"+e.getMessage());
                    }

                    Product product = new Product();
                    product.setName(name);
                    product.setPrice(price);
                    product.setStockQuantity(stockQuantity);
                    product.setCategory(category);
                    product.setVendor(vendor);

                    try {
                        productService.addProduct(product);
                        System.out.println("Product added successfully");
                    } catch (Exception e) {
                        System.out.println("Product not added"+e.getMessage());
                    }

                }
                case 2 -> {
                    System.out.println("---------------------Get Product by Id---------------------");
                    System.out.println("Enter productId :");
                    int productId = in.nextInt();
                    try{
                        Product product = productService.getProductById(productId);
                        System.out.println(product);
                    } catch (Exception e) {
                        System.out.println("Unable to get product by id : "+e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("---------------------Update Product Stock Quantity---------------------");
                    System.out.println("Enter productId :");
                    int productId = in.nextInt();
                    System.out.println("Enter new stock count :");
                    int stockQty = in.nextInt();
                    try{
                        productService.updateStockQuantity(productId,stockQty);
                        System.out.println("Stock quantity updated successfully");
                    } catch (Exception e) {
                        System.out.println("Unable to update stock quantity : "+e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.println("---------------------Get Product Count by Vendors---------------------");
                    System.out.println("Vendorname\tProductCount");
                    List<VendorProductCountDto> list = productService.getProductCountByVendors();
                    list.forEach((dto) -> {
                        System.out.println(dto.vendorName() + "\t" + dto.productCount());
                    });
                }
            }
        }
        context.close();
    }
}
