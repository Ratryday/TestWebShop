package com.ratryday.services;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.web.multipart.MultipartFile;
import org.junit.jupiter.api.BeforeEach;
import com.ratryday.models.Category;
import com.ratryday.dao.ProductDao;
import com.ratryday.models.Product;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * {@link ProductServices} must create, update, check if existed, delete and return one or list of Products
 */

@WebMvcTest(ProductServices.class)
class ProductServicesTest {

    private int testId;
    private Product testProduct;
    private Category testCategory;
    private List<Product> testProductList;
    private static final String TEST_FILE_NAME = "name.jpg";
    private static final String REAL_PATH_TO_UPLOADS = "src/main/webapp";

    @Mock
    private ProductDao productDao;

    @Mock
    private MultipartFile multipartFileMock;

    @InjectMocks
    private ProductServices productServices;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Captor
    private ArgumentCaptor<Category> categoryArgumentCaptor;

    @BeforeEach
    void setUp() {
        testId = 0;
        testProduct = new Product();
        testCategory = new Category();
        testProduct.setProductImage("images/name.jpg");
        testProduct.setCategory(testCategory);
        testProductList = new ArrayList<>();
    }

    /*
     * Test create() method. It must:
     * - pass Product object to ProductDao insert(Product) method
     * - return true if ProductDao return true
     * - return false if ProductDao return false
     * */

    @Test
    void createPassProductToProductDao() throws Exception {
        when(multipartFileMock.getOriginalFilename()).thenReturn(TEST_FILE_NAME);
        productServices.create(new Product(), multipartFileMock, testCategory, REAL_PATH_TO_UPLOADS);
        verify(productDao).insert(productArgumentCaptor.capture());
        assertThat(productArgumentCaptor.getValue(), is(testProduct));
    }

    @Test
    void createReturnTrueIfProductDaoReturnTrue() {
        when(productDao.insert(testProduct)).thenReturn(true);

        // productServices.create() return true if productDao.insert(Product) return true
        assertTrue(productDao.insert(testProduct));
    }

    @Test
    void createReturnFalseIfProductDaoReturnFalse() {
        when(productDao.insert(testProduct)).thenReturn(false);

        // productServices.create() return false if productDao.insert(Product) return false
        assertFalse(productDao.insert(testProduct));
    }

    /*
     * Test delete() method. It must:
     * - pass int id to ProductDao delete(id) method
     * - return true if ProductDao return true
     * - return false if ProductDao return false
     * */

    @Test
    void deletePassIntegerToProductDao() {
        productServices.delete(testId);
        verify(productDao).delete(integerArgumentCaptor.capture());
        assertThat(integerArgumentCaptor.getValue(), is(testId));
    }

    @Test
    void deleteReturnTrueIfProductDaoReturnTrue() {
        when(productDao.delete(testId)).thenReturn(true);

        // productServices.delete() return true if productDao.delete() return true
        assertTrue(productDao.delete(testId));
    }

    @Test
    void deleteReturnFalseIfProductDaoReturnFalse() {
        when(productDao.delete(testId)).thenReturn(false);

        // productServices.delete() return false if productDao.delete() return false
        assertFalse(productDao.delete(testId));
    }

    /*
     * Test isExist() method. It must:
     * - pass String to ProductDao selectOne(String productName) method
     * - return true if ProductDao return true
     * - return false if ProductDao return false
     * */

    @Test
    void isExistPassStringToProductDao() {
        productServices.isExist(TEST_FILE_NAME);
        verify(productDao).selectOne(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(TEST_FILE_NAME));
    }

    @Test
    void isExistReturnTrueIfProductDaoReturnTrue() {
        when(productDao.selectOne(anyString())).thenReturn(true);

        // productServices.isExist() return true if productDao.selectOne(String productName) return true
        assertTrue(productDao.selectOne(anyString()));
    }

    @Test
    void isExistReturnFalseIfProductDaoReturnFalse() {
        when(productDao.selectOne(anyString())).thenReturn(false);

        // productServices.isExist() return false if productDao.selectOne(String productName) return false
        assertFalse(productDao.selectOne(anyString()));
    }

    /*
     * Test update() method. It must:
     * - pass Product object to ProductDao update(Product) method
     * - return true if ProductDao return true
     * - return false if ProductDao return false
     * */

    @Test
    void updatePassProductToProductDao() throws IOException {
        when(multipartFileMock.getOriginalFilename()).thenReturn(TEST_FILE_NAME);
        productServices.update(new Product(), multipartFileMock, testCategory, REAL_PATH_TO_UPLOADS);
        verify(productDao).update(productArgumentCaptor.capture());
        assertThat(productArgumentCaptor.getValue(), is(testProduct));
    }

    @Test
    void updateReturnTrueIfProductDaoReturnTrue() {
        when(productDao.update(testProduct)).thenReturn(true);

        // productServices.update() return true if productDao.update(Product) return true
        assertTrue(productDao.update(testProduct));
    }

    @Test
    void updateReturnFalseIfProductDaoReturnFalse() {
        when(productDao.update(testProduct)).thenReturn(false);

        // productServices.update() return false if productDao.update(Product) return false
        assertFalse(productDao.selectOne(anyString()));
    }

    /*
     * Test getProduct() method. It must:
     * - pass Product object to ProductDao selectOne(Integer) method
     * - return Product if ProductDao return Product
     * - return null if ProductDao return null
     * */

    @Test
    void getProductPassProductToProductDao() {
        productServices.getProduct(testId);
        verify(productDao).selectOne(integerArgumentCaptor.capture());
        assertThat(integerArgumentCaptor.getValue(), is(testId));
    }

    @Test
    void getProductReturnProductIfProductDaoReturnProduct() {
        when(productDao.selectOne(testId)).thenReturn(testProduct);

        // productServices.selectOne(Integer) return Product if productDao.selectOne(Integer) return Product
        assertThat(productServices.getProduct(testId), is(testProduct));
    }

    @Test
    void getProductReturnNullIfProductDaoReturnNull() {
        when(productDao.selectOne(testId)).thenReturn(null);

        // productServices.selectOne(Integer) return null if productDao.selectOne(Integer) return null
        assertNull(productServices.getProduct(testId));
    }

    /*
     * Test getProductList() method. It must:
     * - pass Category object to ProductDao select(Category)
     * - return List<Product> if ProductDao return List<Product>
     * This method return empty list of products if ProductDao doesn't find any Products in database
     * because ProductDaoImp use Criteria for searching.
     * When use Criteria method list(), it creates empty List and then add founded Objects to it.
     * It means if criteria.list() doesn't find any objects it returns empty List.
     * So this List doesn't throw NullPointerException.
     * */

    @Test
    void getProductListPassCategoryToProductDao() {
        productServices.getProductList(testCategory);
        verify(productDao).select(categoryArgumentCaptor.capture());
        assertThat(categoryArgumentCaptor.getValue(), is(testCategory));
    }

    @Test
    void getProductListOfProductsReturnListIfProductDaoReturnListOfProducts() {
        when(productDao.select(testCategory)).thenReturn(testProductList);
        assertThat(productServices.getProductList(testCategory), is(testProductList));
    }

}