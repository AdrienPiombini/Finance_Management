import { Link, Outlet } from "react-router-dom";

export default function Home() {
  return (
    <div className="container">
      <h1>WELCOME ON THE BEST FINANCE APP</h1>
      <div className="centered-buttons">
        <Link to="/login">
          <button>LOGIN</button>
        </Link>
        <Link to="/register">
          <button>REGISTER</button>
        </Link>
      </div>
      <Outlet />
    </div>
  );
}
