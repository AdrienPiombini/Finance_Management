import axios from "axios";
import { authenticationService } from "./authenticationServices";

const Axios = axios.create({
  baseURL: "http://localhost:8080",
});

Axios.interceptors.request.use((request) => {
  if (authenticationService.isLogged()) {
    request.headers.Authorization =
      "Bearer " + authenticationService.getToken();
  }
  console.log(request);

  return request;
});

Axios.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    console.log(error);
    if (error.response.status === 401) {
      authenticationService.logout();
    }
  }
);

export default Axios;
