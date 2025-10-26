import { BrowserRouter as Router, Routes, Route } from "react-router";

import "./App.css";
import { AppRestricted } from "./AppRestricted";
import Login from "./pages/Login";

function App() {
  return <AppRestricted />;

  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<AppRestricted />} />
      </Routes>
    </Router>
  );
}

export default App;
