import { useState } from "react";
import { useAuth } from "../components/auth/AuthContext";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";

const LoginAdmin = ({ setIsAuthenticated }) => {
  const { login } = useAuth();
  const API_URL = "https://exploramas.onrender.com";
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = async (data) => {
    try {
      const response = await fetch(`${API_URL}/auth/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (response.ok) {
        const result = await response.json();
        //login(result.token); 
        setIsAuthenticated(true);
        navigate("/admin");
      } else {
        alert("Usuario o contraseña incorrectos");
      }
    } catch (error) {
      console.error("Error durante el login:", error);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-r from-[#2c3e50] to-[#34495e] flex items-center justify-center">
      <div className="w-full max-w-md bg-white p-8 rounded-lg shadow-lg">
        <h2 className="text-2xl font-semibold text-center text-gray-800 mb-6">
          Iniciar sesión como Admin
        </h2>
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="mb-4">
            <label
              className="block text-sm font-semibold text-gray-700 mb-2"
              htmlFor="email"
            >
              Usuario
            </label>
            <input
              type="email"
              id="email"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Ingresa tu usuario"
              {...register("email", { required: "Este campo es requerido" })}
            />
            {errors.email && (
              <p className="text-red-500 text-xs mt-1">
                {errors.email.message}
              </p>
            )}
          </div>

          <div className="mb-6">
            <label
              className="block text-sm font-semibold text-gray-700 mb-2"
              htmlFor="password"
            >
              Contraseña
            </label>
            <input
              type="password"
              id="password"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Ingresa tu contraseña"
              {...register("password", { required: "Este campo es requerido" })}
            />
            {errors.password && (
              <p className="text-red-500 text-xs mt-1">
                {errors.password.message}
              </p>
            )}
          </div>

          <button
            type="submit"
            className="w-full py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-all duration-300 ease-in-out"
          >
            Ingresar
          </button>
        </form>
      </div>
    </div>
  );
};

export default LoginAdmin;
