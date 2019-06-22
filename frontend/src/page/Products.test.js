import React from 'react';
import { shallow } from 'enzyme';
import Products from './Products';
import Item from '../components/Item';


describe('Product page', () => {
    const createWrapper = () => shallow(<Products />);

    const products = [
        {
            'id': '5c61496906057ea38af387cd',
            'productName': 'Leaf Rake',
            'productCode': 'GDN-0011',
            'releaseDate': 1489916755570,
            'lastModified': 1549879657904,
            'description': 'Leaf rake with 48-inch wooden handle.',
            'price': 1995,
            'starRating': 3.2,
            'imageUrl': 'http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png',
            'stock': 400,
        },
        {
            'id': '5c61496906057ea38af387ce',
            'productName': 'Garden Cart',
            'productCode': 'GDN-0023',
            'releaseDate': 1489805155570,
            'lastModified': 1549879657912,
            'description': '15 gallon capacity rolling garden cart',
            'price': 3295,
            'starRating': 4.2,
            'imageUrl': 'http://openclipart.org/image/300px/svg_to_png/58471/garden_cart.png',
            'stock': 20,
        },
    ];

    afterEach(() => {
        jest.clearAllMocks();
        fetch.resetMocks();
    });

    it('should match snapshot of Product page', async () => {
        fetch.mockResolvedValueOnce({ json: () => Promise.resolve([]), ok: true });

        const component = createWrapper();

        // eslint-disable-next-line no-undef
        await flushPromises();

        expect(component).toMatchSnapshot();
    });

    it('should render list of products', async () => {
        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(products), ok: true });
        const component = createWrapper();

        expect(component.find(Item).length).toBe(0);

        expect(fetch).toHaveBeenCalledWith('/api/products', {
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        // eslint-disable-next-line no-undef
        await flushPromises();

        expect(component.find(Item).length).toBe(2);
    });
});
