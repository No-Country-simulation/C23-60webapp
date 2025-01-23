// src/components/Navbar.js
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext'; // Importar el hook

const Navbar = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const { user, logout } = useAuth(); // Obtener el usuario y logout desde el AuthContext
  const navigate = useNavigate();

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const handleLogout = () => {
    logout(); // Llamar al método logout del AuthContext
    navigate('/login'); // Redirigir a la página de login
  };

  return (
    <nav className="bg-white shadow-md">
      <div className="container mx-auto px-4 py-4 flex justify-between items-center">
        <Link to="/" className="text-2xl font-heading font-bold text-primary">
          Explora+Más
        </Link>

        <button
          onClick={toggleMenu}
          className="md:hidden text-gray-600 focus:outline-none"
        >
          {isMenuOpen ? (
            <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          ) : (
            <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16m-7 6h7" />
            </svg>
          )}
        </button>

        <div className="hidden md:flex gap-6 items-center">
          <Link to="/destinations" className="text-gray-600 hover:text-gray-800 transition-colors duration-300">Destinos</Link>
          <Link to="/packages" className="text-gray-600 hover:text-gray-800 transition-colors duration-300">Paquetes</Link>
          <Link to="/mis-viajes" className="text-gray-600 hover:text-gray-800 font-semibold transition-colors duration-300">Mis Viajes</Link>

          {user ? (
            <>
              <span className="text-gray-600">Hola, {user.name || user.email}</span>
              <button onClick={handleLogout} className="text-gray-600 hover:text-gray-800 transition-colors duration-300">Cerrar sesión</button>
            </>
          ) : (
            <>
              <Link to="/login" className="text-gray-600 hover:text-gray-800 transition-colors duration-300">Loguearse</Link>
              <Link to="/register" className="py-2 px-4 bg-[rgb(26,54,93)] text-white rounded-md hover:bg-[rgb(26,54,93,0.8)]">Registrarse</Link>
            </>
          )}
        </div>
      </div>

      {isMenuOpen && (
        <div className="md:hidden bg-white shadow-lg rounded-lg p-4 mt-2 transform transition-all duration-300 ease-in-out">
          <div className="flex flex-col gap-4">
            <Link to="/destinations" className="text-gray-600 hover:bg-gray-100 hover:text-gray-900 rounded-lg px-4 py-2 transition-colors duration-200">Destinos</Link>
            <Link to="/packages" className="text-gray-600 hover:bg-gray-100 hover:text-gray-900 rounded-lg px-4 py-2 transition-colors duration-200">Paquetes</Link>
            <Link to="/mis-viajes" className="text-gray-600 hover:bg-gray-100 hover:text-gray-900 rounded-lg px-4 py-2 transition-colors duration-200">Mis Viajes</Link>

            {user ? (
              <>
                <span className="text-gray-600">Hola, {user.name || user.email}</span>
                <button onClick={handleLogout} className="w-full text-gray-600 hover:bg-gray-100 hover:text-gray-900 rounded-lg px-4 py-2 transition-colors duration-200">Cerrar sesión</button>
              </>
            ) : (
              <>
                <Link to="/login" className="w-full text-gray-600 hover:bg-gray-100 hover:text-gray-900 rounded-lg px-4 py-2 transition-colors duration-200">Loguearse</Link>
                <Link to="/register" className="w-full py-2 px-4 bg-[rgb(26,54,93)] text-white rounded-md hover:bg-[rgb(26,54,93,0.8)]">Registrarse</Link>
              </>
            )}
          </div>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
