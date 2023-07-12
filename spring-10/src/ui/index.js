import React from 'react'
import ReactDOM from 'react-dom/client'
import StartPage from "./components/layout/StartPage";
import ErrorPage from "./components/layout/ErrorPage";
import Books from "./components/books/Books";
import {createBrowserRouter, RouterProvider,} from "react-router-dom";
import Authors from "./components/authors/Authors";
import Genres from "./components/genres/Genres";

const router = createBrowserRouter([
    {
        path: "/",
        element: <StartPage />,
        errorElement: <ErrorPage />,
        children: [
            {
                path: "books",
                element: <Books />,
            },
            {
                path: "authors",
                element: <Authors />,
            },
            {
                path: "genres",
                element: <Genres />,
            },
        ],
    },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>
);