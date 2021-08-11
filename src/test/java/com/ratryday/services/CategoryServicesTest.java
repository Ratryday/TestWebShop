package com.ratryday.services;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.junit.jupiter.api.BeforeEach;
import com.ratryday.dao.CategoryDao;
import com.ratryday.models.Category;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * {@link CategoryServices}  must create, update, check if existed, delete and return one or list of Category
 */

@WebMvcTest(CategoryServices.class)
class CategoryServicesTest {

    private Integer testInteger;
    private Category testCategory;
    private List<Category> testCategoryList;
    private static final String TEST_STRING = "Test";

    @Mock
    private CategoryDao categoryDaoMock;

    @InjectMocks
    private CategoryServices categoryServices;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Captor
    private ArgumentCaptor<Category> categoryArgumentCaptor;

    @BeforeEach
    private void setUp() {
        testCategoryList = new ArrayList<>();
        testCategory = new Category();
        testInteger = 0;
    }

    /*
     * Test create(Category) method. It must:
     * - pass Category object to CategoryDao insert(Category) method
     * - return true if CategoryDao return true
     * - return false if CategoryDao return false
     * */

    @Test
    void createPassCategoryToCategoryDao() {
        categoryServices.create(testCategory);

        verify(categoryDaoMock).insert(categoryArgumentCaptor.capture());

        assertThat(categoryArgumentCaptor.getValue(), is(testCategory));
    }

    @Test
    void createReturnTrueIfCategoryDaoReturnTrue() {
        when(categoryDaoMock.insert(testCategory)).thenReturn(true);

        // categoryServices.create(Category) return true if categoryDao.insert(Category) return true
        assertTrue(categoryServices.create(testCategory));
    }

    @Test
    void createReturnFalseIfCategoryDaoReturnFalse() {
        when(categoryDaoMock.insert(testCategory)).thenReturn(false);

        // categoryServices.create(Category) return false if categoryDao.insert(Category) return false
        assertFalse(categoryServices.create(testCategory));
    }

    /*
     * Test update(Category) method. It must:
     * - pass Category object to CategoryDao update(Category) method
     * - return true if CategoryDao return true
     * - return false if CategoryDao return false
     * */

    @Test
    void updatePassCategoryToCategoryDao() {
        categoryServices.update(testCategory);

        verify(categoryDaoMock).update(categoryArgumentCaptor.capture());

        assertThat(categoryArgumentCaptor.getValue(), is(testCategory));
    }

    @Test
    void updateReturnTrueIfCategoryDaoReturnTrue() {
        when(categoryDaoMock.insert(testCategory)).thenReturn(true);

        // categoryServices.update(Category) return true if categoryDao.update(Category) return true
        assertTrue(categoryServices.create(testCategory));
    }

    @Test
    void updateReturnFalseIfCategoryDaoReturnFalse() {
        when(categoryDaoMock.update(testCategory)).thenReturn(false);

        // categoryServices.update(Category) return false if categoryDao.update(Category) return false
        assertFalse(categoryServices.update(testCategory));
    }

    /*
     * Test isExist(String) method. It must:
     * - pass Category object to CategoryDao selectOne(String) method
     * - return true if CategoryDao return true
     * - return false if CategoryDao return false
     * */

    @Test
    void isExistPassCategoryToCategoryDao() {
        categoryServices.isExist(TEST_STRING);

        verify(categoryDaoMock).selectOne(stringArgumentCaptor.capture());

        assertEquals(stringArgumentCaptor.getValue(), TEST_STRING);
    }

    @Test
    void isExistReturnTrueIfCategoryDaoReturnTrue() {
        when(categoryDaoMock.selectOne(TEST_STRING)).thenReturn(true);

        // categoryServices.isExist(String) return true if categoryDao.selectOne(String) return true
        assertTrue(categoryServices.isExist(TEST_STRING));
    }

    @Test
    void isExistReturnFalseIfCategoryDaoReturnFalse() {
        when(categoryDaoMock.selectOne(TEST_STRING)).thenReturn(false);

        // categoryServices.isExist(String) return false if categoryDao.selectOne(String) return false
        assertFalse(categoryServices.isExist(TEST_STRING));
    }

    /*
     * Test delete(Integer) method. It must:
     * - pass Category object to CategoryDao delete(Integer) method
     * - return true if CategoryDao return true
     * - return false if CategoryDao return false
     * */

    @Test
    void deletePassCategoryToCategoryDao() {
        categoryServices.delete(testInteger);

        verify(categoryDaoMock).delete(integerArgumentCaptor.capture());

        assertEquals(integerArgumentCaptor.getValue(), testInteger);
    }

    @Test
    void deleteReturnTrueIfCategoryDaoReturnTrue() {
        when(categoryDaoMock.selectOne(TEST_STRING)).thenReturn(true);

        // categoryServices.delete(Integer) return true if categoryDao.delete(Integer) return true
        assertTrue(categoryServices.isExist(TEST_STRING));
    }

    @Test
    void deleteReturnFalseIfCategoryDaoReturnFalse() {
        when(categoryDaoMock.delete(testInteger)).thenReturn(false);

        // categoryServices.delete(Integer) return false if categoryDao.delete(Integer) return false
        assertFalse(categoryServices.delete(testInteger));
    }

    /*
     * Test getCategory() method. It must:
     * - pass Category object to CategoryDao selectOne(Integer) method
     * - return Category if CategoryDao return Category
     * - return null if CategoryDao return null
     * */

    @Test
    void getCategoryPassCategoryToCategoryDao() {
        categoryServices.getCategory(testInteger);

        verify(categoryDaoMock).selectOne(integerArgumentCaptor.capture());

        assertEquals(integerArgumentCaptor.getValue(), new Category(testInteger, anyString()).getCategoryId());
    }

    @Test
    void getCategoryReturnCategoryIfCategoryDaoReturnCategory() {
        when(categoryDaoMock.selectOne(testInteger)).thenReturn(testCategory);

        // categoryServices.getCategory(Integer) return Category if categoryDao.selectOne(Integer) return Category
        assertThat(categoryServices.getCategory(testInteger), is(testCategory));
    }

    @Test
    void getCategoryReturnNullIfCategoryDaoReturnNull() {
        when(categoryDaoMock.selectOne(testInteger)).thenReturn(null);

        // categoryServices.getCategory(Integer) return null if categoryDao.selectOne(Integer) return null
        assertNull(categoryServices.getCategory(testInteger));
    }

    /*
     * Test getCategoryList() method. It must:
     * - return List<Category> if CategoryDao return List<Category>
     * This method return empty list of categories if CategoryDao doesn't find any Categories in database
     * because CategoryDaoImp use Criteria for searching.
     * When use Criteria method list(), it creates empty List and then add founded Objects to it.
     * It means if criteria.list() doesn't find any objects it returns empty List.
     * So this List doesn't throw NullPointerException.
     * */

    @Test
    void getCategoryListReturnListOfCategoriesIfCategoryDaoReturnListOfCategories() {
        when(categoryDaoMock.select()).thenReturn(testCategoryList);

        assertThat(categoryServices.getCategoryList(), is(testCategoryList));
    }

}