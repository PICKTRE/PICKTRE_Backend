package BAESOOJIN.PICKTRE.api.controller;

import BAESOOJIN.PICKTRE.api.dto.product.ProductRequest;
import BAESOOJIN.PICKTRE.api.dto.product.ProductResponse;
import BAESOOJIN.PICKTRE.api.dto.response.CommonResult;
import BAESOOJIN.PICKTRE.api.dto.response.ListResult;
import BAESOOJIN.PICKTRE.api.dto.response.SingleResult;
import BAESOOJIN.PICKTRE.domain.product.Product;
import BAESOOJIN.PICKTRE.loader.S3Uploader;
import BAESOOJIN.PICKTRE.service.ProductService;
import BAESOOJIN.PICKTRE.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ResponseService responseService;

    private final S3Uploader s3Uploader;


    /**
     *
     * @return
     * 상품 전체 조회
     */
    @GetMapping
    public ListResult<ProductResponse> getAllProduct() {
        List<Product> allProducts = productService.getAllProductsByViewCount();
        List<ProductResponse> productResponses = allProducts.stream().map(ProductResponse::toDto).collect(Collectors.toList());
        return responseService.getListResult(productResponses);
    }

    /**
     *
     * @param msrl
     * @return
     * 상품 단일 조회
     */
    @GetMapping("/{msrl}")
    public SingleResult<ProductResponse> findProductById(@PathVariable Long msrl) {
        Product product = productService.updateViewCount(msrl);
        ProductResponse productResponse = ProductResponse.toDto(product);
        return responseService.getSingleResult(productResponse);
    }


    /**
     *
     * @param productRequest
     * @param file
     * @return
     * @throws IOException
     * 상품 등록
     */
    @PostMapping
    public CommonResult createProduct(@RequestPart(value="productRequest") ProductRequest productRequest,
                                      @RequestPart(value="file")MultipartFile file) throws IOException {
        String filePath;
        try {
            filePath=s3Uploader.uploadFiles(file, "static");
        } catch (Exception e) {
            return responseService.getFailResult();
        }
        productService.createProduct(productRequest,filePath);
        return responseService.getSuccessResult();
    }

    /**
     *
     * @param msrl
     * @param productRequest
     * @return
     * 상품 수정
     */
    @PutMapping("/{msrl}")
    public SingleResult<ProductResponse> updateProduct(@PathVariable Long msrl,@RequestBody ProductRequest productRequest) {
        Product product = productService.updateProduct(msrl, productRequest);
        ProductResponse productResponse = ProductResponse.toDto(product);
        return responseService.getSingleResult(productResponse);
    }

    /**
     *
     * @param msrl
     * @return
     * 상품 삭제
     */
    @DeleteMapping("/{msrl}")
    public CommonResult deleteProduct(@PathVariable Long msrl) {
        productService.deleteProduct(msrl);
        return responseService.getSuccessResult();
    }

}
