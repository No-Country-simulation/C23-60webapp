// src/components/AuthContext.js
import react, { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export const useAuth = () => {
  return useContext(AuthContext);
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  // Al iniciar la aplicación, intenta obtener el usuario del localStorage
  useEffect(() => {
    try {
    } catch {}

    const storedUser = JSON.parse(localStorage.getItem("user"));
    if (storedUser) {
      setUser(storedUser); // Si el usuario está guardado, lo carga al estado
    }
  }, []);

  const login = (token) => {
    localStorage.setItem("token", token);
    setUser(token); // Establecer el usuario en el estado
  };

  const logout = () => {
    setUser(null); // Limpiar el estado del usuario en el contexto
    localStorage.removeItem("token"); // Eliminar el usuario del localStorage
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
