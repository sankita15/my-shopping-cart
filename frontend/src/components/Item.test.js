import React from 'react';
import { shallow } from 'enzyme';
import { Card, CardImg, CardBody, CardTitle, CardSubtitle, CardText } from 'reactstrap';
import Item from './Item';

describe('Item page', () => {
    const defaultProps = {
        id: '5c61496906057ea38af387cd',
        productName: 'Leaf Rake',
        description: 'Leaf rake with 48-inch wooden handle.',
        price: 1995,
        imageUrl: 'http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png',
    };

    const createWrapper = () => shallow(<Item {...defaultProps} />);

    it('should find card for each Item', () => {
        const component = createWrapper();
        const card = component.find(Card);
        const cardImg = component.find(Card).find(CardImg);
        const cardBody = component.find(Card).find(CardBody);

        expect(card.length).toBe(1);
        expect(cardImg.length).toBe(1);
        expect(cardBody.length).toBe(1);
        expect(cardBody.find(CardTitle).length).toBe(1);
        expect(cardBody.find(CardSubtitle).length).toBe(1);
        expect(cardBody.find(CardText).length).toBe(1);
    });

    it('should match snapshot', () => {
        const component = createWrapper();

        expect(component).toMatchSnapshot();
    });

    it('should redirect to product details on clicking of product card', () => {
        const component = createWrapper();
        const anchorClick = component.find('a').prop('href');

        expect(anchorClick).toBe(`/products/${defaultProps.id}`);
    });
});
