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
import com.gaia.domain.ProductAttrEntity;
import com.gaia.service.ProductAttrService;

@RestController
@RequestMapping(path = "/api/v1.0", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductAttrController {

	@Autowired
	private ProductAttrService productAttrService;

	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@GetMapping("productattr/{id}")
	public ResponseEntity<ProductAttrEntity> getProductAttr(@PathVariable long id) throws GaiaException {
		return new ResponseEntity<ProductAttrEntity>(productAttrService.getProductAttr(id), HttpStatus.OK);
	}

	@GetMapping("productattr")
	public ResponseEntity<PagedResources<Resource<ProductAttrEntity>>> getProductAttr(
			PagedResourcesAssembler<ProductAttrEntity> assembler, @RequestParam(name = "id", required = false) Long id,
			@RequestParam(name = "productId", required = false) Long productId, @RequestParam Map<String, String> map,
			Pageable pageable) throws GaiaException {
		if (map.containsKey("id"))
			map.remove("id");
		if (map.containsKey("productId"))
			map.remove("productId");
		ProductAttrEntity prodEntity = dozerBeanMapper.map(map, ProductAttrEntity.class);
		Map<String, String> request = dozerBeanMapper.map(prodEntity, Map.class);
		Page<ProductAttrEntity> response = productAttrService.getProductAttr(request, id, productId, pageable);

		if (response.getContent().isEmpty()) {
			throw new GaiaException(ErrorCodes.CODE_NO_DATA, ErrorCodes.MSG_NO_DATA);
		}

		return new ResponseEntity<>(assembler.toResource(response), HttpStatus.OK);
	}

}
