package com.gaia.web.rest;

import java.time.LocalDateTime;
import java.util.HashMap;
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
import com.gaia.domain.SalesOrderPaymentEntity;
import com.gaia.service.SalesOrderPaymentService;
import com.gaia.web.rest.vm.ResponseVm;

@RestController
@RequestMapping(path = "/api/v1.0", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesOrderPaymentController {

	@Autowired
	private SalesOrderPaymentService salesPayServ;

	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@PostMapping("salesorderpayment")
	public ResponseEntity<SalesOrderPaymentEntity> add(@RequestBody SalesOrderPaymentEntity salesPayEntity)
			throws GaiaException {
		return new ResponseEntity<SalesOrderPaymentEntity>(salesPayServ.addSalesOrderPay(salesPayEntity),
				HttpStatus.OK);
	}

	@GetMapping("salesorderpayment/{id}")
	public ResponseEntity<SalesOrderPaymentEntity> getSalesOrderPay(@PathVariable long id) throws GaiaException {
		return new ResponseEntity<SalesOrderPaymentEntity>(salesPayServ.getSalesOrderPay(id), HttpStatus.OK);
	}

	@GetMapping("salesorderpayment")
	public ResponseEntity<PagedResources<Resource<SalesOrderPaymentEntity>>> getSalesOrderPay(
			PagedResourcesAssembler<SalesOrderPaymentEntity> assembler,
			@RequestParam(name = "id", required = false) Long id,
			@RequestParam(name = "orderId", required = false) Long orderId,
			@RequestParam(name = "ccExpMonth", required = false) Long ccExpMonth,
			@RequestParam(name = "ccExpYear", required = false) Long ccExpYear, @RequestParam Map<String, String> map,
			Pageable pageable) throws GaiaException {

		Map<String, Long> longMap = new HashMap<String, Long>();

		if (map.containsKey("id")) {
			longMap.put("id", id);
			map.remove("id");
		}

		if (map.containsKey("orderId")) {
			longMap.put("orderId", orderId);
			map.remove("orderId");
		}

		if (map.containsKey("ccExpMonth")) {
			longMap.put("ccExpMonth", ccExpMonth);
			map.remove("ccExpMonth");
		}

		if (map.containsKey("ccExpYear")) {
			longMap.put("ccExpYear", ccExpYear);
			map.remove("ccExpYear");
		}

		SalesOrderPaymentEntity salesPayReq = dozerBeanMapper.map(map, SalesOrderPaymentEntity.class);
		Map<String, String> request = dozerBeanMapper.map(salesPayReq, Map.class);

		Page<SalesOrderPaymentEntity> response = salesPayServ.getSalesOrderPay(request, longMap, pageable);
		if (response.getContent().isEmpty()) {
			throw new GaiaException(ErrorCodes.CODE_NO_DATA, ErrorCodes.MSG_NO_DATA);
		}
		return new ResponseEntity<>(assembler.toResource(response), HttpStatus.OK);
	}

	@DeleteMapping("salesorderpayment/{id}")
	public ResponseEntity<ResponseVm> deleteSalesOrderPay(@PathVariable long id) {
		salesPayServ.deleteSalesOrderItem(id);
		return new ResponseEntity<ResponseVm>(ResponseVm.getSuccessVm(), HttpStatus.OK);
	}

	@PutMapping("salesorderpayment/{id}")
	public ResponseEntity<ResponseVm> updateSalesOrderPay(@RequestBody SalesOrderPaymentEntity salePayEntity,
			@PathVariable long id) throws GaiaException {
		SalesOrderPaymentEntity oldSalesPayDetails = salesPayServ.getSalesOrderPay(id);
		if (oldSalesPayDetails == null)
			return ResponseEntity.notFound().build();
		dozerBeanMapper.map(salePayEntity, oldSalesPayDetails);
		oldSalesPayDetails.setId(id);
		oldSalesPayDetails.setUpdatedAt(LocalDateTime.now());
		salesPayServ.addSalesOrderPay(oldSalesPayDetails);
		return new ResponseEntity<ResponseVm>(ResponseVm.getSuccessVm(), HttpStatus.OK);
	}

}
