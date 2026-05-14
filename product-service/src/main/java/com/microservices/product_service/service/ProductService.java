package com.microservices.product_service.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.product_service.entity.Product;
import com.microservices.product_service.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repo;
	
	
	
	public Product saveProduct(Product product) {
		return repo.save(product);
	}
	
	public List<Product> getAllProduct(){
		return repo.findAll();
	}

	  public Product getProduct(Long id) {
	        return repo.findById(id).orElse(null);
	    }

	
}
