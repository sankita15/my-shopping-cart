import React from 'react';
import { shallow } from 'enzyme';
import { Nav, NavLink, NavItem } from 'reactstrap';
import { FaViadeo } from 'react-icons/fa';
import { GoSignOut } from 'react-icons/go';
import Header from './Header';

describe('Header Description', () => {
    const createWrapper = () => shallow(<Header />);

    describe('render header', () => {
        it('should match snapshot', () => {
            const component = createWrapper();

            expect(component).toMatchSnapshot();
        });

        it('should render Navs for header', () => {
            const component = createWrapper();

            const icon = component.find(Nav).find(NavItem).at(0).find(NavLink)
                .find(FaViadeo);
            const home = component.find(Nav).find(NavItem).at(1).find(NavLink);
            const logout = component.find(Nav).find(NavItem).at(2).find(NavLink)
                .find(GoSignOut);

            expect(icon.length).toEqual(1);
            expect(home.length).toEqual(1);
            expect(home.prop('children')).toEqual('Home');
            expect(logout.length).toEqual(1);
        });
    });

    describe('Home Tab', () => {
        it('should redirect to product page when click on Home Tab', () => {
            const component = createWrapper();
            const home = component.find(Nav).find(NavItem).at(1).find(NavLink);
            const homeTabLink = home.prop('href');

            expect(homeTabLink).toBe('/products');
        });
    });

    describe('LogOut Icon', () => {
        it('should log out when click on Profile icon and select log out', () => {
            const component = createWrapper();
            const logout = component.find(Nav).find(NavItem).at(2).find(NavLink);
            const logOutRedirection = logout.prop('href');

            expect(logOutRedirection).toBe('/logout');
        });
    });
});
