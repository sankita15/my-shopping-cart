import React from 'react';
import { Nav, NavLink, NavItem } from 'reactstrap';
import { FaViadeo } from 'react-icons/fa';
import { GoSignOut } from 'react-icons/go';

export default class Header extends React.Component {
    render() {
        return (
            <div className="header">
                <Nav>
                    <NavItem>
                        <NavLink className="icon">
                            <FaViadeo size={45} />
                        </NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink href="/products" className="link">
                            Home
                        </NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink href="/logout" className="log-out">
                            <GoSignOut size={25} />
                        </NavLink>
                    </NavItem>
                </Nav>
            </div>
        );
    }
}
