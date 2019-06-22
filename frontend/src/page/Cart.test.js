import React from 'react';
import { shallow } from 'enzyme';
import { Table, Label } from 'reactstrap';
import { FaPlusSquare, FaMinusSquare } from 'react-icons/fa';
import Cart from './Cart';

describe('carts page', () => {
    const defaultProps = {
        user: 'alice',
    };

    const EMPTY_CART = [
        {
            id: '5c6bf8a306057eeb12dfb7c4',
            status: 'pending',
            username: 'alice',
            products: {},
            productQuantities: {},
            lastModified: 1551162037567,
            orderDate: 1550482348428,
            totalPrice: 547,
        },
    ];

    const CART = [
        {
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
                    'price': 300,
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
                    'price': 200,
                    'starRating': 4.2,
                    'imageUrl': 'http://gilette.com/2/',
                    'stock': 1000,
                },
            },
            productQuantities: {
                '59d89875479ded2a718bac13': 2,
                '59d89875479ded2a718bac14': 2,
            },
            lastModified: 1551162037567,
            orderDate: 1550482348428,
            totalPrice: 1000,
        },
    ];

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

    const ORDERED_CART = [{
        id: '5c6bf8a306057eeb12dfb7c4',
        status: 'ordered',
        username: 'alice',
        products: {
            '59d89875479ded2a718bac13': {
                'id': '59d89875479ded2a718bac13',
                'productName': 'colgatewhite',
                'productCode': 'GE-1206',
                'releaseDate': 1483539355570,
                'lastModified': 1507367029712,
                'description': 'dentifrice',
                'price': 300,
                'starRating': 4.5,
                'imageUrl': 'http://colgate.com/2/',
                'stock': 1000,
            },
        },
        productQuantities: {
            '59d89875479ded2a718bac13': 2,
        },
        lastModified: 1551162037567,
        orderDate: 1550482348428,
        totalPrice: 1000,
    },
    {
        id: '5c7ab5bf06057e568d0f76c4',
        status: 'ordered',
        username: 'alice',
        products: {
            '5c7ab0e006057e50bbc8d898': {
                'id': '5c7ab0e006057e50bbc8d898',
                'productName': 'Garden Cart',
                'productCode': 'GDN-0023',
                'releaseDate': 1489805155570,
                'lastModified': 1551544544142,
                'description': '15 gallon capacity rolling garden cart',
                'price': 3295,
                'starRating': 4.2,
                'imageUrl': 'http://openclipart.org/image/300px/svg_to_png/58471/garden_cart.png',
                'stock': 14,
            },
        },
        productQuantities: {
            '5c7ab0e006057e50bbc8d898': 1,
        },
        lastModified: 1551545797603,
        orderDate: 1551545797603,
        totalPrice: 3295,
    }];

    const THIRD_CART = {
        id: '5c6bf8a306057eeb12dfb7c4',
        status: 'pending',
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
            '59d89875479ded2a718bac13': 1,
            '59d89875479ded2a718bac14': 2,
        },
        lastModified: 1551162037567,
        orderDate: 1550482348428,
        totalPrice: 700,
    };

    let component;

    const createWrapper = () => shallow(<Cart {...defaultProps} />);

    beforeEach(async () => {
        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(CART), ok: true });

        component = createWrapper();

        // eslint-disable-next-line no-undef
        await flushPromises();
    });


    afterEach(() => {
        jest.clearAllMocks();
        fetch.resetMocks();
    });

    it('should match snapshot', () => {
        expect(component).toMatchSnapshot();
    });

    it('should render carts page when CART is not empty', () => {
        const table = component.find(Table);

        expect(table.length).toBe(1);
    });

    it('should call fetch for rendering carts data', () => {
        expect(fetch).toHaveBeenCalledWith(`/api/carts/user/${defaultProps.user}`, {
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        });
    });

    it('should render message when CART is not created', async () => {
        fetch.resetMocks();

        fetch.mockResolvedValueOnce({ json: () => Promise.resolve([{}]), ok: true });

        const wrapper = createWrapper();

        // eslint-disable-next-line no-undef
        await flushPromises();

        const message = wrapper.find(Label);

        expect(message.length).toBe(1);
    });

    it('should render message when CART is created but empty', async () => {
        fetch.resetMocks();

        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(EMPTY_CART), ok: true });

        const wrapper = createWrapper();

        // eslint-disable-next-line no-undef
        await flushPromises();

        const message = wrapper.find(Label);

        expect(message.length).toBe(1);
    });

    it('should render Cart Empty message when there is no pending cart available', async () => {
        fetch.resetMocks();

        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(ORDERED_CART), ok: true });

        const wrapper2 = createWrapper();

        // eslint-disable-next-line no-undef
        await flushPromises();

        const message = wrapper2.find(Label);

        expect(message.length).toBe(1);
    });

    it('should redirect to product page on click of product name', () => {
        const table = component.find(Table);
        const productName = table.find('a').at(1).prop('href');
        const productId = Object.keys(CART[0].products)[0];

        expect(productName).toBe(`/products/${productId}`);
    });

    it('should redirect to product page on click of product image', () => {
        const table = component.find(Table);
        const productImage = table.find('a').at(0).prop('href');
        const productId = Object.keys(CART[0].products)[0];

        expect(productImage).toBe(`/products/${productId}`);
    });

    it('should add product quantity on click of add icon', async () => {
        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(SECOND_CART), ok: true });

        const table = component.find(Table);
        const addIcon = table.find(FaPlusSquare).at(0);
        const previousQuantity = table.find(Label).at(1);

        expect(previousQuantity.prop('children')).toEqual(2);

        addIcon.simulate('click');

        component.update();

        // eslint-disable-next-line no-undef
        await flushPromises();

        expect(fetch).toHaveBeenCalledWith('/api/carts/5c6bf8a306057eeb12dfb7c4/product/59d89875479ded2a718bac13', {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        // eslint-disable-next-line no-undef
        await flushPromises();

        const quantity = component.find(Table).find(Label).at(1);

        expect(quantity.prop('children')).toEqual(3);
    });

    it('should reduce the count of product on click on minus icon', async () => {
        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(THIRD_CART), ok: true });
        const table = component.find(Table);
        const subtractIcon = component.find(FaMinusSquare).at(0);
        const previousQuantity = table.find(Label).at(1);

        expect(previousQuantity.prop('children')).toEqual(2);

        subtractIcon.simulate('click');

        component.update();

        expect(fetch).toHaveBeenCalledWith('/api/carts/5c6bf8a306057eeb12dfb7c4/product/59d89875479ded2a718bac13', {
            credentials: 'include',
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        // eslint-disable-next-line no-undef
        await flushPromises();

        const quantity = component.find(Table).find(Label).at(1);

        expect(quantity.prop('children')).toEqual(1);
    });

    it('should render total price after adding new item', async () => {
        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(SECOND_CART), ok: true });
        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(THIRD_CART), ok: true });

        const table = component.find(Table);
        const addIcon = table.find(FaPlusSquare).at(0);
        const subtractIcon = table.find(FaMinusSquare).at(0);

        const initialPrice = component.find(Label).at(5);

        expect(initialPrice.props().children).toEqual(`SubTotal: ${CART[0].totalPrice}`);

        addIcon.simulate('click');
        component.update();

        // eslint-disable-next-line no-undef
        await flushPromises();

        const previousTotalPrice = component.find(Label).at(5);

        expect(previousTotalPrice.props().children).toEqual(`SubTotal: ${SECOND_CART.totalPrice}`);

        subtractIcon.simulate('click');
        component.update();

        // eslint-disable-next-line no-undef
        await flushPromises();

        const totalPrice = component.find(Label).at(5);

        expect(totalPrice.prop('children')).toEqual(`SubTotal: ${THIRD_CART.totalPrice}`);
    });
});
