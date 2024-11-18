import { Navigate } from "react-router-dom";
import { authenticationService } from "../services/authenticationServices";

export default function AuthGuard({ children }) {
  if (!authenticationService.isLogged()) {
    return <Navigate to="/"></Navigate>;
  }
  return children;
}
