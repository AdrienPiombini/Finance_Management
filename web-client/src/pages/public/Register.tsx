import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { authenticationService } from "../services/authenticationServices";

export default function Register() {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({
    user_credential: "",
    user_password: "",
    confirm_password: "",
  });
  const [error, setError] = useState("");

  const onChange = (event: any) => {
    setCredentials({
      ...credentials,
      [event.target.name]: event.target.value,
    });
  };

  const onSubmit = async (event: any) => {
    event.preventDefault();
    const { user_password, confirm_password } = credentials;

    if (user_password !== confirm_password) {
      setError("Passwords do not match");
      return;
    }

    const res = await authenticationService.register(credentials);

    if (res == false) {
      setError("An error occured, your registration failed");
      return;
    }

    navigate("/dashboard");
  };

  return (
    <div>
      <form onSubmit={onSubmit}>
        <h3>REGISTER</h3>
        <div className="group">
          <label htmlFor="register">Username</label>
          <input
            type="text"
            name="user_credential"
            placeholder="user_credentials"
            id=""
            value={credentials.user_credential}
            onChange={onChange}
          />
        </div>

        <div className="group">
          <label htmlFor="register">Password</label>
          <input
            type="password"
            name="user_password"
            placeholder="user_password"
            id=""
            value={credentials.user_password}
            onChange={onChange}
          />
        </div>

        <div className="group">
          <label htmlFor="register">Confirm Password</label>
          <input
            type="password"
            name="confirm_password"
            placeholder="confirm password"
            id=""
            value={credentials.confirm_password}
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
