import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../components/auth/AuthContext";
import { useForm } from "react-hook-form";

const LoginPage = () => {
  const API_URL = "https://exploramas.onrender.com"; // URL de la API
  const [error, setError] = useState(null); // Estado para manejar errores
  const { login } = useAuth(); // Context para el login
  const navigate = useNavigate();

  // Configurando el useForm
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

      if (response.status === 500) {
        setError(
          "Error interno del servidor. Por favor, intenta nuevamente más tarde.",
        );
        return;
      }

      const json = await response.json();

      if (!response.ok) {
        setError(json.message || "Error en la solicitud"); // Manejar errores de la API
        return;
      }

      // Si el login es exitoso, guardar el usuario en el contexto y redirigir
      login(json.user); // Suponiendo que la API devuelve el objeto del usuario
      alert("Inicio de sesión exitoso");
      navigate("/"); // Redirigir al home
    } catch (error) {
      console.error(error);
      setError("Ocurrió un error al conectarse al servidor.");
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-r from-[#2c3e50] to-[#34495e] flex items-center justify-center">
      <div className="w-full max-w-md bg-white p-8 rounded-lg shadow-xl relative z-10">
        <h2 className="text-3xl sm:text-4xl font-semibold text-center text-gray-800 mb-6">
          Iniciar sesión
        </h2>
        {error && (
          <div className="mb-4 p-4 bg-red-100 border border-red-400 text-red-700 rounded-lg">
            {error}
          </div>
        )}
        <form onSubmit={handleSubmit(onSubmit)}>
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
              {...register("email", {
                required: "El correo electrónico es obligatorio",
                pattern: {
                  value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                  message: "Correo electrónico no válido",
                },
              })}
              className="w-full px-6 py-3 border border-gray-300 rounded-lg text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-[#2980b9] focus:ring-opacity-50 transition-all duration-300 ease-in-out"
              placeholder="Ingresa tu correo electrónico"
            />
            {errors.email && (
              <p className="text-red-500 text-sm mt-1">
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
              {...register("password", {
                required: "La contraseña es obligatoria",
                minLength: {
                  value: 6,
                  message: "La contraseña debe tener al menos 6 caracteres",
                },
              })}
              className="w-full px-6 py-3 border border-gray-300 rounded-lg text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-[#2980b9] focus:ring-opacity-50 transition-all duration-300 ease-in-out"
              placeholder="Ingresa tu contraseña"
            />
            {errors.password && (
              <p className="text-red-500 text-sm mt-1">
                {errors.password.message}
              </p>
            )}
          </div>

          <button
            type="submit"
            className="w-full py-3 bg-[#2980b9] text-white rounded-lg hover:bg-[#2980b9]/80 focus:outline-none transition-all duration-300 ease-in-out"
          >
            Iniciar sesión
          </button>
        </form>

        <p className="mt-4 text-center text-sm text-gray-600">
          ¿No tienes una cuenta?{" "}
          <a href="/register" className="text-[#3498db] hover:underline">
            Regístrate aquí
          </a>
        </p>
      </div>
    </div>
  );
};

export default LoginPage;
