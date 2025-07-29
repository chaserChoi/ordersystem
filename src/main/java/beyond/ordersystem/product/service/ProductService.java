package beyond.ordersystem.product.service;

import beyond.ordersystem.member.domain.Member;
import beyond.ordersystem.member.repository.MemberRepository;
import beyond.ordersystem.product.domain.Product;
import beyond.ordersystem.product.dto.ProductCreateDto;
import beyond.ordersystem.product.dto.ProductResDto;
import beyond.ordersystem.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    // 1,  상품 등록
    public Long save(ProductCreateDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("member not found."));

        Product product = productRepository.save(dto.toEntity());
        return product.getId();
    }

    public List<ProductResDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(p -> ProductResDto.fromEntity(p))
                .toList();
    }

    public ProductResDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("product not found."));
        return ProductResDto.fromEntity(product);
    }
}
