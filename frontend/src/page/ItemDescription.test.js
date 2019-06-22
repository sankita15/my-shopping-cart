import React from 'react';
import { shallow } from 'enzyme';
import { Media, Label, Button, Row } from 'reactstrap';
import { FaCartPlus } from 'react-icons/fa';
import ItemDescription from './ItemDescription';


describe('Item Description Page', () => {
    const defaultProps = {
        id: '5c6a7bac06057ee4fc0d3646',
        user: 'alice',
    };

    const ITEM = {
        id: defaultProps.id,
        productName: 'Leaf Rake',
        productCode: 'GDN-0011',
        description: 'Leaf rake with 48-inch wooden handle.',
        price: 1995,
        starRating: 3.2,
        imageUrl: 'http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png',
        stock: 400,
    };

    const OUT_OF_STOCK_ITEM = {
        id: '5c868bc406057e1cf0ab11f1',
        productName: 'Garden Cart',
        productCode: 'GDN-0023',
        description: '15 gallon capacity rolling garden cart',
        price: 3295,
        starRating: 4.2,
        imageUrl: 'http://openclipart.org/image/300px/svg_to_png/58471/garden_cart.png',
        stock: 0,
    };

    const OUT_OF_STOCK_CART = [{
        id: '5c868c1206057e2278747208',
        status: 'pending',
        username: 'alice',
        products: {
            '5c868bc406057e1cf0ab11f1': {
                'id': '5c868bc406057e1cf0ab11f1',
                'productName': 'Garden Cart',
                'productCode': 'GDN-0023',
                'releaseDate': 1489805155570,
                'lastModified': 1552321476007,
                'description': '15 gallon capacity rolling garden cart',
                'price': 3295,
                'starRating': 4.2,
                'imageUrl': 'http://openclipart.org/image/300px/svg_to_png/58471/garden_cart.png',
                'stock': 0,
            },
        },
        productQuantities: {
            '5c868bc406057e1cf0ab11f1': 20,
        },
        lastModified: 1552386843888,
        orderDate: 1552370305496,
        totalPrice: 19770,
    }];

    const ORDERED_CART = [{
        id: '5c71694e06057e0960b31579',
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

    const EMPTY_CART = {
        id: '',
        status: null,
        username: 'alice',
        products: {},
        productQuantities: {},
        lastModified: 1489805155570,
        orderDate: 1489805155570,
        totalPrice: 0,
    };

    const CART = {
        ...EMPTY_CART,
        id: '5c71694e06057e0960b31579',
        status: 'pending',
    };

    const FILLED_CART = {
        ...CART,
        products: {
            '5c6fbc0206057e041f95c27c': {
                'id': '5c6fbc0206057e041f95c27c',
                'productName': 'Garden Cart',
                'productCode': 'GDN-0023',
                'releaseDate': 1489805155570,
                'lastModified': 1550826498308,
                'description': '15 gallon capacity rolling garden cart',
                'price': 3295,
                'starRating': 4.2,
                'imageUrl': 'http://openclipart.org/image/300px/svg_to_png/58471/garden_cart.png',
                'stock': 19,
            },
        },
        productQuantities: {
            '5c6fbc0206057e041f95c27c': 1,
        },
        totalPrice: 3295,
    };

    const createWrapper = () => shallow(<ItemDescription {...defaultProps} />);

    let component;

    beforeEach(async () => {
        fetch.mockResolvedValueOnce({ status: 404 });
        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(CART), ok: true });
        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(ITEM), ok: true });
        component = createWrapper();

        // eslint-disable-next-line no-undef
        await flushPromises();
    });

    afterEach(() => {
        fetch.resetMocks();
    });

    window.location.assign = jest.fn();

    describe('Item Description Page', () => {
        it('should render Item Description Page', async () => {
        // eslint-disable-next-line no-undef
            await flushPromises();

            const image = component.find(Media);
            const label = component.find(Label);
            const button = component.find(Button);

            expect(image.length)
                .toBe(1);
            expect(label.length)
                .toBe(6);
            expect(button.length)
                .toBe(2);
        });

        it('should match snapshot', () => {
            expect(component).toMatchSnapshot();
        });

        it('should call fetch to render item description', async () => {
            expect(fetch).toHaveBeenCalledWith(`/api/carts/user/${defaultProps.user}`, {
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            expect(fetch).toHaveBeenCalledWith('/api/carts', {
                credentials: 'include',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: expect.any(String),
            });

            expect(fetch).toHaveBeenCalledWith(`/api/products/${defaultProps.id}`, { credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                } });

            expect(component.find(Media).prop('src')).toBe(ITEM.imageUrl);
        });
    });

    describe('Add To Cart', () => {
        it('should call fetch for getting cart data if no cart present', async () => {
            fetch.mockResolvedValueOnce({ json: () => Promise.resolve(FILLED_CART), ok: true });

            const addToCart = component.find(Button).at(0);

            addToCart.simulate('click');

            component.update();

            // eslint-disable-next-line no-undef
            await flushPromises();

            expect(fetch.mock.calls.length)
                .toBe(4);

            expect(fetch.mock.calls[3][0])
                .toBe(`/api/carts/${CART.id}/product/${ITEM.id}`);
            expect(fetch.mock.calls[3][1])
                .toEqual({
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    method: 'POST',
                });
        });

        it('should create new cart for placing order if no more pending cart available', async () => {
            fetch.resetMocks();
            fetch.mockResolvedValueOnce({ json: () => Promise.resolve(ORDERED_CART), ok: true });
            fetch.mockResolvedValueOnce({ json: () => Promise.resolve(CART), ok: true });
            fetch.mockResolvedValueOnce({ json: () => Promise.resolve(ITEM), ok: true });
            fetch.mockResolvedValueOnce({ json: () => Promise.resolve(FILLED_CART), ok: true });

            const wrapper = createWrapper();

            // eslint-disable-next-line no-undef
            await flushPromises();

            const addToCart = wrapper.find(Button).at(0);

            addToCart.simulate('click');

            wrapper.update();

            expect(fetch.mock.calls.length)
                .toBe(4);

            expect(fetch.mock.calls[3][0])
                .toBe(`/api/carts/${CART.id}/product/${ITEM.id}`);
            expect(fetch.mock.calls[3][1])
                .toEqual({
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    method: 'POST',
                });
        });

        it('should increment items in cart quantity when click on ADD TO CART icon', async () => {
            fetch.mockResolvedValueOnce({ json: () => Promise.resolve(FILLED_CART), ok: true });

            expect(component.find(Row).at(0).find(Label).prop('children')).toEqual(0);

            const addToCartButton = component.find(Button).at(0);
            addToCartButton.simulate('click');

            // eslint-disable-next-line no-undef
            await flushPromises();

            component.update();

            expect(component.find(Row).at(0).find(Label).prop('children')).toEqual(1);
        });

        it('should render label for product quantity is out of stock', async () => {
            fetch.resetMocks();
            fetch.mockResolvedValueOnce({ json: () => Promise.resolve(OUT_OF_STOCK_CART), ok: true });
            fetch.mockResolvedValueOnce({ json: () => Promise.resolve(OUT_OF_STOCK_ITEM), ok: true });

            const wrapper = createWrapper();

            // eslint-disable-next-line no-undef
            await flushPromises();

            expect(wrapper.find(Button).at(0).prop('disabled')).toBeTruthy();
            expect(wrapper.find(Button).at(1).prop('disabled')).toBeTruthy();
            expect(wrapper.find(Label).at(6).length).toEqual(1);
            expect(wrapper.find(Label).at(6).prop('children')).toEqual('Out Of Stock');
        });

        it('should render label for product quantity out of stock after user clicks on add to cart button',async () => {
            fetch.resetMocks();
            fetch.mockResolvedValueOnce({ json: () => Promise.resolve([FILLED_CART]), ok: true });
            fetch.mockResolvedValueOnce({ json: () => Promise.resolve(OUT_OF_STOCK_ITEM), ok: true });
            fetch.mockResolvedValueOnce({ json: () => Promise.resolve(OUT_OF_STOCK_CART), ok: true });

            const wrapper = createWrapper();

            // eslint-disable-next-line no-undef
            await flushPromises();

            const addToCartButton = wrapper.find(Button).at(0);
            addToCartButton.simulate('click');

            // eslint-disable-next-line no-undef
            await flushPromises();

            wrapper.update();

            expect(wrapper.find(Button).at(0).prop('disabled')).toBeTruthy();
            expect(wrapper.find(Button).at(1).prop('disabled')).toBeTruthy();
            expect(wrapper.find(Label).at(6).length).toEqual(1);
            expect(wrapper.find(Label).at(6).prop('children')).toEqual('Out Of Stock');
        });
    });

    describe('Buy Now', () => {
        it('should redirect to BuyNow Page by adding product when click on BUY NOW button', async () => {
            fetch.mockResolvedValueOnce({ json: () => Promise.resolve(FILLED_CART), ok: true });

            const button = component.find(Button).at(1);
            button.simulate('click');

            expect(component.find(Row).at(0).find(Label).prop('children')).toEqual(0);

            component.update();

            // eslint-disable-next-line no-undef
            await flushPromises();

            expect(component.find(Row).at(0).find(Label).prop('children')).toEqual(1);

            expect(window.location.assign).toHaveBeenCalledWith('/buy');
        });
    });

    describe('Click on cart icon', () => {
        it('should redirect to /carts Page when click on CART icon', () => {
            const cartIcon = component.find(Row).at(1).find(FaCartPlus);
            cartIcon.simulate('click');

            component.update();

            expect(window.location.assign).toHaveBeenCalledWith('/carts');
        });
    });
});
