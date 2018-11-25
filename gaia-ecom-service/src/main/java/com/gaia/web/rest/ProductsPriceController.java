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
import com.gaia.domain.ProductsPriceEntity;
import com.gaia.service.ProductsPriceService;

@RestController
@RequestMapping(path = "/api/v1.0", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsPriceController {

	@Autowired
	private ProductsPriceService prodPriceServ;

	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@GetMapping("productsprice/{id}")
	public ResponseEntity<ProductsPriceEntity> getProdPrice(@PathVariable long id) throws GaiaException {
		return new ResponseEntity<ProductsPriceEntity>(prodPriceServ.getProdPrice(id), HttpStatus.OK);
	}

	@GetMapping("productsprice")
	public ResponseEntity<PagedResources<Resource<ProductsPriceEntity>>> getProdPrice(
			PagedResourcesAssembler<ProductsPriceEntity> assembler, @RequestParam Map<String, String> map,
			Pageable pageable) throws GaiaException {

		ProductsPriceEntity prodPriceEntity = dozerBeanMapper.map(map, ProductsPriceEntity.class);
		Map<String, Long> request = dozerBeanMapper.map(prodPriceEntity, Map.class);
		Page<ProductsPriceEntity> response = prodPriceServ.getProdPrice(request, pageable);
		if (response.getContent().isEmpty()) {
			throw new GaiaException(ErrorCodes.CODE_NO_DATA, ErrorCodes.MSG_NO_DATA);
		}
		return new ResponseEntity<>(assembler.toResource(response), HttpStatus.OK);
	}

}
