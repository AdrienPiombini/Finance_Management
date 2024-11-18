import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./styles/index.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import UnAuthGuard from "./utils/UnAuthenticatedGuard";
import Home from "./pages/Home";
import AuthGuard from "./utils/AuthenticatedGuard";
import Login from "./pages/Login";
import Register from "./pages/Register";
import ErrorPage from "./utils/ErrorPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: (
      <UnAuthGuard>
        <Home />
      </UnAuthGuard>
    ),
    children: [
      {
        path: "/login",
        element: <Login />,
      },
      {
        path: "/register",
        element: <Register />,
      },
    ],
    errorElement: <ErrorPage />,
  },
  {
    path: "/",
    element: (
      <AuthGuard>
        <Home />
      </AuthGuard>
    ),
  },
]);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
