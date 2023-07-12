import React, {useEffect, useState} from 'react'
import {Empty} from "antd";
import {config} from "../Config";
import AuthorModal from "./AuthorModal";
import EditableTable from "../table/EditableTable";

function Authors() {

    const [authorList, setAuthorList] = useState([]);
    const [showAuthorEditModal, setShowAuthorEditModal] = useState(false);

    useEffect(() => {
        setAuthorList([]);
        fetch(`${config.url}/api/v1/author/list`)
            .then((res) => res.json())
            .then((authors) => setAuthorList(authors));

    }, [setAuthorList]);

    const getAuthorsFromRest = async () => {
        setAuthorList([]);
        await fetch(`${config.url}/api/v1/author/list`)
            .then((res) => res.json())
            .then((authors) => setAuthorList(authors));
    };

    const createTableData = (authorListData) => {
        if (Array.isArray(authorListData) && authorListData.length) {
            return authorListData.map((e, i) => {
                return {
                    key: i,
                    id: e.id,
                    firstName: e.firstName,
                    lastName: e.lastName,
                    birthday: e.birthday,
                    books: e.books,
                    booksDisplay: e.books.map(b => b.name + ', '),
                }
            });
        } else {
            return [];
        }
    }

    let columns = [
        {
            width: '10%',
            title: 'ID',
            dataIndex: 'id',
            editable: false,
            key: 1
        },
        {
            width: '20%',
            title: 'Имя',
            dataIndex: 'firstName',
            editable: true,
            key: 2
        },
        {
            width: '10%',
            title: 'Фамилия',
            dataIndex: 'lastName',
            editable: true,
            key: 3
        },
        {
            width: '20%',
            title: 'Дата рождения',
            dataIndex: 'birthday',
            editable: true,
            key: 4
        },
        {
            width: '20%',
            title: 'Книги',
            dataIndex: 'booksDisplay',
            editable: false,
            ellipsis: true,
            key: 5
        }
    ];

    const handleDelete = async (id) => {
        fetch(`${config.url}/api/v1/author/${id}`, {method: 'DELETE'})
            .then(() => getA())
    };

    const handleAdd = () => setShowAuthorEditModal(true);

    const handleSave = async (record) => {
        fetch(`${config.url}/api/v1/author`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(record)
        })
            .then(() => getAuthorsFromRest());
    }

    let dataSource = createTableData(authorList);

    if (Array.isArray(authorList) && authorList.length) {
        return <>
            <EditableTable
                dataSource={dataSource}
                columns={columns}
                handleAdd={handleAdd}
                handleSave={handleSave}
                handleDelete={handleDelete}
            />

            <AuthorModal
                setShowAuthorEditModal={setShowAuthorEditModal}
                showAuthorEditModal={showAuthorEditModal}
                handleSave={handleSave}
            />
        </>
    } else {
        return <Empty description="Авторы не найдены"/>
    }
}

export default Authors;