import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../components/auth/AuthContext"; // Importar el hook de autenticación
import { useForm } from "react-hook-form"; // Importar useForm

const RegisterPage = () => {
  const API_URL = "https://exploramas.onrender.com";
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState(null); // Estado para manejar errores
  const { login } = useAuth(); // Usamos el contexto para hacer el login
  const navigate = useNavigate();

  //Configurando el useForm
  const {
    register,
    handleSubmit,
    formState: { errors },
    watch,
  } = useForm();

  const onSubmit = async (data) => {
    // Verificar si el usuario ya existe
    const existingUser = localStorage.getItem(data.email);
    if (existingUser) {
      alert("Este correo electrónico ya está registrado.");
      return;
    }

    try {
      const response = await fetch(`${API_URL}/auth/register`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (response.status === 201) {
        login(data);
        alert("Registro exitoso");
        navigate("/");
      }

      // Redirigir al home o la página principal
    } catch (error) {
      console.error(error);
      setError("Ocurrió un error al conectarse al servidor.");
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-r from-[#2c3e50] to-[#34495e] flex items-center justify-center">
      <div className=" w-full min-w-150 max-w-md bg-white p-8 rounded-lg shadow-xl relative z-10">
        <h2 className="text-3xl sm:text-4xl font-semibold text-center text-gray-800 mb-6">
          Crear una cuenta
        </h2>
        {error && ( // Mostrar el mensaje de error si existe
          <div className="mb-4 p-4 bg-red-100 border border-red-400 text-red-700 rounded-lg">
            {error}
          </div>
        )}
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="flex gap-3 ">
            <div className="w-100 mb-4">
              <label
                className="block text-sm font-semibold text-gray-700 mb-2"
                htmlFor="firstName"
              >
                Nombre
              </label>
              <input
                type="text"
                id="firstName"
                {...register("firstName", {
                  required: "El nombre es obligatorio",
                })}
                className="w-full px-6 py-3 border border-gray-300 rounded-lg text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-[#2980b9] focus:ring-opacity-50 transition-all duration-300 ease-in-out"
                placeholder="Ingresa tu nombre"
              />
              {errors.firstName && (
                <p className="text-red-500 text-sm mt-1">
                  {errors.firstName.message}
                </p>
              )}
            </div>
            <div className="w-100 mb-4">
              <label
                className="block text-sm font-semibold text-gray-700 mb-2"
                htmlFor="lastName"
              >
                Apellido
              </label>
              <input
                type="text"
                id="lastName"
                {...register("lastName", {
                  required: "El apellido es obligatorio",
                })}
                className="w-full px-6 py-3 border border-gray-300 rounded-lg text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-[#2980b9] focus:ring-opacity-50 transition-all duration-300 ease-in-out"
                placeholder="Ingresa tu apellido"
              />
              {errors.lastName && (
                <p className="text-red-500 text-sm mt-1">
                  {errors.lastName.message}
                </p>
              )}
            </div>
          </div>
          <div className="flex gap-3">
            <div className="w-100 mb-4">
              <label
                className="block text-sm font-semibold text-gray-700 mb-2"
                htmlFor="identityCard"
              >
                DNI
              </label>
              <input
                type="number"
                id="identityCard"
                {...register("identityCard", {
                  required: "El DNI es obligatorio",
                  minLength: {
                    value: 8,
                    message: "El DNI debe tener al menos 8 dígitos",
                  },
                  maxLength: {
                    value: 8,
                    message: "El DNI no puede tener más de 8 dígitos",
                  },
                })}
                className="w-full px-6 py-3 border border-gray-300 rounded-lg text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-[#2980b9] focus:ring-opacity-50 transition-all duration-300 ease-in-out"
                placeholder="Ingresa tu DNI"
              />
              {errors.identityCard && (
                <p className="text-red-500 text-sm mt-1">
                  {errors.identityCard.message}
                </p>
              )}
            </div>
            <div className="w-100 mb-4">
              <label
                className="block text-sm font-semibold text-gray-700 mb-2"
                htmlFor="email"
              >
                Correo electrónico
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
          </div>
          <div className="flex gap-3">
            <div className="w-100 mb-4">
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
            <div className="w-100 mb-6">
              <label
                className="block text-sm font-semibold text-gray-700 mb-2"
                htmlFor="confirmPassword"
              >
                Confirmar contraseña
              </label>
              <input
                type="password"
                id="confirmPassword"
                {...register("confirmPassword", {
                  required: "Confirma tu contraseña",
                  validate: (value) =>
                    value === watch("password") ||
                    "Las contraseñas no coinciden",
                })}
                className="w-full px-6 py-3 border border-gray-300 rounded-lg text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-[#2980b9] focus:ring-opacity-50 transition-all duration-300 ease-in-out"
                placeholder="Confirma tu contraseña"
              />
              {errors.confirmPassword && (
                <p className="text-red-500 text-sm mt-1">
                  {errors.confirmPassword.message}
                </p>
              )}
            </div>
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
