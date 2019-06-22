import React from 'react';
import PropTypes from 'prop-types';
import { Media, Label, Button, Row } from 'reactstrap';
import { FaCartPlus } from 'react-icons/fa';

export default class ItemDescription extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cartQuantity: 0,
            item: {},
            cartDetails: {},
            showOutOfStockLabel: false,
            disabled: false,
        };
    }

    createCart() {
        const { user } = this.props;

        const initialCart = {
            username: user,
            products: {},
            productQuantities: {},
            lastModified: Date.now(),
            orderDate: Date.now(),
            totalPrice: 0,
        };

        return fetch('/api/carts', {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(initialCart),
        })
            .then(res => (res.ok ? res.json() : Promise.reject(res.status)))
            .then(newCart => this.setState({ cartDetails: newCart }))
            .catch(status => console.log('cart creation failed', status));
    }

    getUserCartDetails() {
        const { user } = this.props;

        return fetch(`/api/carts/user/${user}`, {
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(res => (res.ok ? res.json() : Promise.reject(res.status)))
            .then(allCart => allCart.filter(cart => cart.status === 'pending')[0])
            .then(cartDetails => (cartDetails === undefined ? Promise.reject(404) : this.setState({ cartDetails })))
            .catch((status) => {
                if ([404].includes(status)) {
                    return this.createCart();
                }
            });
    }

    async addToCart() {
        const { cartDetails: { id: cartId } } = this.state;
        const { id: productId } = this.props;

        await fetch(`/api/carts/${cartId}/product/${productId}`, {
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
            method: 'POST',
        })
            .then(res => (res.ok ? res.json() : Promise.reject(res.status)))
            .then(cartWithProduct => this.setState({ cartDetails: cartWithProduct }))
            .then(() => this.calculateCartQuantity())
            .catch((status) => {
                if (status === 409) {
                    this.toggle();
                }
            });
    }


    async componentDidMount() {
        const { id } = this.props;

        await this.getUserCartDetails();

        fetch(`/api/products/${id}`, {
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(res => (res.ok ? res.json() : Promise.reject(res.status)))
            .then(item => this.setState({ item }))
            .then(() => this.isProductAvailable())
            .then(() => this.calculateCartQuantity())
            .catch(e => (console.warn(e)));
    }

    render() {
        const { item: { productName, description, price, imageUrl, starRating, productCode }, cartQuantity, disabled, showOutOfStockLabel } = this.state;

        return (
            <div className="item-detail-page">
                <div className="cart-icon">
                    <Row>
                        <Label className="cart-quantity">{cartQuantity}</Label>
                    </Row>
                    <Row>
                        <FaCartPlus size={45} onClick={() => this.navigateToPage('/carts')} />
                    </Row>
                </div>
                <div className="item-image">
                    <Media src={imageUrl} alt={productName} />
                </div>
                <div className="item-container">
                    <div className="item-details">
                        <Label className="item-name">{productName}</Label>
                        <Label>
                            Star Rating:
                            {starRating}
                        </Label>
                        <Label>
                            Product Code:
                            {productCode}
                        </Label>
                        <Label>
                            Description:
                            {description}
                        </Label>
                        <Label>
                            Rs.
                            {price}
                        </Label>
                        { showOutOfStockLabel && this.renderOutOfStockLabel() }
                        <br />
                        <br />
                    </div>
                    <div className="button-group">
                        <Button className="cart" onClick={() => this.addToCart()} disabled={disabled}>
ADD TO
                            CART
                        </Button>
                        <Button
                            className="buy"
                            onClick={() => this.addProductAndNavigateToBuyPage()}
                            disabled={disabled}
                        >
BUY NOW
                        </Button>
                    </div>
                </div>
            </div>
        );
    }

    navigateToPage = route => window.location.assign(route);

    async addProductAndNavigateToBuyPage() {
        await this.addToCart();
        this.navigateToPage('/buy');
    }

    calculateCartQuantity() {
        const { cartDetails: { productQuantities } } = this.state;

        const quantity = Object.values(productQuantities)
            .reduce(((previousValue, productQuantity) => previousValue + productQuantity), 0);


        this.setState({
            cartQuantity: quantity,
        });
    }

    isProductAvailable() {
        const { item: { stock } } = this.state;
        if (stock === 0) {
            this.toggle();
        }
    }

    toggle() {
        this.setState(prevState => ({
            showOutOfStockLabel: !prevState.showOutOfStockLabel,
            disabled: !prevState.disabled,
        }));
    }

    renderOutOfStockLabel = () => (
        <div>
            <Label className="out-of-stock-label">Out Of Stock</Label>
        </div>
    );
}

ItemDescription.propTypes = {
    id: PropTypes.string.isRequired,
    user: PropTypes.string.isRequired,
};
