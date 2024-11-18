import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./styles/index.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import UnAuthGuard from "./utils/UnAuthenticatedGuard";
import AuthGuard from "./utils/AuthenticatedGuard";

import ErrorPage from "./utils/ErrorPage";
import Home from "./pages/public/Home";
import Login from "./pages/public/Login";
import Register from "./pages/public/Register";
import SecuredEndpoint from "./pages/private/Secured";

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
    path: "/secured",
    element: (
      <AuthGuard>
        <SecuredEndpoint />
      </AuthGuard>
    ),
  },
]);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
