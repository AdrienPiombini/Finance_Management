import { Navigate } from "react-router-dom";
import { authenticationService } from "../services/authenticationServices";

export default function UnAuthGuard({ children }) {
  if (authenticationService.isLogged()) {
    return <Navigate to="/secured"></Navigate>;
  }
  return children;
}
