package com.ratryday.services;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.ratryday.dao.CategoryDao;
import com.ratryday.models.Category;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * CategoryServices must create, update, return one/list, check if exist and delete Category
 */

@WebMvcTest(CategoryServices.class)
class CategoryServicesTest {

    private static final List<Category> TEST_CATEGORY_LIST = new ArrayList<>();
    private static final Category TEST_CATEGORY = new Category();
    private static final String TEST_STRING = "Test";
    private static final Integer TEST_INTEGER = 0;

    @Mock
    private CategoryDao categoryDao;

    @InjectMocks
    private CategoryServices categoryServicesMock;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Captor
    private ArgumentCaptor<Category> categoryArgumentCaptor;

    /*
     * Test create(Category) method. It must:
     * - pass Category object to CategoryDao insert(Category) method
     * - return true if CategoryDao return true
     * - return false if CategoryDao return false
     * */

    @Test
    void createPassCategoryToCategoryDao() {
        categoryServicesMock.create(TEST_CATEGORY);

        Mockito.verify(categoryDao).insert(categoryArgumentCaptor.capture());

        assertThat(categoryArgumentCaptor.getValue(), is(TEST_CATEGORY));
    }

    @Test
    void createReturnTrueIfCategoryDaoReturnTrue() {
        when(categoryDao.insert(TEST_CATEGORY)).thenReturn(true);

        // categoryServices.create(Category) return true if categoryDao.insert(Category) return true
        assertTrue(categoryServicesMock.create(TEST_CATEGORY));
    }

    @Test
    void createReturnFalseIfCategoryDaoReturnFalse() {
        when(categoryDao.insert(TEST_CATEGORY)).thenReturn(false);

        // categoryServices.create(Category) return false if categoryDao.insert(Category) return false
        assertFalse(categoryServicesMock.create(TEST_CATEGORY));
    }

    /*
     * Test update(Category) method. It must:
     * - pass Category object to CategoryDao update(Category) method
     * - return true if CategoryDao return true
     * - return false if CategoryDao return false
     * */

    @Test
    void updatePassCategoryToCategoryDao() {
        categoryServicesMock.update(TEST_CATEGORY);

        Mockito.verify(categoryDao).update(categoryArgumentCaptor.capture());

        assertThat(categoryArgumentCaptor.getValue(), is(TEST_CATEGORY));
    }

    @Test
    void updateReturnTrueIfCategoryDaoReturnTrue() {
        when(categoryDao.insert(TEST_CATEGORY)).thenReturn(true);

        // categoryServices.update(Category) return true if categoryDao.update(Category) return true
        assertTrue(categoryServicesMock.create(TEST_CATEGORY));
    }

    @Test
    void updateReturnFalseIfCategoryDaoReturnFalse() {
        when(categoryDao.update(TEST_CATEGORY)).thenReturn(false);

        // categoryServices.update(Category) return false if categoryDao.update(Category) return false
        assertFalse(categoryServicesMock.update(TEST_CATEGORY));
    }

    /*
     * Test isExist(String) method. It must:
     * - pass Category object to CategoryDao selectOne(String) method
     * - return true if CategoryDao return true
     * - return false if CategoryDao return false
     * */

    @Test
    void isExistPassCategoryToCategoryDao() {
        categoryServicesMock.isExist(TEST_STRING);

        Mockito.verify(categoryDao).selectOne(stringArgumentCaptor.capture());

        assertEquals(stringArgumentCaptor.getValue(), TEST_STRING);
    }

    @Test
    void isExistReturnTrueIfCategoryDaoReturnTrue() {
        when(categoryDao.selectOne(TEST_STRING)).thenReturn(true);

        // categoryServices.isExist(String) return true if categoryDao.selectOne(String) return true
        assertTrue(categoryServicesMock.isExist(TEST_STRING));
    }

    @Test
    void isExistReturnFalseIfCategoryDaoReturnFalse() {
        when(categoryDao.selectOne(TEST_STRING)).thenReturn(false);

        // categoryServices.isExist(String) return false if categoryDao.selectOne(String) return false
        assertFalse(categoryServicesMock.isExist(TEST_STRING));
    }

    /*
     * Test delete(Integer) method. It must:
     * - pass Category object to CategoryDao delete(Integer) method
     * - return true if CategoryDao return true
     * - return false if CategoryDao return false
     * */

    @Test
    void deletePassCategoryToCategoryDao() {
        categoryServicesMock.delete(TEST_INTEGER);

        Mockito.verify(categoryDao).delete(integerArgumentCaptor.capture());

        assertEquals(integerArgumentCaptor.getValue(), TEST_INTEGER);
    }

    @Test
    void deleteReturnTrueIfCategoryDaoReturnTrue() {
        when(categoryDao.selectOne(TEST_STRING)).thenReturn(true);

        // categoryServices.delete(Integer) return true if categoryDao.delete(Integer) return true
        assertTrue(categoryServicesMock.isExist(TEST_STRING));
    }

    @Test
    void deleteReturnFalseIfCategoryDaoReturnFalse() {
        when(categoryDao.delete(TEST_INTEGER)).thenReturn(false);

        // categoryServices.delete(Integer) return false if categoryDao.delete(Integer) return false
        assertFalse(categoryServicesMock.delete(TEST_INTEGER));
    }

    /*
     * Test getCategory() method. It must:
     * - pass Category object to CategoryDao selectOne(Integer) method
     * - return Category if CategoryDao return Category
     * - return null if CategoryDao return null
     * */

    @Test
    void getCategoryPassCategoryToCategoryDao() {
        categoryServicesMock.getCategory(TEST_INTEGER);

        Mockito.verify(categoryDao).selectOne(integerArgumentCaptor.capture());

        assertEquals(integerArgumentCaptor.getValue(), new Category(TEST_INTEGER, anyString()).getCategoryId());
    }

    @Test
    void getCategoryReturnCategoryIfCategoryDaoReturnCategory() {
        when(categoryDao.selectOne(TEST_INTEGER)).thenReturn(TEST_CATEGORY);

        // categoryServices.getCategory(Integer) return Category if categoryDao.selectOne(Integer) return Category
        assertThat(categoryServicesMock.getCategory(TEST_INTEGER), is(TEST_CATEGORY));
    }

    @Test
    void getCategoryReturnNullIfCategoryDaoReturnNull() {
        when(categoryDao.selectOne(TEST_INTEGER)).thenReturn(null);

        // categoryServices.getCategory(Integer) return null if categoryDao.selectOne(Integer) return null
        assertNull(categoryServicesMock.getCategory(TEST_INTEGER));
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
        when(categoryDao.select()).thenReturn(TEST_CATEGORY_LIST);

        assertThat(categoryServicesMock.getCategoryList(), is(TEST_CATEGORY_LIST));
    }

}