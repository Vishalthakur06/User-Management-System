import React, { useState } from "react";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Hero from "./components/Hero";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Profile from "./pages/Profile";

const App = () => {
  const [token, setToken] = useState(localStorage.getItem("token") || "");
  const [currentPage, setCurrentPage] = useState("home");

  const handleSetToken = (t) => {
    localStorage.setItem("token", t);
    setToken(t);
    if (t) setCurrentPage("profile");
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    setToken("");
    setCurrentPage("home");
  };

  const renderPage = () => {
    if (token) return <Profile token={token} />;
    switch (currentPage) {
      case "register":
        return <Register setCurrentPage={setCurrentPage} />;
      case "login":
        return (
          <Login setToken={handleSetToken} setCurrentPage={setCurrentPage} />
        );
      default:
        return <Hero />;
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 dark:bg-gray-900 text-gray-900 dark:text-gray-100">
      <Header
        token={token}
        setCurrentPage={setCurrentPage}
        onLogout={handleLogout}
      />
      {renderPage()}
      <Footer />
    </div>
  );
};

export default App;
