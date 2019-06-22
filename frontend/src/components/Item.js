import React from 'react';
import { Card, CardImg, CardBody, CardTitle, CardText, CardSubtitle } from 'reactstrap';
import * as PropTypes from 'prop-types';

const Item = ({ imageUrl, description, price, productName, id }) => (
    <a href={`/products/${id}`}>
        <div className="item-page">
            <Card className="item-card">
                <CardImg top width="100%" className="item-page-image" src={imageUrl} />
                <CardBody className="item-body">
                    <CardTitle>{productName}</CardTitle>
                    <CardSubtitle>
                                Rs.
                        {price}
                    </CardSubtitle>
                    <CardText>{description}</CardText>
                </CardBody>
            </Card>
        </div>
    </a>
);

Item.propTypes = {
    id: PropTypes.string.isRequired,
    productName: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
    imageUrl: PropTypes.string.isRequired,
};

export default Item;
