package com.ratryday.controllers;


import com.ratryday.models.Category;
import com.ratryday.models.Product;
import com.ratryday.services.CartServices;
import com.ratryday.services.CategoryServices;
import com.ratryday.services.OrderServices;
import com.ratryday.services.ProductServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * {@link AdminController} have many methods to control admin panel.
 * I can split all methods into several layers:
 * - Admin layer
 * - Category layer
 * - Product layer
 * - Cart layer
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AdminController.class)
@Import(Category.class)
class AdminControllerTest {

    @Mock
    private BindingResult bindingResultMock;

    @Mock
    private MockMultipartFile multipartFileMock;

    @Mock
    private MultipartFile multipartFile;

    @Mock
    private HttpServletRequest httpServletRequestMock;

    @MockBean
    private CategoryServices categoryServicesMockBean;

    @MockBean
    private ProductServices productServicesMockBean;

    @MockBean
    private OrderServices orderServicesMockBean;

    @MockBean
    private CartServices cartServicesMockBean;

    private Category testCategory;
    private Product testProduct;
    private int testId;

    private final MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;

    @Autowired
    AdminControllerTest(MockMvc mockMvc, WebApplicationContext webApplicationContext) {
        this.mockMvc = mockMvc;
        this.webApplicationContext = webApplicationContext;
    }

    @BeforeEach
    void setUp() {
        testId = 0;
        testCategory = new Category("name");
        testProduct = new Product(new BigDecimal(1.1), "name", "image",
                "description", testCategory);
         multipartFileMock = new MockMultipartFile("imageFile", "image.jpg", "text/plain", "some xml".getBytes());
    }


    // Admin layer
    /*
     * Method adminSingIn() is responsible for load admin.jsp page if user successfully sing in to admin panel.
     * For it, I must test:
     * - what addAttribute will add to model when categoryServices return empty list / list of categories
     * - does it return the correct view name as a String
     * */
    @Test
    void adminSingInCategoryServicesReturnEmptyListOfCategories() throws Exception {
        when(categoryServicesMockBean.getCategoryList()).thenReturn(new ArrayList<>());

        // Test that categoryServices.getCategoryList() return empty lost of categories
        assertEquals(new ArrayList<>(), categoryServicesMockBean.getCategoryList());

        /*
         * Test that method return correct view name as a String and that model.addAttribute()
         * add correct attribute to model
         * */
        mockMvc.perform(post("/admin/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin"))
                .andExpect(model().attribute("massage", "There are no categories here."));
    }

    @Spy
    List<Category> spyListOfCategories = new ArrayList<>();

    {
        spyListOfCategories.add(new Category());
    }

    @Test
    void adminSingInCategoryServicesReturnListOfCategories() throws Exception {
        when(categoryServicesMockBean.getCategoryList()).thenReturn(spyListOfCategories);

        // test that categoryServices.getCategoryList() return list of categories
        assertNotNull(categoryServicesMockBean.getCategoryList().get(0));

        /*
         * Test that method return correct view name as a String and that model.addAttribute()
         * add correct attribute to model
         * */
        mockMvc.perform(post("/admin/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin"))
                .andExpect(model().attribute("allCategory", categoryServicesMockBean.getCategoryList()));
    }

    /*
     * Method logout() needed only for correct work of Spring Security.
     * When user try to log out Spring Security using this method take away admin role from user.
     * It only purposes redirect to IndexController
     * */
    @Test
    void logout() throws Exception {
        mockMvc.perform(post("/admin/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    /*
     * Method adminLogin() needed only for correct work of Spring Security.
     * When user try to log in Spring Security using this method give user admin role.
     * It only purposes redirect to adminSingIn()
     * */
    @Test
    void adminLogin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/login"));
    }

    // Category layer
    /*
     * Method newCategory() add new Category to model attribute and send it to create.jsp.
     * */
    @Test
    void newCategoryAddNewCategoryToModelAttribute() throws Exception {
        mockMvc.perform(get("/admin/category/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/category/create"))
                .andExpect(model().attribute("category", new Category()));
    }

    /*
     * Method createCategory() create new Category if it is valid.
     * If Category doesn't be valid, method return page of creation category with errors to display.
     * */
    @Test
    void createCategoryWhenCategoryWithTheSameNameAlreadyExist() throws Exception {
        // This will add error to BindingResult
        when(categoryServicesMockBean.isExist(testCategory.getCategoryName())).thenReturn(true);
        when(bindingResultMock.hasErrors()).thenReturn(true);
        mockMvc.perform(post("/admin/category").param("category", "name"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/category/create"))
                .andExpect(model().attribute("category", testCategory));
    }

    @Test
    void createCategoryWhenCategoryHaveUniqueNameAndBindingResultHasErrors() throws Exception {
        when(categoryServicesMockBean.isExist(testCategory.getCategoryName())).thenReturn(false);
        when(bindingResultMock.hasErrors()).thenReturn(true);
        mockMvc.perform(post("/admin/category").param("category", "name"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/category/create"))
                .andExpect(model().attribute("category", testCategory));
    }

    @Test
    void createCategoryWhenCategoryHaveUniqueNameAndBindingResultHasNoErrorsButCategoryHasNotCreated() throws Exception {
        when(categoryServicesMockBean.isExist(testCategory.getCategoryName())).thenReturn(false);
        when(bindingResultMock.hasErrors()).thenReturn(false);

        // Category hasn't created
        when(categoryServicesMockBean.create(testCategory)).thenReturn(false);

        testCategory.setCategoryName("name");

        mockMvc.perform(post("/admin/category").param("category", "name"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/category/create"))
                .andExpect(model().attribute("category", testCategory));
    }

    @Test
    void createCategoryWhenCategoryHaveUniqueNameAndBindingResultHasNoErrorsAndCategoryWasCreated() throws Exception {
        when(categoryServicesMockBean.isExist(testCategory.getCategoryName())).thenReturn(false);
        when(bindingResultMock.hasErrors()).thenReturn(false);

        // Category created
        when(categoryServicesMockBean.create(testCategory)).thenReturn(true);

        testCategory.setCategoryName("name");

        mockMvc.perform(post("/admin/category").param("category", "name"))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/admin"))
                .andExpect(model().attribute("category", testCategory));
    }

    /*
     * Method editCategory() add category that must be edited to model
     * */
    @Test
    void editCategoryAddCategoryToUpdateToModalAttribute() throws Exception {
        when(categoryServicesMockBean.getCategory(testId)).thenReturn(testCategory);
        mockMvc.perform(get("/admin/category/edit").param("categoryId", String.valueOf(testId)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/category/edit"))
                .andExpect(model().attribute("category", categoryServicesMockBean.getCategory(testId)));
    }

    /*
     * Method updateCategory() update Category if it is valid.
     * If Category doesn't be valid, method return page of editing category with errors to display.
     * */
    @Test
    void updateCategoryCategoryDoesNotHaveName() throws Exception {
        testCategory.setCategoryName(null);
        when(bindingResultMock.hasErrors()).thenReturn(true); // because Category doesn't have name
        assertNull(testCategory.getCategoryName());
        mockMvc.perform(post("/admin/category/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/category/edit"))
                .andExpect(model().attribute("category", testCategory));
    }

    @Test
    void updateCategoryCategoryHaveNameAndItTheSameNameThatBefore() throws Exception {
        when(categoryServicesMockBean.getCategory(testId)).thenReturn(testCategory);
        assertNotNull(testCategory.getCategoryName());
        when(categoryServicesMockBean.getCategory(testCategory.getCategoryId())).thenReturn(testCategory);
        assertEquals(categoryServicesMockBean.getCategory(testCategory.getCategoryId()).getCategoryName(),
                testCategory.getCategoryName());
        mockMvc.perform(post("/admin/category/edit").param("category", "name"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/category/edit"))
                .andExpect(model().attribute("category", testCategory));
    }

    @Test
    void updateCategoryCategoryHaveNewNameButCategoryWithThisNameAlreadyExist() throws Exception {
        when(categoryServicesMockBean.getCategory(testId)).thenReturn(testCategory);
        assertNotNull(testCategory.getCategoryName());

        // Category with new name
        Category categoryWithNewName = new Category("NewName");
        when(categoryServicesMockBean.getCategory(testCategory.getCategoryId())).thenReturn(testCategory);
        assertNotEquals(categoryServicesMockBean.getCategory(testCategory.getCategoryId()).getCategoryName(),
                categoryWithNewName.getCategoryName());
        when(categoryServicesMockBean.isExist(categoryWithNewName.getCategoryName())).thenReturn(true);
        mockMvc.perform(post("/admin/category/edit").param("category", "NewName"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/category/edit"))
                .andExpect(model().attribute("category", categoryWithNewName));
    }

    @Test
    void updateCategoryCategoryHaveNewNameAndCategoryWithThisNameDoesNotExistButUpdateReturnFalse() throws Exception {
        when(categoryServicesMockBean.getCategory(testId)).thenReturn(testCategory);
        assertNotNull(testCategory.getCategoryName());

        // Category with new name
        Category categoryWithNewName = new Category("NewName");
        when(categoryServicesMockBean.getCategory(testCategory.getCategoryId())).thenReturn(testCategory);
        assertNotEquals(categoryServicesMockBean.getCategory(testCategory.getCategoryId()).getCategoryName(),
                categoryWithNewName.getCategoryName());
        when(categoryServicesMockBean.isExist(categoryWithNewName.getCategoryName())).thenReturn(false);
        when(categoryServicesMockBean.update(categoryWithNewName)).thenReturn(false);
        mockMvc.perform(post("/admin/category/edit").param("category", "NewName"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/category/edit"))
                .andExpect(model().attribute("category", categoryWithNewName))
                .andExpect(model().attribute("massage", "Category wasn't be updated"));
    }

    @Test
    void updateCategoryCategoryHaveNewNameAndCategoryWithThisNameDoesNotExistAndUpdateReturnTrue() throws Exception {
        when(categoryServicesMockBean.getCategory(testId)).thenReturn(testCategory);
        assertNotNull(testCategory.getCategoryName());

        // Category with new name
        Category categoryWithNewName = new Category("NewName");
        when(categoryServicesMockBean.getCategory(testCategory.getCategoryId())).thenReturn(testCategory);
        assertNotEquals(categoryServicesMockBean.getCategory(testCategory.getCategoryId()).getCategoryName(),
                categoryWithNewName.getCategoryName());
        when(categoryServicesMockBean.isExist(categoryWithNewName.getCategoryName())).thenReturn(false);
        when(categoryServicesMockBean.update(categoryWithNewName)).thenReturn(true);
        mockMvc.perform(post("/admin/category/edit").param("category", "NewName"))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/admin"))
                .andExpect(model().attribute("category", categoryWithNewName));
    }


    /*
     * Method deleteCategory() delete Category from DB.
     * */
    @Test
    void deleteCategoryNotSuccessful() throws Exception {
        when(categoryServicesMockBean.delete(testId)).thenReturn(false);
        mockMvc.perform(post("/admin/category/delete").param("categoryId", String.valueOf(testId)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin"))
                .andExpect(model().attribute("massage", "Category doesn't deleted"));
    }

    @Test
    void deleteCategorySuccessful() throws Exception {
        when(categoryServicesMockBean.delete(testId)).thenReturn(true);
        mockMvc.perform(post("/admin/category/delete").param("categoryId", String.valueOf(testId)))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/admin"));
    }


    // Product layer
    /*
     * Method productList() add different attribute to modal.
     * */
    @Test
    void productListIfListOfProductsIsEmpty() throws Exception {
        when(categoryServicesMockBean.getCategory(testId)).thenReturn(testCategory);
        when(productServicesMockBean.getProductList(categoryServicesMockBean.getCategory(testId))).thenReturn(new ArrayList<>());
        assertEquals(productServicesMockBean.getProductList(categoryServicesMockBean.getCategory(testId)), new ArrayList<>());
        mockMvc.perform(get("/admin/product/products").param("categoryId", String.valueOf(testId)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/product/products"))
                .andExpect(model().attribute("category", categoryServicesMockBean.getCategory(testId)))
                .andExpect(model().attribute("massage", "There are no products here."));
    }

    @Spy
    List<Product> spyListOfProducts = new ArrayList<>();

    {
        spyListOfProducts.add(new Product());
    }

    @Test
    void productListIfListOfProductsNotEmpty() throws Exception {
        when(categoryServicesMockBean.getCategory(testId)).thenReturn(testCategory);
        when(productServicesMockBean.getProductList(categoryServicesMockBean.getCategory(testId))).thenReturn(spyListOfProducts);
        assertNotNull(productServicesMockBean.getProductList(categoryServicesMockBean.getCategory(testId)).get(0));
        mockMvc.perform(get("/admin/product/products").param("categoryId", String.valueOf(testId)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/product/products"))
                .andExpect(model().attribute("category", categoryServicesMockBean.getCategory(testId)))
                .andExpect(model().attribute("allProducts", productServicesMockBean
                        .getProductList(categoryServicesMockBean.getCategory(testId))));
    }

    /*
     * Method newProduct() add new Product to model attribute and send it to create.jsp.
     * */
    @Test
    void newProductAddNewProductToModel() throws Exception {
        mockMvc.perform(get("/admin/product/new").param("categoryId", String.valueOf(testId)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/product/create"))
                .andExpect(model().attribute("categoryId", testId))
                .andExpect(model().attribute("product", new Product()));
    }

    /*
     * Method createProduct() create new Product if it is valid.
     * If Product doesn't be valid, method return page of creation category with errors to display.
     * */
    @Test
    void createProductWhenImageFileIsEmpty() throws Exception {
        when(multipartFile.isEmpty()).thenReturn(true);
        assertTrue(multipartFile.isEmpty());
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mvc.perform(multipart("/admin/product")
                        .file("imageFile", null)
                        .flashAttr("product", new Product())
                        .param("categoryId", String.valueOf(testId)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("categoryId", testId))
                .andExpect(view().name("admin/product/create"));
    }

    @Test
    void createProductWhenProductWithTheSameNameAlreadyExist() throws Exception {
        when(multipartFile.isEmpty()).thenReturn(false);
        when(productServicesMockBean.isExist(testProduct.getProductName())).thenReturn(true);

        assertFalse(multipartFile.isEmpty());

        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mvc.perform(multipart("/admin/product", testProduct)
                        .file(multipartFileMock)
                        .flashAttr("product", testProduct)
                        .param("categoryId", String.valueOf(testId)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("categoryId", testId))
                .andExpect(view().name("admin/product/create"));
    }

    @Test
    void editProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }


    // Cart layer
    @Test
    void orders() {
    }

    @Test
    void orderCart() {
    }

    @Test
    void deleteCartEntryProduct() {
    }
}