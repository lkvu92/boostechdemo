package com.boostech.demo.service;

import java.util.List;
import java.util.UUID;

import com.boostech.demo.dto.FindAllProductByCategoryIdAndAttributeIdValuePairsDto;
import com.boostech.demo.entity.PValue;
import com.boostech.demo.entity.Product;

public interface IPValueService {
	PValue findById(UUID attributeId, UUID productId);
	
//	PValue findByAttributeId(UUID attributeId);
//	
//	PValue findByProductId(UUID productId);
	
	List<PValue> findAllByAttributeIdList(List<UUID> attributeIdList);
	
	List<PValue> findAllByProductIdList(List<UUID> productIdList);
	
	List<Product> findAllProductByCategoryIdAndAttributeIdAndValue(FindAllProductByCategoryIdAndAttributeIdValuePairsDto dto);
}