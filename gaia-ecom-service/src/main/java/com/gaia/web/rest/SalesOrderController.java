package com.gaia.web.rest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaia.common.ErrorCodes;
import com.gaia.common.GaiaException;
import com.gaia.domain.SalesOrderEntity;
import com.gaia.service.SalesOrderService;
import com.gaia.web.rest.vm.ResponseVm;

@RestController
@RequestMapping(path = "/api/v1.0", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesOrderController {

	@Autowired
	private SalesOrderService salesServ;

	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@PostMapping("salesorder")
	public ResponseEntity<SalesOrderEntity> add(@RequestBody SalesOrderEntity salesEntity) throws GaiaException {
		return new ResponseEntity<SalesOrderEntity>(salesServ.addSalesOrder(salesEntity), HttpStatus.OK);
	}

	@GetMapping("salesorder/{id}")
	public ResponseEntity<SalesOrderEntity> getSalesOrder(@PathVariable long id) throws GaiaException {
		return new ResponseEntity<SalesOrderEntity>(salesServ.getSalesOrder(id), HttpStatus.OK);
	}

	@GetMapping("salesorder")
	public ResponseEntity<PagedResources<Resource<SalesOrderEntity>>> getSalesOrder(
			PagedResourcesAssembler<SalesOrderEntity> assembler, @RequestParam Map<String, String> map,
			@RequestParam(name = "id", required = false) Long id,
			@RequestParam(name = "customerId", required = false) Long customerId,
			@RequestParam(name = "websiteId", required = false) Long websiteId,
			@RequestParam(name = "totalItems", required = false) Long totalItems,
			@RequestParam(name = "totalItemsQty", required = false) Long totalItemsQty,

			@RequestParam(name = "grandTotal", required = false) BigDecimal grandTotal,
			@RequestParam(name = "shippingAmount", required = false) BigDecimal shippingAmount,
			@RequestParam(name = "taxAmount", required = false) BigDecimal taxAmount,
			@RequestParam(name = "codCharges", required = false) BigDecimal codCharges, Pageable pageable)
			throws GaiaException {
		Map<String, Long> longMap = new HashMap<String, Long>();
		Map<String, BigDecimal> decimalMap = new HashMap<String, BigDecimal>();

		if (map.containsKey("id")) {
			longMap.put("id", id);
			map.remove("id");
		}

		if (map.containsKey("customerId")) {
			longMap.put("customerId", customerId);
			map.remove("customerId");
		}

		if (map.containsKey("websiteId")) {
			longMap.put("websiteId", websiteId);
			map.remove("websiteId");
		}

		if (map.containsKey("totalItems")) {
			longMap.put("totalItems", totalItems);
			map.remove("totalItems");
		}
		if (map.containsKey("totalItemsQty")) {
			longMap.put("totalItemsQty", totalItemsQty);
			map.remove("totalItemsQty");
		}
		if (map.containsKey("grandTotal")) {
			decimalMap.put("grandTotal", grandTotal);
			map.remove("grandTotal");
		}

		if (map.containsKey("shippingAmount")) {
			decimalMap.put("shippingAmount", shippingAmount);
			map.remove("shippingAmount");
		}

		if (map.containsKey("taxAmount")) {
			decimalMap.put("taxAmount", taxAmount);
			map.remove("taxAmount");
		}

		if (map.containsKey("codCharges")) {
			decimalMap.put("codCharges", codCharges);
			map.remove("codCharges");
		}

		SalesOrderEntity salesReq = dozerBeanMapper.map(map, SalesOrderEntity.class);
		Map<String, String> request = dozerBeanMapper.map(salesReq, Map.class);
		Page<SalesOrderEntity> response = salesServ.getSalesOrder(request, pageable, longMap, decimalMap);
		if (response.getContent().isEmpty()) {
			throw new GaiaException(ErrorCodes.CODE_NO_DATA, ErrorCodes.MSG_NO_DATA);
		}
		return new ResponseEntity<>(assembler.toResource(response), HttpStatus.OK);
	}

	@DeleteMapping("salesorder/{id}")
	public ResponseEntity<ResponseVm> deleteSalesOrder(@PathVariable long id) {
		salesServ.deleteSalesOrder(id);
		return new ResponseEntity<ResponseVm>(ResponseVm.getSuccessVm(), HttpStatus.OK);
	}

	@PutMapping("salesorder/{id}")
	public ResponseEntity<ResponseVm> updateSalesOrder(@RequestBody SalesOrderEntity salesEntity, @PathVariable long id)
			throws GaiaException {
		SalesOrderEntity oldSalesDetails = salesServ.getSalesOrder(id);
		if (oldSalesDetails == null)
			return ResponseEntity.notFound().build();
		salesEntity.setCreatedAt(oldSalesDetails.getCreatedAt());
		salesEntity.setUpdatedAt(LocalDateTime.now());
		salesServ.addSalesOrder(salesEntity);
		return new ResponseEntity<ResponseVm>(ResponseVm.getSuccessVm(), HttpStatus.OK);
	}

}
