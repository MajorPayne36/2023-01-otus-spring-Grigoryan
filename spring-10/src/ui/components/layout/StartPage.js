import React from 'react';
import {Breadcrumb, Layout, Menu, theme} from 'antd';
import {BookOutlined, UserOutlined, ContainerOutlined} from '@ant-design/icons';
import {Link, Outlet, Route, Routes} from 'react-router-dom';
import Books from "../books/Books";

const {Header, Content, Footer} = Layout;

const items = [
    {
        label: <Link to='books'>Книги</Link>,
        key: 'books',
        icon: <BookOutlined/>
    },
    {
        label: <Link to='authors'>Авторы</Link>,
        key: 'authors',
        icon: <UserOutlined/>
    },
    {
        label: <Link to='genres'>Жанры</Link>,
        key: 'genres',
        icon: <ContainerOutlined/>
    }
]

const StartPage = () => {
    const {
        token: {colorBgContainer},
    } = theme.useToken();

    return (
        <Layout className="layout">
            <Header>
                <div className="logo"/>
                <Menu
                    theme="dark"
                    mode="horizontal"
                    defaultSelectedKeys={['1']}
                    items={items}
                />
            </Header>

            <Content style={{padding: '0 50px'}}>
                <div
                    className="site-layout-content"
                    style={{background: colorBgContainer}}>
                    {/* Outlet нужен для того, чтоб route смог рендерить страницы*/}
                    <Outlet />
                </div>
            </Content>

            <Footer style={{textAlign: 'center',}}>
                Библиотека А. В. Григоряна
            </Footer>

        </Layout>
    );
};
export default StartPage;