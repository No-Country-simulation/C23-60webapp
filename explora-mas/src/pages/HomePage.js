import React, { useState, useEffect } from "react";
import { MagnifyingGlassIcon } from "@heroicons/react/20/solid"; // Asegúrate de que la ruta sea correcta
import FeaturedDestinations from "../components/FeaturedDestinations"; // Asegúrate de que la ruta sea correcta
import { Footer } from "../components/Footer"; // Asegúrate de que la ruta sea correcta

const HomePage = () => {
  const [currentSection, setCurrentSection] = useState(0); // Para controlar el índice del carrusel
  const [fade, setFade] = useState(true); // Para controlar el fade in y fade out

  useEffect(() => {
    const interval = setInterval(() => {
      setFade(false); // Iniciar el desvanecimiento antes de cambiar la sección

      setTimeout(() => {
        setCurrentSection((prev) => (prev === 0 ? 1 : 0)); // Cambiar de sección después del desvanecimiento
        setFade(true); // Volver a activar el fade después de cambiar la sección
      }, 500); // Tiempo para esperar a que termine el desvanecimiento

    }, 8000); // Cambio de sección cada 8 segundos

    // Limpiar el intervalo cuando el componente se desmonte
    return () => clearInterval(interval);
  }, []);

  return (
    <div>
      {/* Sección de presentación */}
      <div className="relative h-[600px] sm:h-[500px] lg:h-[600px] flex items-center justify-center bg-cover bg-center" style={{
        backgroundImage: "url('https://images.unsplash.com/photo-1469474968028-56623f02e42e')"
      }}>
        <div className="absolute inset-0 bg-black opacity-50" />
        <div className="relative z-10 text-center text-white px-4 md:px-8">
          <h1 className="text-4xl sm:text-5xl font-bold mb-6 animate-fadeIn">Descubre tu próxima aventura</h1>
          <p className="text-lg sm:text-xl mb-8 max-w-3xl mx-auto">
            Explora los destinos más bellos del mundo con nuestros paquetes de viaje seleccionados
          </p>
          {/* Componente de búsqueda estilizado */}
          <div className="flex justify-center space-x-4 items-center mx-auto">
            <input
              type="text"
              placeholder="Buscar destinos"
              className="w-full sm:w-auto px-6 py-3 rounded-lg text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-pastelBlue focus:ring-opacity-50 transition-all duration-300 ease-in-out"
            />
            <button
              className="flex items-center px-8 py-3 rounded-lg text-white transition-all duration-300 ease-in-out hover:bg-[rgb(255_124_93)]/90"
              style={{
                backgroundColor: "rgb(255 124 93)", // Color especificado para el botón
              }}
            >
              <MagnifyingGlassIcon className="h-5 w-5 mr-2" /> {/* Ícono de lupa */}
              Buscar
            </button>
          </div>
        </div>
      </div>

      {/* Sección del carrusel: "Por qué elegirnos" y "Cómo funciona" */}
      <div className="py-16 text-center">
        <h2
          className={`text-3xl font-bold text-gray-800 mb-6 transition-opacity duration-1000 ${fade ? "opacity-100" : "opacity-0"}`}
        >
          {currentSection === 0 ? "¿Por qué elegirnos?" : "¿Cómo funciona?"}
        </h2>

        {/* Contenido de las tarjetas */}
        <div className="max-w-5xl mx-auto">
          <div
            className={`grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8 transition-opacity duration-2000 ${fade ? "opacity-100" : "opacity-0"}`}
          >
            {currentSection === 0 ? (
              <>
                <div className="bg-white p-6 rounded-lg shadow-lg">
                  <h3 className="text-xl font-semibold text-gray-800 mb-4">Tarifas Exclusivas</h3>
                  <p className="text-gray-600">Obtén acceso a tarifas especiales y ofertas solo para miembros.</p>
                </div>
                <div className="bg-white p-6 rounded-lg shadow-lg">
                  <h3 className="text-xl font-semibold text-gray-800 mb-4">Reserva Flexible</h3>
                  <p className="text-gray-600">Disfruta de opciones de cancelación flexibles para tu tranquilidad.</p>
                </div>
                <div className="bg-white p-6 rounded-lg shadow-lg">
                  <h3 className="text-xl font-semibold text-gray-800 mb-4">Atención Personalizada</h3>
                  <p className="text-gray-600">Nuestro equipo está disponible para ayudarte en cada paso del proceso.</p>
                </div>
              </>
            ) : (
              <>
                <div className="bg-white p-6 rounded-lg shadow-lg">
                  <h3 className="text-xl font-semibold text-gray-800 mb-4">1. Elige tu destino</h3>
                  <p className="text-gray-600">Explora nuestros destinos más populares y selecciona el que más te guste.</p>
                </div>
                <div className="bg-white p-6 rounded-lg shadow-lg">
                  <h3 className="text-xl font-semibold text-gray-800 mb-4">2. Reserva tu paquete</h3>
                  <p className="text-gray-600">Escoge el paquete que más te interese, ajusta las fechas y haz tu reserva.</p>
                </div>
                <div className="bg-white p-6 rounded-lg shadow-lg">
                  <h3 className="text-xl font-semibold text-gray-800 mb-4">3. Disfruta tu aventura</h3>
                  <p className="text-gray-600">Prepárate para vivir una experiencia inolvidable en tu destino seleccionado.</p>
                </div>
              </>
            )}
          </div>
        </div>
      </div>

      {/* Sección de destinos destacados */}
      <div className="py-8 px-4 sm:px-6 lg:px-8">
        <FeaturedDestinations />
      </div>

      <Footer />
    </div>
  );
};

export default HomePage;
