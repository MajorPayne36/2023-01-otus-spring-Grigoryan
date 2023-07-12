import {Button, Form, Popconfirm, Table, Typography} from 'antd';
import React, {useState} from 'react';
import EditableCell from "./EditableCell";

function EditableTable(props) {
    const [form] = Form.useForm();
    const {dataSource, columns, handleAdd, handleSave,  handleDelete} = props;

    const [editingKey, setEditingKey] = useState('');

    const isEditing = (record) => record.key === editingKey;

    const edit = (record) => {
        form.setFieldsValue({
            ...record,
        });
        setEditingKey(record.key);
    };

    const save = async () => {
        try {
            await form.validateFields();
            const row = form.getFieldsValue(true);
            setEditingKey('');
            handleSave(row)

        } catch (errInfo) {
            console.log('Validate Failed:', errInfo);
        }
    };

    const cancel = () => {
        setEditingKey('');
    };

    const actions = [
        {
            width: '10%',
            title: '',
            dataIndex: 'edit',
            key: 6,
            render: (_, record) => {
                const editable = isEditing(record);
                return editable ? (
                    <span>
                        <Typography.Link
                            onClick={save}
                            style={{marginRight: 8,}}
                        >
                            Сохранить
                        </Typography.Link>
                        <Popconfirm title="Отменить?" onConfirm={cancel}>
                          <a>Отмена</a>
                        </Popconfirm>
                    </span>
                ) : (
                    <Typography.Link disabled={editingKey !== ''} onClick={() => edit(record)}>
                        Изменить
                    </Typography.Link>
                );
            }
        },
        {
            width: '10%',
            title: '',
            dataIndex: 'delete',
            key: 7,
            render: (_, record) => dataSource.length >= 1 ? (
                <Popconfirm title="Вы точно хотите удалить?" onConfirm={() => handleDelete(record.id)}>
                    <Typography.Link>
                        Удалить
                    </Typography.Link>
                </Popconfirm>
            ) : null,
        }
    ];

    const mergedColumns = columns.map((col) => {
        if (!col.editable) {
            return col;
        }
        return {
            ...col,
            onCell: (record) => ({
                record,
                inputType: col.dataIndex === 'publicationDate' ? 'date' : 'text',
                dataIndex: col.dataIndex,
                title: col.title,
                editing: isEditing(record),
            }),
        };
    });
    mergedColumns.push(actions[0], actions[1]);

    return (
        <Form form={form} component={false}>
            <Button
                onClick={handleAdd}
                type="primary"
                style={{
                    marginBottom: 16,
                }}
            >
                Добавить запись
            </Button>
            <Table
                components={{body: {cell: EditableCell}}}
                rowClassName={() => 'editable-row'}
                bordered
                dataSource={dataSource}
                columns={mergedColumns}
            />
        </Form>
    );
};

export default EditableTable;