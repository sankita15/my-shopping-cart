import React from 'react';
import { Card, Label, CardText, Col, CardBody, Button, Media, Row, Alert } from 'reactstrap';
import PropTypes from 'prop-types';
import { getCartProducts, getCartResponse, getTotalPrice, isCartEmpty } from './CartAvailability';

export default class OrderPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cartDetails: {},
            showAlert: false,
            isDisabled: false,
        };

        this.getProduct = this.getProduct.bind(this);
    }

    componentDidMount() {
        const { user } = this.props;

        getCartResponse(user)
            .then(cartDetails => this.setState({ cartDetails }))
            .catch(status => console.warn(status));
    }

    getProduct = ({ id, productName, productCode, price, imageUrl, starRating }, productQuantity) => {
        const deliveryDate = new Date();

        return (
            <CardBody className="place-order" key={id}>
                <Media src={imageUrl} className="order-image" />
                <CardText className="product-name">
                    {productName}
                </CardText>
                <CardText className="product-code">
                    {productCode}
                </CardText>
                <CardText className="product-price">
                    {`Rs. ${price}`}
                </CardText>
                <CardText className="product-quantity">
                    {`Quantity: ${productQuantity}`}
                </CardText>
                <CardText className="product-rating">
                    {`Star Rating: ${starRating}`}
                </CardText>
                <CardText className="product-delivery">
                    {`Estimated Delivery : ${`${deliveryDate.getDate()}/${deliveryDate.getMonth() + 1}/${deliveryDate.getFullYear()}`}`}
                </CardText>
                <div className="horizontal-line" />
            </CardBody>
        );
    };

    placeOrder() {
        const { cartDetails: { id } } = this.state;

        fetch(`/api/carts/order/${id}`, {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        }).then(res => (res.ok ? res.json() : Promise.reject(res.status)))
            .then(() => {
                this.setState({ showAlert: true, isDisabled: true });
            }).catch(status => console.warn(status));
    }

    productOrderedAlert() {
        const { showAlert } = this.state;
        return (
            <div>
                <Alert color="success" className="alert-box" isOpen={showAlert} toggle={() => this.onDismiss()}>
                Yayy!!! Your Order Got Placed.
                Click on cancel to continue shopping
                </Alert>
            </div>
        );
    }

    render() {
        const { user } = this.props;
        const { showAlert, isDisabled, cartDetails } = this.state;
        const delivery = 40;
        
        if (!isCartEmpty(cartDetails)) {
            return <Label>Your Cart is empty.Please add item to your cart</Label>;
        }

        return (
            <div>
                <Row>{ showAlert && this.productOrderedAlert()}</Row>
                <Label className="welcome-order">{`Hello ${user.toUpperCase()}, Review Your Order`}</Label>
                <Row>
                    <Col sm={8}>
                        <Card className="order-card">
                            {
                                getCartProducts(cartDetails, this.getProduct)
                            }
                        </Card>
                    </Col>
                    <Col sm={3}>
                        <Card className="order-value-card">
                            <CardBody className="order-price">
                                <CardText className="order-summary">Order Summary</CardText>
                                <CardText>{`Items Price: ${getTotalPrice(cartDetails.totalPrice)}`}</CardText>
                                <CardText>
                                    {' '}
                                    {`Delivery: Rs ${delivery}`}
                                    {' '}
                                </CardText>
                                <CardText>
                                    {' '}
                                    {`Cart Total: Rs ${getTotalPrice(cartDetails.totalPrice) + delivery}`}
                                    {' '}
                                </CardText>
                                <Button className="place-order-button" disabled={isDisabled} onClick={() => this.placeOrder()}>Place Your Order</Button>
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
            </div>
        );
    }

    onDismiss() {
        this.setState({
            showAlert: false,
        });
        this.redirectToProductPage();
    }

    redirectToProductPage = () => window.location.assign('/products');
}

OrderPage.propTypes = {
    user: PropTypes.string.isRequired,
};
