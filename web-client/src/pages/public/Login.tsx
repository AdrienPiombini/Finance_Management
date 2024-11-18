import { useState } from "react";

import { useNavigate } from "react-router-dom";
import { authenticationService } from "../../services/authenticationServices";

export default function Login() {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });

  const onChange = (event: any) => {
    setCredentials({
      ...credentials,
      [event.target.name]: event.target.value,
    });
  };

  const [error, setError] = useState("");

  const onSubmit = async (event: any) => {
    event.preventDefault();

    const res = await authenticationService.login(credentials);
    if (res == false) {
      setError("An error occured, your connexion failed");
      return;
    }

    navigate("/secured");
  };

  return (
    <div>
      <form onSubmit={onSubmit}>
        <h3>LOGIN</h3>
        <div className="group">
          <label htmlFor="login">Username</label>
          <input
            type="text"
            name="username"
            placeholder="username"
            id=""
            value={credentials.username}
            onChange={onChange}
          />
        </div>

        <div className="group">
          <label htmlFor="login">Password</label>
          <input
            type="password"
            name="password"
            placeholder="password"
            id=""
            value={credentials.password}
            onChange={onChange}
          />
        </div>

        <div className="group">
          {error && <p style={{ color: "red" }}>{error}</p>}
          <button>Connexion</button>
        </div>
      </form>
    </div>
  );
}
