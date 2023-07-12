import React, {useState} from 'react'
import {DatePicker, Form, Input, Modal, Select} from "antd";
import {config} from "../Config";

function GenreModal(props) {

    const {showGenreEditModal, setShowGenreEditModal, handleSave} = props;
    const [bookOptions, setBookOptions] = useState([]);

    const [form] = Form.useForm();
    const name = Form.useWatch('name', form);
    const books = Form.useWatch('books', form);

    const getBooksFromRest = async () => {
        await fetch(`${config.url}/api/v1/book/list`)
            .then((res) => res.json())
            .then((genre) => setBookOptions(genre.map(e => {
                    return {
                        value: e,
                        label: e.name,
                    }
                }
            )));
    };

    const handleCancel = () => {
        setShowGenreEditModal(false);
    }

    const handleOk = async () => {
        await form.validateFields()
            .then(() => {
                let genreToSave = {
                    name,
                    books,
                };
                handleSave(genreToSave);
                handleCancel();
            })

    }


    const handleChange = (value) => {
        console.log("value" + value);
        console.log("books" + books);
    };

    return <Modal title='Жанры' open={showGenreEditModal} onOk={handleOk} onCancel={handleCancel}>
        <Form
            form={form}
            labelCol={{span: 7,}}
            wrapperCol={{span: 14,}}
            layout="horizontal"
        >

            <Form.Item name={"name"} label="Жанр" rules={[{required: true, message: "Объязательное поле!"}]}>
                <Input />
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

export default GenreModal;