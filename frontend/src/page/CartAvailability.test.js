import React from 'react';
import {
    isCartEmpty,
    getCartProducts,
    getCartResponse,
} from './CartAvailability';

describe('Cart Availability Description', () => {
    const EMPTY_CART = {
        id: '5c6bf8a306057eeb12dfb7c4',
        status: 'pending',
        username: 'alice',
        products: {},
        productQuantities: {},
        lastModified: 1551162037567,
        orderDate: 1550482348428,
        totalPrice: 547,
    };

    const SECOND_CART = {
        id: '5c6bf8a306057eeb12dfb7c4',
        status: 'pending',
        username: 'alice',
        products: {
            '59d89875479ded2a718bac13': {
                'id': '59d89875479ded2a718bac13',
                'productName': 'colgatewhite',
                'productCode': 'GE-1206',
                'releaseDate': 1483539355570,
                'lastModified': 1507367029712,
                'description': 'dentifrice',
                'price': 3,
                'starRating': 4.5,
                'imageUrl': 'http://colgate.com/2/',
                'stock': 1000,
            },
            '59d89875479ded2a718bac14': {
                'id': '59d89875479ded2a718bac14',
                'productName': 'gilettemousse',
                'productCode': 'GE-1206',
                'releaseDate': 1486224955570,
                'lastModified': 1507367029713,
                'description': 'moussearaser',
                'price': 2,
                'starRating': 4.2,
                'imageUrl': 'http://gilette.com/2/',
                'stock': 1000,
            },
        },
        productQuantities: {
            '59d89875479ded2a718bac13': 3,
            '59d89875479ded2a718bac14': 2,
        },
        lastModified: 1551162037567,
        orderDate: 1550482348428,
        totalPrice: 1300,
    };

    const USER = 'alice';

    const getProduct = jest.fn();

    it('should test empty cart', () => {
        const result = isCartEmpty(EMPTY_CART);

        expect(!result).toBeTruthy();
    });

    it('should test getCartProducts function', () => {
        getCartProducts(SECOND_CART, getProduct);

        expect(getProduct).toHaveBeenCalled();
    });

    it('should test getCartResponse function for successful response', () => {
        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(SECOND_CART) });

        const response = getCartResponse(USER);

        expect(fetch).toHaveBeenCalledWith(`/api/carts/user/${USER}`, {
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        expect(response).toEqual(Promise.resolve(SECOND_CART));
    });

    it('should test getCartResponse function for unsuccessful response', () => {
        fetch.resetMocks();
        fetch.mockResolvedValueOnce({ json: () => Promise.reject(404) });

        const response = getCartResponse(USER);

        expect(fetch).toHaveBeenCalledWith(`/api/carts/user/${USER}`, {
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        expect(response).toEqual(Promise.reject(404));
    });
});
