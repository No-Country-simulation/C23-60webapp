// src/pages/RegisterPage.js
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../components/auth/AuthContext"; // Importar el hook de autenticación

const RegisterPage = () => {
  const [confirmPassword, setConfirmPassword] = useState("");
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    identityCard: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const { login } = useAuth(); // Usamos el contexto para hacer el login
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Verificar si las contraseñas coinciden
    if (formData.password !== formData.confirmPassword) {
      alert("¡Las contraseñas no coinciden!");
      return;
    }

    // Verificar si el usuario ya existe
    const existingUser = localStorage.getItem(formData.email);
    if (existingUser) {
      alert("Este correo electrónico ya está registrado.");
      return;
    }

    // Crear el objeto de usuario
    const user = {
      firstName: formData.firstName,
      lastName: formData.lastName,
      identityCard: formData.identityCard,
      email: formData.email,
      password: formData.password,
    };

    // Guardar el usuario en la API con el email como clave
    try {
      const response = await fetch("api/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      const json = await response.json();
      console.log(json);
      login(user);
      alert("Registro exitoso");
      navigate("/"); // Redirigir al home o la página principal
      if (!response.ok) {
        console.log("Error en la solicitud");
      }
      setFormData("");
    } catch (error) {
      console.error(error);
    }

    // Loguear al usuario inmediatamente

    // Limpiar los campos del formulario
    setFormData({
      firstName: "",
      lastName: "",
      email: "",
      identityCard: "",
      password: "",
      confirmPassword: "",
    });
  };

  return (
    <div className="min-h-screen bg-gradient-to-r from-[#2c3e50] to-[#34495e] flex items-center justify-center">
      <div className="w-full max-w-md bg-white p-8 rounded-lg shadow-xl relative z-10">
        <h2 className="text-3xl sm:text-4xl font-semibold text-center text-gray-800 mb-6">
          Crear una cuenta
        </h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label
              className="block text-sm font-semibold text-gray-700 mb-2"
              htmlFor="name"
            >
              Nombre
            </label>
            <input
              type="text"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              className="w-full px-6 py-3 border border-gray-300 rounded-lg text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-[#2980b9] focus:ring-opacity-50 transition-all duration-300 ease-in-out"
              placeholder="Ingresa tu nombre"
            />
          </div>
          <div className="mb-4">
            <label
              className="block text-sm font-semibold text-gray-700 mb-2"
              htmlFor="lastName"
            >
              Apellido
            </label>
            <input
              type="text"
              id="lastName"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              className="w-full px-6 py-3 border border-gray-300 rounded-lg text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-[#2980b9] focus:ring-opacity-50 transition-all duration-300 ease-in-out"
              placeholder="Ingresa tu nombre"
            />
          </div>
          <div className="mb-4">
            <label
              className="block text-sm font-semibold text-gray-700 mb-2"
              htmlFor="lastName"
            >
              Dni
            </label>
            <input
              type="text"
              id="identityCard"
              name="identityCard"
              value={formData.identityCard}
              onChange={handleChange}
              className="w-full px-6 py-3 border border-gray-300 rounded-lg text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-[#2980b9] focus:ring-opacity-50 transition-all duration-300 ease-in-out"
              placeholder="Ingresa Dni"
            />
          </div>
          <div className="mb-4">
            <label
              className="block text-sm font-semibold text-gray-700 mb-2"
              htmlFor="email"
            >
              Dirección de correo electrónico
            </label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              className="w-full px-6 py-3 border border-gray-300 rounded-lg text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-[#2980b9] focus:ring-opacity-50 transition-all duration-300 ease-in-out"
              placeholder="Ingresa tu correo electrónico"
            />
          </div>

          <div className="mb-4">
            <label
              className="block text-sm font-semibold text-gray-700 mb-2"
              htmlFor="password"
            >
              Contraseña
            </label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              className="w-full px-6 py-3 border border-gray-300 rounded-lg text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-[#2980b9] focus:ring-opacity-50 transition-all duration-300 ease-in-out"
              placeholder="Ingresa tu contraseña"
            />
          </div>

          <div className="mb-6">
            <label
              className="block text-sm font-semibold text-gray-700 mb-2"
              htmlFor="confirmPassword"
            >
              Confirmar contraseña
            </label>
            <input
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              className="w-full px-6 py-3 border border-gray-300 rounded-lg text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-[#2980b9] focus:ring-opacity-50 transition-all duration-300 ease-in-out"
              placeholder="Confirma tu contraseña"
            />
          </div>

          <button
            type="submit"
            className="w-full py-3 bg-[#2980b9] text-white rounded-lg hover:bg-[#2980b9]/80 focus:outline-none transition-all duration-300 ease-in-out"
          >
            Registrarse
          </button>
        </form>

        <p className="mt-4 text-center text-sm text-gray-600">
          ¿Ya tienes una cuenta?{" "}
          <a href="/login" className="text-[#3498db] hover:underline">
            Inicia sesión aquí
          </a>
        </p>
      </div>
    </div>
  );
};

export default RegisterPage;
