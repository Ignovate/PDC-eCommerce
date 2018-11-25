package com.gaia.web.rest;

import java.util.Map;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaia.common.ErrorCodes;
import com.gaia.common.GaiaException;
import com.gaia.domain.ProductEntity;
import com.gaia.service.ProductService;

@RestController
@RequestMapping(path = "/api/v1.0", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@GetMapping("product/{id}")
	public ResponseEntity<ProductEntity> getProduct(@PathVariable long id) throws GaiaException {
		return new ResponseEntity<ProductEntity>(productService.getProduct(id), HttpStatus.OK);
	}

	@GetMapping("product")
	public ResponseEntity<PagedResources<Resource<ProductEntity>>> getProduct(
			PagedResourcesAssembler<ProductEntity> assembler,@RequestParam(name = "sku", required = false) String sku,
			 @RequestParam Map<String, String> map,
			Pageable pageable) throws GaiaException {
		if(map.containsKey("sku"))
			map.remove("sku");
		ProductEntity prodEntity = dozerBeanMapper.map(map, ProductEntity.class);
		Map<String, Long> request = dozerBeanMapper.map(prodEntity, Map.class);
		Page<ProductEntity> response = productService.getProduct(request,sku, pageable);
		
		if (response.getContent().isEmpty()) {
			throw new GaiaException(ErrorCodes.CODE_NO_DATA, ErrorCodes.MSG_NO_DATA);
		}

		return new ResponseEntity<>(assembler.toResource(response), HttpStatus.OK);
	}

}
