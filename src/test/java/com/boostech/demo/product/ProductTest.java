package com.boostech.demo.product;

import com.boostech.demo.dto.GetOneProductDto;
import com.boostech.demo.entity.*;
import com.boostech.demo.repository.CategoryRepository;
import com.boostech.demo.repository.IAttributeRepository;
import com.boostech.demo.repository.PValueRepository;
import com.boostech.demo.repository.ProductRepository;
import com.boostech.demo.service.IPValueService;
import com.boostech.demo.service.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
public class ProductTest {
    @Autowired
    private ProductService productService;

    @Test
    void contextLoads() {
        GetOneProductDto.CategoryDto category = new GetOneProductDto.CategoryDto(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "Bu lông neo");
        GetOneProductDto.AttributeDto attribute = new GetOneProductDto.AttributeDto(
               UUID.fromString("7ce5e559-7c84-49a5-86f2-17ce7eb98354"),
                "weight",
                "1000",
                "g"
        );
        List<GetOneProductDto.AttributeDto> attributes = List.of(attribute);
        GetOneProductDto dto = new GetOneProductDto(UUID.fromString("e6f5392b-7e0a-4047-b02c-edff8ac73df2"), "Cutting Machine", category, attributes);

//       given(productService.getOneProductDtos(UUID.fromString("e6f5392b-7e0a-4047-b02c-edff8ac73df2")))
//                .willReturn(dto);

       assertThat(productService.getOneProductDtos(UUID.fromString("e6f5392b-7e0a-4047-b02c-edff8ac73df2")).getName()).isEqualTo(dto.getName());


       UUID id = UUID.fromString("e6f5392b-7e0a-4047-b02c-edff8ac73df2");

       assertThat(productService.deleteProduct(id)).isEqualTo(true);
    }
}
