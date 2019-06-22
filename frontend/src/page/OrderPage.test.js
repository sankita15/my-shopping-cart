import React from 'react';
import { shallow } from 'enzyme';
import { Col, Card, Label, Button, CardBody, CardText, Alert } from 'reactstrap';
import OrderPage from './OrderPage';


describe('Order Page', () => {
    const defaultProps = {
        user: 'alice',
    };

    const CART = [{
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
    }];

    const ORDERED_CART = [{
        id: '5c6bf8a306057eeb12dfb7c4',
        status: 'ordered',
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
    }];

    const createWrapper = () => shallow(<OrderPage {...defaultProps} />);

    window.location.assign = jest.fn();

    let component;

    beforeEach(async () => {
        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(CART), ok: true });

        component = createWrapper();

        // eslint-disable-next-line no-undef
        await flushPromises();
    });

    it('should match snapshot', () => {
        expect(component).toMatchSnapshot();
    });

    it('should render Order page', () => {
        const welcomeMessage = component.find(Label);
        const productsOrdered = component.find(Col).at(0).find(Card);
        const orderDetails = component.find(Col).at(1).find(Card);
        const buyNowButton = component.find(Col).at(1).find(Card).find(Button);

        expect(welcomeMessage.length).toEqual(1);
        expect(welcomeMessage.props().children).toEqual(`Hello ${defaultProps.user.toUpperCase()}, Review Your Order`);
        expect(productsOrdered.length).toEqual(1);
        expect(productsOrdered.find(CardBody).find(CardText).at(0).props().children).toEqual('colgatewhite');
        expect(orderDetails.length).toEqual(1);
        expect(orderDetails.find(CardBody).find(CardText).at(0).props().children).toEqual('Order Summary');

        expect(buyNowButton.length).toEqual(1);
    });

    it('should call fetch for getting all carts for a particular user', () => {
        expect(fetch).toHaveBeenCalledWith(`/api/carts/user/${defaultProps.user}`, {
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        });
    });

    it('should fetch /order api on click of place your order button', async () => {
        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(ORDERED_CART), ok: true });

        const buyNowButton = component.find(Col).at(1).find(Card).find(Button);

        expect(component.find(Alert).length).toEqual(0);

        buyNowButton.simulate('click');

        component.update();

        // eslint-disable-next-line no-undef
        await flushPromises();

        expect(fetch).toHaveBeenCalledWith(`/api/carts/order/${CART[0].id}`, {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        const alertBox = component.find(Alert);

        expect(alertBox.length).toEqual(1);

        alertBox.props().toggle();

        expect(window.location.assign).toHaveBeenCalledWith('/products');
    });

    it('should disable button after order gets placed', async () => {
        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(ORDERED_CART), ok: true });

        const buyNowButton = component.find(Col).at(1).find(Card).find(Button);

        expect(buyNowButton.prop('disabled')).toBeFalsy();

        buyNowButton.simulate('click');

        component.update();

        // eslint-disable-next-line no-undef
        await flushPromises();

        const buyNowButton1 = component.find(Col).at(1).find(Card).find(Button);

        expect(buyNowButton1.prop('disabled')).toBeTruthy();
    });
});
