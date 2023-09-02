package BAESOOJIN.PICKTRE.service;

import BAESOOJIN.PICKTRE.api.dto.product.ProductRequest;
import BAESOOJIN.PICKTRE.domain.product.Product;
import BAESOOJIN.PICKTRE.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<Product> getAllProductsByViewCount() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC,"viewCount"));
    }

    /**
     * 상품 재고 수량 업데이트
     *
     * @param productId 상품 ID
    //     * @param quantity  업데이트할 재고 수량
     * @return 업데이트된 상품 엔티티
     */
    public Product updateProductQuantity(Long productId, ProductRequest productRequest) {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(productRequest.getProductName());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setQuantity(productRequest.getQuantity());
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

    public void updateViewCount(Product product) {
        int tempCount=product.getViewCount();
        product.setViewCount(tempCount+1);
    }
}
