import React, {useEffect, useState} from 'react'
import {Empty} from "antd";
import {config} from "../Config";
import BookModal from "./BookModal";
import EditableTable from "../table/EditableTable";

function Books() {

    const [bookList, setBookList] = useState([]);
    const [showBookEditModal, setShowBookEditModal] = useState(false);

    useEffect(() => {
        setBookList([]);
        fetch(`${config.url}/api/v1/book/list`)
            .then((res) => res.json())
            .then((books) => setBookList(books));

    }, [setBookList]);

    const getBooksFromRest = async () => {
        setBookList([]);
        await fetch(`${config.url}/api/v1/book/list`)
            .then((res) => res.json())
            .then((books) => setBookList(books));
    };

    const createTableData = (bookListData) => {
        if (Array.isArray(bookListData) && bookListData.length) {
            return bookListData.map((e, i) => {
                return {
                    key: i,
                    id: e.id,
                    name: e.name,
                    publicationDate: e.publicationDate,
                    authorsDisplay: e.authors.map(el => el.firstName + ' ' + el.lastName + ', '),
                    authors: e.authors,
                    genresDisplay: e.genres.map(el => el.name + ', '),
                    genres: e.genres,
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
            title: 'Название книги',
            dataIndex: 'name',
            editable: true,
            key: 2
        },
        {
            width: '10%',
            title: 'Дата публикации',
            dataIndex: 'publicationDate',
            editable: true,
            key: 3
        },
        {
            width: '20%',
            title: 'Авторы',
            dataIndex: 'authorsDisplay',
            editable: false,
            ellipsis: true,
            key: 4
        },
        {
            width: '20%',
            title: 'Жанры',
            dataIndex: 'genresDisplay',
            editable: false,
            ellipsis: true,
            key: 5
        }
    ];

    const handleDelete = async (id) => {
        fetch(`${config.url}/api/v1/book/${id}`, {method: 'DELETE'})
            .then(() => getBooksFromRest())
    };

    const handleAdd = () => setShowBookEditModal(true);

    const handleSave = async (record) => {
        fetch(`${config.url}/api/v1/book`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(record)
        })
            .then(() => getBooksFromRest());
    }

    let dataSource = createTableData(bookList);

    if (Array.isArray(bookList) && bookList.length) {
        return <>
            <EditableTable
                dataSource={dataSource}
                columns={columns}
                handleAdd={handleAdd}
                handleSave={handleSave}
                handleDelete={handleDelete}
            />

            <BookModal
                setShowBookModal={setShowBookEditModal}
                showBookModal={showBookEditModal}
                handleSave={handleSave}
            />
        </>
    } else {
        return <Empty description="Книги не найдены"/>
    }
}

export default Books;