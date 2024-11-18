import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { authenticationService } from "../../services/authenticationServices";

export default function Register() {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
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

    const res = await authenticationService.register(credentials);

    if (res == false) {
      setError("An error occured, your registration failed");
      return;
    }

    navigate("/secured");
  };

  return (
    <div>
      <form onSubmit={onSubmit}>
        <h3>REGISTER</h3>
        <div className="group">
          <label htmlFor="register">Username</label>
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
          <label htmlFor="register">Password</label>
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
