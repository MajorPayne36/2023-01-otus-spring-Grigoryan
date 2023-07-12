import React, {useEffect, useState} from 'react'
import {Empty} from "antd";
import {config} from "../Config";
import GenreModal from "./GenreModal";
import EditableTable from "../table/EditableTable";

function Genres() {

    const [genreList, setGenreList] = useState([]);
    const [showGenreEditModal, setShowGenreEditModal] = useState(false);

    useEffect(() => {
        setGenreList([]);
        fetch(`${config.url}/api/v1/genre/list`)
            .then((res) => res.json())
            .then((genres) => setGenreList(genres));

    }, [setGenreList]);

    const getGenresFromRest = async () => {
        setGenreList([]);
        await fetch(`${config.url}/api/v1/genre/list`)
            .then((res) => res.json())
            .then((genres) => setGenreList(genres));
    };

    const createTableData = (genreListData) => {
        if (Array.isArray(genreListData) && genreListData.length) {
            return genreListData.map((e, i) => {
                return {
                    key: i,
                    id: e.id,
                    name: e.name,
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
            title: 'Наименование жанра',
            dataIndex: 'name',
            editable: true,
            key: 2
        },
        {
            width: '20%',
            title: 'Книги в этом жанре',
            dataIndex: 'booksDisplay',
            editable: false,
            ellipsis: true,
            key: 3
        }
    ];

    const handleDelete = async (id) => {
        fetch(`${config.url}/api/v1/genre/${id}`, {method: 'DELETE'})
            .then(() => getGenresFromRest())
    };

    const handleAdd = () => setShowGenreEditModal(true);

    const handleSave = async (record) => {
        fetch(`${config.url}/api/v1/genre`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(record)
        })
            .then(() => getGenresFromRest());
    }

    let dataSource = createTableData(genreList);

    if (Array.isArray(genreList) && genreList.length) {
        return <>
            <EditableTable
                dataSource={dataSource}
                columns={columns}
                handleAdd={handleAdd}
                handleSave={handleSave}
                handleDelete={handleDelete}
            />

            <GenreModal
                setShowGenreEditModal={setShowGenreEditModal}
                showGenreEditModal={showGenreEditModal}
                handleSave={handleSave}
            />
        </>
    } else {
        return <Empty description="Авторы не найдены"/>
    }
}

export default Genres;