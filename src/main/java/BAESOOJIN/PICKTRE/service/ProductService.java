package BAESOOJIN.PICKTRE.service;

import BAESOOJIN.PICKTRE.api.dto.product.ProductRequest;
import BAESOOJIN.PICKTRE.domain.product.Product;
import BAESOOJIN.PICKTRE.exception.ProductNotFoundException;
import BAESOOJIN.PICKTRE.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품 등록
     * <p>
     * //     * @param name     상품 이름
     * //     * @param price    상품 가격
     * //     * @param quantity 상품 재고 수량
     *
     * @return 등록된 상품 엔티티
     */

    public Product createProduct(ProductRequest productRequest, String filePath) {
        Product product = Product.builder()
                .name(productRequest.getProductName())
                .imagePath(filePath) // 이미지 파일 경로 추가
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .viewCount(0)
                .build();
        return productRepository.save(product);
    }

    /**
     * 모든 상품 목록 조회
     *
     * @return 상품 엔티티 목록
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    /**
     * 인기 별 모든 상품 목록 조회
     *
     * @return 상품 엔티티 목록
     */
    public List<Product> getAllProductsByViewCount() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC,"viewCount"));
    }

    /**
     * 상품 업데이트
     *
     * @param productId 상품 ID
     * @param productRequest 상품 update Request
     *
     * @return 업데이트된 상품 엔티티
     */
    public Product updateProduct(Long productId, ProductRequest productRequest) {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.updateProduct(productRequest.getProductName(),
                productRequest.getPrice(),
                productRequest.getQuantity());

        return productRepository.save(existingProduct);
    }

    /**
     * 상품 삭제
     * @param productId 상품 ID
     */
    public void deleteProduct(Long productId) {
        // 해당 ID에 해당하는 상품 조회
        Product productToDelete = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 상품 삭제
        productRepository.delete(productToDelete);
    }

    /**
     * 상품 조회
     *
     * @param productId 상품 ID
     * @return 상품 엔티티
     * @throws RuntimeException 상품이 없을 경우 예외 발생
     */
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    /**
     *
     * @param keyword 검색할 Keyword
     * @return
     * 상품 검색 기능
     */
    public List<Product> searchProduct(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }


    /**
     * 조회수 증가 기능
     * @param productId 상품 고유 ID
     * @return 고유 ID 로 찾은 상품 Entity
     */
    public Product updateViewCount(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            int currentViewCount = product.getViewCount();
            product.addViewCount(currentViewCount + 1);
            productRepository.save(product); // 변경사항을 저장합니다.
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }

        return optionalProduct.get();
    }
}
