import React, {useState} from 'react'
import {DatePicker, Form, Input, Modal, Select} from "antd";
import {config} from "../Config";

function AuthorModal(props) {

    const {showAuthorEditModal, setShowAuthorEditModal, handleSave} = props;
    const [bookOptions, setBookOptions] = useState([]);

    const [form] = Form.useForm();
    const firstName = Form.useWatch('firstName', form);
    const lastName = Form.useWatch('lastName', form);
    const birthday = Form.useWatch('birthday', form);
    const books = Form.useWatch('books', form);

    const getBooksFromRest = async () => {
        await fetch(`${config.url}/api/v1/book/list`)
            .then((res) => res.json())
            .then((authors) => setBookOptions(authors.map(e => {
                    return {
                        value: e,
                        label: e.name,
                    }
                }
            )));
    };

    const handleCancel = () => {
        setShowAuthorEditModal(false);
    }

    const handleOk = async () => {
        await form.validateFields()
            .then(() => {
                let authorToSave = {
                    firstName,
                    lastName,
                    birthday,
                    books,
                };
                handleSave(authorToSave);
                handleCancel();
            })
    }

    const handleChange = (value) => {
        console.log("value" + value);
        console.log("authors" + books);
    };

    return <Modal title='Авторы' open={showAuthorEditModal} onOk={handleOk} onCancel={handleCancel}>
        <Form
            form={form}
            labelCol={{span: 7,}}
            wrapperCol={{span: 14,}}
            layout="horizontal"
        >
            <Form.Item name={"firstName"} label="Имя" rules={[{required: true, message: "Объязательное поле!"}]}>
                <Input/>
            </Form.Item>

            <Form.Item name={"lastName"} label="Фамилия" rules={[{required: true, message: "Объязательное поле!"}]}>
                <Input/>
            </Form.Item>

            <Form.Item name={"birthday"} label="Дата рождения" rules={[{required: true, message: "Объязательное поле!"}]}>
                <DatePicker/>
            </Form.Item>

            <Form.Item name={"books"} label="Книги">
                <Select
                    labelInValue
                    onChange={handleChange}
                    options={bookOptions}
                />
            </Form.Item>
        </Form>
    </Modal>
}

export default AuthorModal;