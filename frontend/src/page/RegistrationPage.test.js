import React from 'react';
import { shallow } from 'enzyme';
import { Label, Card, Button, CardBody, InputGroup, Input } from 'reactstrap';
import RegistrationPage from './RegistrationPage';


describe('Registration Page', () => {
    const INVALID_EMAIL = 'xyz@abc';

    const INVALID_FIRSTNAME = 'ab'; // less than 3 & more than 10

    const INVALID_LASTNAME = 'ab'; // less than 3 & more than 10

    const INVALID_USERNAME = 'ab'; // less than 3 & more than 10

    const INVALID_PASSWORD = 'abcdefghij'; // less than 4 & more than 8

    const SIGNUP_BODY = {
        email: 'xyz@abc.com',
        username: 'abcdef',
        password: 'toto',
        firstName: 'abcdef',
        lastName: 'abcdef',
    };

    window.location.assign = jest.fn();

    let component;
    let firstName;
    let lastName;
    let email;
    let username;
    let password;
    let confirmPassword;
    let button;

    const createWrapper = () => shallow(<RegistrationPage />);

    beforeEach(() => {
        component = createWrapper();

        const cardBody = component.find(Card).find(CardBody);

        firstName = cardBody.find(Input).at(0);
        lastName = cardBody.find(Input).at(1);
        email = cardBody.find(InputGroup).find(Input);
        username = cardBody.find(Input).at(3);
        password = cardBody.find(Input).at(4);
        confirmPassword = cardBody.find(Input).at(5);
        button = component.find(Button);
    });

    const testSetUpMethod = () => {
        firstName.prop('onChange')({ target: { value: SIGNUP_BODY.firstName } });
        lastName.prop('onChange')({ target: { value: SIGNUP_BODY.lastName } });
        username.prop('onChange')({ target: { value: SIGNUP_BODY.username } });
        password.prop('onChange')({ target: { value: SIGNUP_BODY.password } });
        confirmPassword.prop('onChange')({ target: { value: SIGNUP_BODY.password } });
    };

    afterEach(() => {
        fetch.resetMocks();
    });

    it('should match snapshot', () => {
        expect(component).toMatchSnapshot();
    });

    it('should render Registration Page field', () => {
        expect(component.find(Label).length).toEqual(1);
        expect(component.find(Card).length).toEqual(1);
        expect(button.length).toEqual(1);
    });

    it('should render invalid field when email address is not valid', () => {
        expect(email.prop('invalid')).toBeFalsy();

        email.prop('onChange')({ target: { value: INVALID_EMAIL } });

        const modifiedEmail = component.find(Card).find(CardBody).find(InputGroup).find(Input);

        expect(modifiedEmail.prop('invalid')).toBeTruthy();
    });

    it('should render invalid field when username is not valid', () => {
        expect(username.prop('invalid')).toBeFalsy();

        username.prop('onChange')({ target: { value: INVALID_USERNAME } });

        const modifiedUsername = component.find(Card).find(CardBody).find(Input).at(3);

        expect(modifiedUsername.prop('invalid')).toBeTruthy();
    });

    it('should render invalid field when password is not valid', () => {
        expect(password.prop('invalid')).toBeFalsy();

        password.prop('onChange')({ target: { value: INVALID_PASSWORD } });

        const modifiedPassword = component.find(Card).find(CardBody).find(Input).at(4);

        expect(modifiedPassword.prop('invalid')).toBeTruthy();
    });

    it('should match password with confirm password field', () => {
        password.prop('onChange')({ target: { value: SIGNUP_BODY.password } });
        confirmPassword.prop('onChange')({ target: { value: INVALID_PASSWORD } });

        const modifiedConfirmPassword = component.find(Card).find(CardBody).find(Input).at(5);

        expect(modifiedConfirmPassword.prop('invalid')).toBeTruthy();
    });

    it('should render invalid field when firstName is not valid', () => {
        expect(firstName.prop('invalid')).toBeFalsy();

        firstName.prop('onChange')({ target: { value: INVALID_FIRSTNAME } });

        const modifiedFirstName = component.find(Card).find(CardBody).find(Input).at(0);

        expect(modifiedFirstName.prop('invalid')).toBeTruthy();
    });

    it('should render invalid field when lastName is not valid', () => {
        expect(lastName.prop('invalid')).toBeFalsy();

        lastName.prop('onChange')({ target: { value: INVALID_LASTNAME } });

        const modifiedLastName = component.find(Card).find(CardBody).find(Input).at(1);

        expect(modifiedLastName.prop('invalid')).toBeTruthy();
    });

    it('should disable button if any field is invalid', () => {
        testSetUpMethod();

        email.prop('onChange')({ target: { value: INVALID_EMAIL } });

        expect(button.props().disabled).toBeTruthy();
    });

    it('should enable button if all the fields are valid', () => {
        expect(button.props().disabled).toBeTruthy();

        testSetUpMethod();
        email.prop('onChange')({ target: { value: SIGNUP_BODY.email } });

        component.update();

        expect(component.find(Button).props().disabled).toBeFalsy();
    });

    it('should call fetch for POST call for sign up', async () => {
        fetch.mockResolvedValueOnce({ json: () => Promise.resolve(), ok: true });

        testSetUpMethod();
        email.prop('onChange')({ target: { value: SIGNUP_BODY.email } });

        button.simulate('click');

        component.update();

        // eslint-disable-next-line no-undef
        await flushPromises();

        expect(fetch).toHaveBeenCalledWith('/api/users/signup', {
            headers: {
                'Content-Type': 'application/json',
            },
            method: 'POST',
            body: JSON.stringify(SIGNUP_BODY),
        });

        expect(window.location.assign).toHaveBeenCalledWith('/login');
    });
});
