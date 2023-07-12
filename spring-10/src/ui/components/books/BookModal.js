import React, {useState} from 'react'
import {DatePicker, Form, Input, Modal, Select} from "antd";
import {config} from "../Config";

function BookModal(props) {

    const {showBookModal, setShowBookModal, handleSave} = props;
    const [authorOptions, setAuthorOptions] = useState([]);
    const [genreOptions, setGenreOptions] = useState([]);

    const [form] = Form.useForm();
    const name = Form.useWatch('name', form);
    const publicationDate = Form.useWatch('publicationDate', form);
    const authors = Form.useWatch('authors', form);
    const genres = Form.useWatch('genres', form);

    const getAuthorsFromRest = () => {
        setAuthorOptions([]);
        fetch(`${config.url}/api/v1/author/list`)
            .then((res) => res.json())
            .then((authors) => setAuthorOptions(authors.map(e => {
                    return {
                        value: e,
                        label: e.firstName + ' ' + e.lastName + ', '
                    }
                }
            )));
    };
    const getGenresFromRest = () => {
        setAuthorOptions([]);
        fetch(`${config.url}/api/v1/genre/list`)
            .then((res) => res.json())
            .then((authors) => setAuthorOptions(authors.map(e => {
                    return {
                        value: e,
                        label: e.firstName + ' ' + e.lastName + ', '
                    }
                }
            )));
    };

    const handleCancel = () => {
        setShowBookModal(false);
    }

    const handleOk = async () => {
        await form.validateFields()
            .then(() => {
                let bookToSave = {
                    name,
                    publicationDate,
                    authors,
                    genres
                };
                handleSave(bookToSave);
                handleCancel();
            })

    }

    const handleChange = (value) => {
        console.log("value" + value);
        console.log("authors" + authors);
    };

    return <Modal title='Книги' open={showBookModal} onOk={handleOk} onCancel={handleCancel}>
        <Form
            form={form}
            labelCol={{span: 7,}}
            wrapperCol={{span: 14,}}
            layout="horizontal"
        >
            <Form.Item name={"name"} label="Название книги" rules={[{required: true, message: "Объязательное поле!"}]}>
                <Input/>
            </Form.Item>

            <Form.Item name={"publicationDate"} label="Дата публикации" rules={[{required: true, message: "Объязательное поле!"}]}>
                <DatePicker/>
            </Form.Item>

            <Form.Item name={"authors"} label="Авторы">
                <Select
                    labelInValue
                    onChange={handleChange}
                    options={authorOptions}
                />
            </Form.Item>

            <Form.Item name={"genres"} label="Жанры">
                <Select
                    labelInValue
                    onChange={handleChange}
                    options={genreOptions}
                />
            </Form.Item>
        </Form>
    </Modal>
}

export default BookModal;