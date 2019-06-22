import React from 'react';
import {
    Input,
    Label,
    Col,
    Container,
    FormGroup,
    Form,
    Button,
    FormFeedback,
} from 'reactstrap';

export default class LoginPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: null,
            password: null,
        };
    }

    handleFieldChange(fieldName, fieldValue, regex) {
        const result = regex.test(fieldValue) ? fieldValue : '';
        this.setState({
            [fieldName]: result,
        });
    }

    canSubmit() {
        const { username, password } = this.state;
        return username && password;
    }

    render() {
        const { username, password } = this.state;
        return (
            <div className="login-form">
                <Container>
                    <Form method="post" action="/login">
                        <Col>
                            <FormGroup>
                                <h2>Login Form</h2>
                                <div className="form-group">
                                    <Label>Username</Label>
                                    <Input
                                        type="username"
                                        name="username"
                                        className="login-form__textfield"
                                        placeholder="Username"
                                        onChange={e => this.handleFieldChange('username', e.target.value, /^[a-zA-Z0-9]{3,10}$/)}
                                        invalid={!username && username !== null}
                                    />
                                    <FormFeedback>Snap! your username is not valid</FormFeedback>
                                    <small id="emailHelp" className="form-text text-muted">
                                        {'We\'ll never share your email with anyone else.'}
                                    </small>
                                </div>
                            </FormGroup>
                        </Col>
                        <Col>
                            <FormGroup>
                                <div className="form-group">
                                    <Label>Password</Label>
                                    <Input
                                        type="password"
                                        name="password"
                                        className="login-form__textfield"
                                        placeholder="*******"
                                        onChange={e => this.handleFieldChange('password', e.target.value, /^[a-zA-Z0-9!@#$%^&*]{4,8}$/)}
                                        invalid={!password && password !== null}
                                    />
                                    <FormFeedback>
                                        Password must be more than 4 characters & less than 8
                                        characters
                                    </FormFeedback>
                                </div>
                            </FormGroup>
                        </Col>
                        <Button type="submit" disabled={!this.canSubmit()} className="btn btn-primary">Submit</Button>
                    </Form>
                </Container>
            </div>
        );
    }
}
