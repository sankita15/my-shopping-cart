import React from 'react';
import {
    Label,
    InputGroup,
    InputGroupAddon,
    Input,
    Button,
    Col,
    Row,
    Card,
    CardBody,
    FormFeedback,
    FormGroup,
} from 'reactstrap';

export default class RegistrationPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            firstName: null,
            lastName: null,
            username: null,
            email: null,
            password: null,
            confirmPasswordMatch: null,
        };
    }

    handleFieldChange(fieldName, fieldValue, regex) {
        const result = regex.test(fieldValue) ? fieldValue : '';
        this.setState({
            [fieldName]: result,
        });
    }

    confirmPassword(fieldValue) {
        const { password } = this.state;
        this.setState({
            confirmPasswordMatch: password === fieldValue,
        });
    }

    canSubmit() {
        const { firstName, lastName, username, email, password, confirmPasswordMatch } = this.state;
        return firstName && lastName && username && email && password && confirmPasswordMatch;
    }

    _handleSubmit() {
        const { email, username, password, firstName, lastName } = this.state;

        return fetch('/api/users/signup', {
            headers: {
                'Content-Type': 'application/json',
            },
            method: 'POST',
            body: JSON.stringify({
                email,
                username,
                password,
                firstName,
                lastName,
            }),
        })
            .then(res => (res.ok ? Promise.resolve() : Promise.reject(res.status)))
            .catch(status => console.warn('Sign up failed', status));
    }

    async submitForm() {
        await this._handleSubmit();
        this.navigateToPage('/login');
    }

    navigateToPage = route => window.location.assign(route);

    render() {
        const { email, username, password, confirmPasswordMatch, firstName, lastName } = this.state;

        return (
            <div className="registration-page">
                <Col>
                    <Row sm={3}>
                        <Card>
                            <CardBody>
                                <Label className="heading1">Signup with ONE-STOP-SHOP</Label>
                                <FormGroup>
                                    <Input
                                        className="firstname"
                                        placeholder="Enter Your Firstname"
                                        onChange={e => this.handleFieldChange('firstName', e.target.value, /^[a-zA-Z0-9]{3,20}$/)}
                                        invalid={!firstName && firstName !== null}
                                    />
                                    <FormFeedback>
                                            firstName cannot be less than 3 letters
                                    </FormFeedback>
                                </FormGroup>
                                <FormGroup>
                                    <Input
                                        className="lastname"
                                        placeholder="Enter your Lastname"
                                        onChange={e => this.handleFieldChange('lastName', e.target.value, /^[a-zA-Z0-9]{3,25}$/)}
                                        invalid={!lastName && lastName !== null}
                                    />
                                    <FormFeedback>
                                            Lastname cannot be less than 3 letters
                                    </FormFeedback>
                                </FormGroup>
                                <InputGroup className="email">
                                    <InputGroupAddon addonType="prepend">@</InputGroupAddon>
                                    <Input
                                        placeholder="Your Email address"
                                        onChange={e => this.handleFieldChange('email', e.target.value, /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/)}
                                        invalid={!email && email !== null}
                                    />
                                    <FormFeedback>
                                        Please provide valid email address.
                                        Email must include @ followed by hostname.
                                    </FormFeedback>
                                </InputGroup>
                                <FormGroup>
                                    <br />
                                    <Input
                                        className="username"
                                        placeholder="Choose Your Username"
                                        onChange={e => this.handleFieldChange('username', e.target.value, /^[a-zA-Z0-9]{3,10}$/)}
                                        invalid={!username && username !== null}
                                    />
                                    <FormFeedback>
                                    Please choose a username of length between 3 to 10
                                    </FormFeedback>
                                </FormGroup>
                                <FormGroup>
                                    <Input
                                        className="password"
                                        placeholder="Choose Your Password"
                                        type="password"
                                        onChange={e => this.handleFieldChange('password', e.target.value, /^[a-zA-Z0-9!@#$%^&*]{4,8}$/)}
                                        invalid={!password && password !== null}
                                    />
                                    <FormFeedback>
                                    Please choose a password of length between 4 to 8.
                                    </FormFeedback>
                                </FormGroup>
                                <FormGroup>
                                    <Input
                                        className="password"
                                        placeholder="Confirm Your Password"
                                        type="password"
                                        onChange={e => this.confirmPassword(e.target.value)}
                                        invalid={!confirmPasswordMatch && confirmPasswordMatch !== null}
                                    />
                                    <FormFeedback>
                                        Password & Confirm Password does not match.
                                    </FormFeedback>
                                </FormGroup>
                                <Button type="submit" disabled={!this.canSubmit()} className="register-button" onClick={() => this.submitForm()}>Register</Button>
                                <Row className="login-back-page"><a className="login-link" href="/login">Back To Login</a></Row>
                            </CardBody>
                        </Card>
                    </Row>
                </Col>
            </div>
        );
    }
}
