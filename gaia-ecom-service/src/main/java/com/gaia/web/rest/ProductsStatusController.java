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
import com.gaia.domain.ProductsStatusEntity;
import com.gaia.service.ProductsStatusService;

@RestController
@RequestMapping(path = "/api/v1.0", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsStatusController {

	@Autowired
	private ProductsStatusService prodStatusServ;

	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@GetMapping("productsstatus/{id}")
	public ResponseEntity<ProductsStatusEntity> getProdStatus(@PathVariable long id) throws GaiaException {
		return new ResponseEntity<ProductsStatusEntity>(prodStatusServ.getProdStatus(id), HttpStatus.OK);
	}

	@GetMapping("productsstatus")
	public ResponseEntity<PagedResources<Resource<ProductsStatusEntity>>> getProdStatus(
			PagedResourcesAssembler<ProductsStatusEntity> assembler, @RequestParam Map<String, String> map,
			Pageable pageable) throws GaiaException {

		ProductsStatusEntity prodStatusEntity = dozerBeanMapper.map(map, ProductsStatusEntity.class);
		Map<String, Long> request = dozerBeanMapper.map(prodStatusEntity, Map.class);
		Page<ProductsStatusEntity> response = prodStatusServ.getProdStatus(request, pageable);
		if (response.getContent().isEmpty()) {
			throw new GaiaException(ErrorCodes.CODE_NO_DATA, ErrorCodes.MSG_NO_DATA);
		}
		return new ResponseEntity<>(assembler.toResource(response), HttpStatus.OK);
	}

}
