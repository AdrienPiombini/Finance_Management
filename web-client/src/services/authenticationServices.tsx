import { jwtDecode, JwtPayload } from "jwt-decode";
import Axios from "./apiCallerService";

const AUTHENTICATION_URI = "/authentication";

const login = async (credentials: any) => {
  try {
    const res = await Axios.post(AUTHENTICATION_URI + "/login", credentials);
    console.log(res.data.token + "toto");
    const token = res.data.token;
    saveToken(token);
    return true;
  } catch (error: any) {
    console.log("error", error);
    return false;
  }
};

const register = async (credentials: any): Promise<boolean> => {
  return Axios.post(AUTHENTICATION_URI + "/register", credentials)
    .then((res) => {
      const token = res.data.token;
      saveToken(token);
      return true;
    })
    .catch(() => false);
};

const saveToken = (token: any) => {
  localStorage.setItem("token", token);
};

const logout = () => {
  localStorage.removeItem("token");
};

const isTokenValid = (token: string): boolean => {
  try {
    const decoded: JwtPayload = jwtDecode(token);
    const currentTime = Date.now() / 1000;
    if (!decoded.exp) {
      return false;
    }
    return decoded.exp > currentTime;
  } catch (error) {
    console.error("Token decoding error:", error);
    return false;
  }
};

const isLogged = () => {
  const token = localStorage.getItem("token");
  if (!token) {
    return false;
  }
  return isTokenValid(token);
};

const getToken = () => {
  return localStorage.getItem("token");
};
export const authenticationService = {
  saveToken,
  logout,
  isLogged,
  login,
  getToken,
  register,
};
