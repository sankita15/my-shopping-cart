import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import * as PropTypes from 'prop-types';
import LoginPage from './page/LoginPage';
import Products from './page/Products';
import ItemDescription from './page/ItemDescription';
import Cart from './page/Cart';
import OrderPage from './page/OrderPage';
import RegistrationPage from './page/RegistrationPage';

// const ItemDescriptionWrapper = ({ match: { params: { id } } }, props) => <ItemDescription id={id} {...props}/>;

const Page = props => (
    <Router>
        <Switch>
            <Route exact path={['/', '/products']}>
                <Products {...props} />
            </Route>
            <Route exact path={['/logout', '/login']}>
                <LoginPage {...props} />
            </Route>
            <Route exact path="/carts">
                <Cart {...props} />
            </Route>
            <Route
                exact
                path="/products/:id"
                render={({ match: { params: { id } } }) => <ItemDescription {...props} id={id} />}
            />
            <Route exact path="/buy">
                <OrderPage {...props} />
            </Route>
            <Route exact path="/users/signup">
                <RegistrationPage />
            </Route>
        </Switch>
    </Router>
);

Page.propTypes = {
    user: PropTypes.string,
};

export default Page;
