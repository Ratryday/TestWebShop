package com.ratryday.services;

import com.ratryday.dao.CartDao;
import com.ratryday.dao.CartEntryDao;
import com.ratryday.models.Cart;
import com.ratryday.models.CartEntry;
import com.ratryday.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link CartServices} must create, get cart, delete cart or cart entry
 */

@WebMvcTest(CartServices.class)
class CartServicesTest {

    private Cart testCart;
    private Product testProduct;
    private int testProductCount;
    private CartEntry testCartEntry;
    private final static String TEST_STRING = "string";

    @InjectMocks
    private CartServices cartServices;

    @Mock
    private MockHttpSession mockHttpSession;

    @Mock
    private CartEntryDao cartEntryDaoMock;

    @Mock
    private CartDao cartDaoMock;

    @Captor
    private ArgumentCaptor<HttpSession> httpSessionArgumentCaptor;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        testCart = new Cart();
        testProduct = new Product();
        testProductCount = 1;
        testCartEntry = new CartEntry();
    }

    @Test
    void createReturnFalseWhenCartDaoReturnFalse() {
        when(cartDaoMock.insert(mockHttpSession)).thenReturn(false);

        cartServices.create(testProductCount, testProduct, mockHttpSession);

        assertFalse(cartDaoMock.insert(mockHttpSession));
    }

    @Test
    void createReturnFalseWhenCartEntryDaoReturnFalse() {
        when(cartDaoMock.insert(mockHttpSession)).thenReturn(true);
        when(cartEntryDaoMock.insert(testCartEntry)).thenReturn(false);

        cartServices.create(testProductCount, testProduct, mockHttpSession);

        assertTrue(cartDaoMock.insert(mockHttpSession));
        assertFalse(cartEntryDaoMock.insert(testCartEntry));
    }

    @Test
    void createReturnTrueWhenCartEntryDaoReturnTrue() {
        when(cartDaoMock.insert(mockHttpSession)).thenReturn(true);
        when(cartEntryDaoMock.insert(testCartEntry)).thenReturn(true);

        cartServices.create(testProductCount, testProduct, mockHttpSession);

        assertTrue(cartDaoMock.insert(mockHttpSession));
        assertTrue(cartEntryDaoMock.insert(testCartEntry));
    }

    @Test
    void deleteReturnFalseWhenCartEntryDaoReturnFalse() {
        when(cartEntryDaoMock.delete(testCart.getCartEntry())).thenReturn(false);

        cartServices.delete(testCart);

        assertFalse(cartEntryDaoMock.delete(testCart.getCartEntry()));
    }

    @Test
    void deleteReturnFalseWhenCartDaoReturnFalse() {
        when(cartEntryDaoMock.delete(testCart.getCartEntry())).thenReturn(true);
        when(cartDaoMock.delete(testCart)).thenReturn(false);

        cartServices.delete(testCart);

        assertTrue(cartEntryDaoMock.delete(testCart.getCartEntry()));
        assertFalse(cartDaoMock.delete(testCart));
    }

    @Test
    void deleteReturnTrueWhenCartDaoReturnTrue() {
        when(cartEntryDaoMock.delete(testCart.getCartEntry())).thenReturn(true);
        when(cartDaoMock.delete(testCart)).thenReturn(true);

        cartServices.delete(testCart);

        assertTrue(cartEntryDaoMock.delete(testCart.getCartEntry()));
        assertTrue(cartDaoMock.delete(testCart));
    }

    @Test
    void deleteCartEntryReturnFalseWhenCartEntryDaoReturnFalse() {
        when(cartEntryDaoMock.delete(testProduct, cartDaoMock.selectOne(mockHttpSession))).thenReturn(false);

        cartServices.deleteCartEntry(testProduct, mockHttpSession);

        assertFalse(cartEntryDaoMock.delete(testProduct, cartDaoMock.selectOne(mockHttpSession)));
    }

    @Test
    void deleteCartEntryReturnTrueWhenCartEntryDaoReturnTrue() {
        when(cartEntryDaoMock.delete(testProduct, cartDaoMock.selectOne(mockHttpSession))).thenReturn(true);

        cartServices.deleteCartEntry(testProduct, mockHttpSession);

        assertTrue(cartEntryDaoMock.delete(testProduct, cartDaoMock.selectOne(mockHttpSession)));
    }

    @Test
    void getCartPassHttpSessionToCartDao() {
        cartServices.getCart(mockHttpSession);

        verify(cartDaoMock).selectOne(httpSessionArgumentCaptor.capture());

        assertThat(httpSessionArgumentCaptor.getValue(), is(mockHttpSession));
    }

    @Test
    void getCartHttpSessionReturnNullWhenCartDaoReturnNull() {
        when(cartDaoMock.selectOne(mockHttpSession)).thenReturn(null);

        cartServices.getCart(mockHttpSession);

        assertNull(cartDaoMock.selectOne(mockHttpSession));
    }

    @Test
    void getCartHttpSessionReturnCartWhenCartDaoReturnCart() {
        when(cartDaoMock.selectOne(mockHttpSession)).thenReturn(testCart);

        cartServices.getCart(mockHttpSession);

        assertThat(cartDaoMock.selectOne(mockHttpSession), is(testCart));
    }

    @Test
    void testGetCartPassStringToCartDao() {
        cartServices.getCart(TEST_STRING);

        verify(cartDaoMock).selectOne(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue(), is(TEST_STRING));
    }

    @Test
    void getCartStringReturnNullWhenCartDaoReturnNull() {
        when(cartDaoMock.selectOne(TEST_STRING)).thenReturn(null);

        cartServices.getCart(TEST_STRING);

        assertNull(cartDaoMock.selectOne(TEST_STRING));
    }

    @Test
    void getCartStringReturnCartWhenCartDaoReturnCart() {
        when(cartDaoMock.selectOne(TEST_STRING)).thenReturn(testCart);

        cartServices.getCart(TEST_STRING);

        assertThat(cartDaoMock.selectOne(TEST_STRING), is(testCart));
    }

    @Test
    void testGetCartPassIntegerToCartDao() {
        cartServices.getCart(testProductCount);

        verify(cartDaoMock).selectOne(integerArgumentCaptor.capture());

        assertThat(integerArgumentCaptor.getValue(), is(testProductCount));
    }

    @Test
    void getCartIntegerReturnNullWhenCartDaoReturnNull() {
        when(cartDaoMock.selectOne(testProductCount)).thenReturn(null);

        cartServices.getCart(testProductCount);

        assertNull(cartDaoMock.selectOne(testProductCount));
    }

    @Test
    void getCartIntegerReturnCartWhenCartDaoReturnCart() {
        when(cartDaoMock.selectOne(testProductCount)).thenReturn(testCart);

        cartServices.getCart(testProductCount);

        assertThat(cartDaoMock.selectOne(testProductCount), is(testCart));
    }
}