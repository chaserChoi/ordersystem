package beyond.ordersystem.product.controller;

import beyond.ordersystem.common.dto.CommonDto;
import beyond.ordersystem.product.dto.ProductCreateDto;
import beyond.ordersystem.product.dto.ProductResDto;
import beyond.ordersystem.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    // 1. 상품 등록: /product/create
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(@ModelAttribute ProductCreateDto productCreateDto) {
        Long id = productService.save(productCreateDto);

        return new ResponseEntity<>(
                CommonDto.builder()
                        .result(id)
                        .statusCode(HttpStatus.CREATED.value())
                        .statusMessage("상품 등록 완료")
                        .build()
                , HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll(@PathVariable Long id) {
        ProductResDto productResDto = productService.findById(id);

        return new ResponseEntity<>(
                CommonDto.builder()
                        .result(id)
                        .statusCode(HttpStatus.OK.value())
                        .statusMessage("상품 등록 완료")
                        .build()
                , HttpStatus.OK);
    }
}
