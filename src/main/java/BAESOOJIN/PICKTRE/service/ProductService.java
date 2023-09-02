package BAESOOJIN.PICKTRE.service;

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

