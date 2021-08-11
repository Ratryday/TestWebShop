package com.ratryday.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.extension.ExtendWith;
import com.ratryday.services.CategoryServices;
import com.ratryday.models.Category;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.util.List;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * {@link IndexController} have only one method whose task
 * is to run the index.jsp page.
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = IndexController.class)
class IndexControllerTest {

    private static final String SLASH = "/";
    private static final String INDEX = "index";
    private static final String message = "message";
    private static final String ALL_CATEGORY = "allCategory";
    private static final String message_CONTENT = "There are no categories here.";

    private final MockMvc mockMvc;

    @MockBean
    private CategoryServices categoryServicesMockBean;

    @Autowired
    IndexControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    /*
     * For index() method I must check two situation:
     * - what addAttribute will add to model when categoryServices return empty list / list of categories
     * - does it return the correct view name as a String
     * */

    @Test
    void indexCategoryServicesReturnEmptyListOfCategories() throws Exception {
        when(categoryServicesMockBean.getCategoryList()).thenReturn(new ArrayList<>());

        // Test that categoryServices.getCategoryList() return empty lost of categories
        assertEquals(new ArrayList<>(), categoryServicesMockBean.getCategoryList());

        /*
         * Test that method return correct view name as a String and that model.addAttribute()
         * add correct attribute to model
         * */
        mockMvc.perform(get(SLASH))
                .andExpect(status().isOk())
                .andExpect(view().name(INDEX))
                .andExpect(model().attribute(message, equalTo(message_CONTENT)));
    }

    @Spy
    List<Category> spyListOfCategories = new ArrayList<>();

    {
        spyListOfCategories.add(new Category());
    }

    @Test
    void indexCategoryServicesReturnListOfCategories() throws Exception {
        when(categoryServicesMockBean.getCategoryList()).thenReturn(spyListOfCategories);

        // test that categoryServices.getCategoryList() return list of categories
        assertNotNull(categoryServicesMockBean.getCategoryList().get(0));

        /*
         * Test that method return correct view name as a String and that model.addAttribute()
         * add correct attribute to model
         * */
        mockMvc.perform(get(SLASH))
                .andExpect(status().isOk())
                .andExpect(view().name(INDEX))
                .andExpect(model().attribute(ALL_CATEGORY, categoryServicesMockBean.getCategoryList()));
    }

}