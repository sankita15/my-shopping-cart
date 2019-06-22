import React from 'react';
import Item from '../components/Item';
import Header from './Header';


export default class Products extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            products: [],
        };
    }

    componentDidMount() {
        fetch('/api/products', {
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(res => (res.ok ? res.json() : Promise.reject(res.status)))
            .then(products => this.setState({ products }))
            .catch(e => console.warn(e));
    }

    render() {
        const { products } = this.state;

        return (
            <div className="product-login">
                <Header />
                {products.map(({ productName, price, description, imageUrl, id }) => (
                    <Item
                        productName={productName}
                        price={price}
                        description={description}
                        imageUrl={imageUrl}
                        id={id}
                        key={id}
                    />
                ))}
            </div>
        );
    }
}
