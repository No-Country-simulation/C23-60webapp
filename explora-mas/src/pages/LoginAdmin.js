import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const LoginAdmin = ({ setIsAuthenticated }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    // Validación de usuario y contraseña (puedes personalizarlo más)
    if (username === 'admin' && password === 'administrador') {
      setIsAuthenticated(true); // Actualizamos el estado de autenticación
      navigate('/admin'); // Redirigir al panel de admin
    } else {
      alert('Usuario o contraseña incorrectos');
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-r from-[#2c3e50] to-[#34495e] flex items-center justify-center">
      <div className="w-full max-w-md bg-white p-8 rounded-lg shadow-lg">
        <h2 className="text-2xl font-semibold text-center text-gray-800 mb-6">Iniciar sesión como Admin</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-sm font-semibold text-gray-700 mb-2" htmlFor="username">
              Usuario
            </label>
            <input
              type="text"
              id="username"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Ingresa tu usuario"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>

          <div className="mb-6">
            <label className="block text-sm font-semibold text-gray-700 mb-2" htmlFor="password">
              Contraseña
            </label>
            <input
              type="password"
              id="password"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Ingresa tu contraseña"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          <button type="submit" className="w-full py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-all duration-300 ease-in-out">
            Ingresar
          </button>
        </form>
      </div>
    </div>
  );
};

export default LoginAdmin;
