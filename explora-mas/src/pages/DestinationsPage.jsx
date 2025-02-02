// src/pages/DestinationsPage.js
import React, { useState } from 'react';
import { Footer } from "../components/common/Footer"; // Asegúrate de que la ruta sea correcta
import { ChevronDownIcon, ChevronUpIcon } from '@heroicons/react/20/solid'; // Importamos los íconos de flechas

const destinos = [
  {
    id: 1,
    nombre: "Bali, Indonesia",
    imagen: "https://images.unsplash.com/photo-1537996194471-e657df975ab4",
    precio: "$899",
    duracion: "7 días",
    descripcion: "Explora las hermosas playas y la rica cultura de Bali",
    calificacion: 4.8,
  },
  {
    id: 2,
    nombre: "Santorini, Grecia",
    imagen: "https://encrypted-tbn3.gstatic.com/licensed-image?q=tbn:ANd9GcTjvwOHltY1-CyWJoN0pvhzEyXU2fQQowcdZBKM95diJ9O9bTVyMmgChLE7DbTor2DFfNsijFWeO39MQeY3bF-l0wm8qlDs1lpudBlTIQ",
    precio: "$1299",
    duracion: "5 días",
    descripcion: "Vive los impresionantes atardeceres de las Islas Griegas",
    calificacion: 4.9,
  },
  {
    id: 3,
    nombre: "Machu Picchu, Perú",
    imagen: "https://images.unsplash.com/photo-1587595431973-160d0d94add1",
    precio: "$1499",
    duracion: "8 días",
    descripcion: "Descubre la antigua ciudadela Inca en los Andes",
    calificacion: 4.7,
  },
  {
    id: 4,
    nombre: "Tokio, Japón",
    imagen: "https://images.unsplash.com/photo-1503899036084-c55cdd92da26",
    precio: "$1699",
    duracion: "10 días",
    descripcion: "Sumérgete en la cultura y tecnología japonesa",
    calificacion: 4.8,
  },
  {
    id: 5,
    nombre: "París, Francia",
    imagen: "https://images.unsplash.com/photo-1502602898657-3e91760cbb34",
    precio: "$1299",
    duracion: "6 días",
    descripcion: "Vive el romance de la Ciudad de la Luz",
    calificacion: 4.6,
  },
  {
    id: 6,
    nombre: "Maldivas",
    imagen: "https://images.unsplash.com/photo-1514282401047-d79a71a590e8",
    precio: "$2499",
    duracion: "7 días",
    descripcion: "Relájate en bungalows sobre el agua en el paraíso",
    calificacion: 4.9,
  },
];

const DestinationsPage = () => {
  const [openFAQ, setOpenFAQ] = useState(null); // Estado para manejar el acordeón de FAQ

  // Función para manejar la apertura/cierre de cada pregunta
  const toggleFAQ = (index) => {
    setOpenFAQ(openFAQ === index ? null : index); // Si la misma pregunta es clickeada, se cierra
  };

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      <div className="container mx-auto px-4 py-8 flex-grow">
        <div className="mb-8">
          <h1 className="text-4xl font-bold text-center mb-4 text-gray-800">
            Explorar destinos
          </h1>
          <p className="text-gray-600 text-center max-w-2xl mx-auto">
            Descubre lugares increíbles alrededor del mundo y comienza a planificar tu próxima aventura con nuestra selección curada de destinos.
          </p>
        </div>

        {/* Grilla de destinos */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8 mb-12">
          {destinos.map((destino) => (
            <div key={destino.id} className="overflow-hidden bg-white shadow-lg rounded-lg hover:shadow-xl transition-all duration-300 ease-in-out">
              <img
                src={destino.imagen}
                alt={destino.nombre}
                className="w-full h-48 sm:h-60 object-cover rounded-t-lg"
              />
              <div className="p-6">
                <h3 className="text-xl font-bold text-gray-800 mb-2">{destino.nombre}</h3>
                <p className="text-gray-600 mb-2">{destino.descripcion}</p>
                <div className="flex justify-between items-center text-gray-600">
                  <span>{destino.duracion}</span>
                  <span className="font-semibold text-primary">
                    {destino.precio}
                  </span>
                </div>
                <div className="mt-2 text-yellow-500">
                  {"★".repeat(Math.floor(destino.calificacion))}
                  <span className="text-gray-400 ml-1">
                    ({destino.calificacion})
                  </span>
                </div>
              </div>
              <div className="p-4 pt-0">
                <button className="w-full py-2 bg-[rgb(26,54,93)] text-white rounded-md hover:bg-[rgb(26,54,93,0.8)]">
                  Ver detalles
                </button>
              </div>
            </div>
          ))}
        </div>

        {/* Sección de Preguntas Frecuentes con acordeón */}
        <div className="max-w-3xl mx-auto mb-12">
          <h2 className="text-3xl font-bold text-center mb-8 text-gray-800">
            Preguntas Frecuentes
          </h2>
          <div className="w-full">
            {[
              { pregunta: "¿Cómo puedo reservar un destino?", respuesta: "Para reservar un destino, selecciona el destino que te interese y sigue el proceso de reserva." },
              { pregunta: "¿Qué incluyen los paquetes de viaje?", respuesta: "Nuestros paquetes incluyen alojamiento, traslados y algunas actividades según el destino." },
              { pregunta: "¿Cuál es la política de cancelación?", respuesta: "Las cancelaciones varían según el destino. Generalmente, si cancelas más de 30 días antes, recibes un reembolso completo." },
              { pregunta: "¿Ofrecen seguro de viaje?", respuesta: "Sí, ofrecemos varias opciones de seguro que puedes agregar a tu reserva." },
            ].map((item, index) => (
              <div key={index} className="border-b border-gray-200 mb-4">
                <button
                  className="w-full text-left text-xl font-semibold text-gray-800 flex items-center justify-between py-4 px-6 bg-gray-200 hover:bg-gray-300 rounded-lg transition-all duration-300 ease-in-out focus:outline-none"
                  onClick={() => toggleFAQ(index)}
                >
                  <span>{item.pregunta}</span>
                  <span>
                    {openFAQ === index ? (
                      <ChevronUpIcon className="h-5 w-5 text-blue-500" />
                    ) : (
                      <ChevronDownIcon className="h-5 w-5 text-blue-500" />
                    )}
                  </span>
                </button>
                {openFAQ === index && (
                  <p className="text-gray-600 mt-2 px-6">{item.respuesta}</p>
                )}
              </div>
            ))}
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default DestinationsPage;
