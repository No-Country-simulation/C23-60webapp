import React, { useState } from "react";
import DestinationDetailsModal from "./DestinationDetailsModal";

const destinations = [
  {
    id: 1,
    name: "Bali, Indonesia",
    image: "https://images.unsplash.com/photo-1537996194471-e657df975ab4",
    price: "$899",
    duration: "7 días",
    description: "Explora templos antiguos, playas paradisíacas y la cultura balinesa",
    includes: [
      "Vuelos ida y vuelta",
      "Hotel 4 estrellas",
      "Desayuno incluido",
      "Tours guiados",
      "Traslados aeropuerto-hotel",
    ],
    dates: ["15 Jun - 22 Jun", "1 Jul - 8 Jul", "15 Jul - 22 Jul"],
  },
  {
    id: 2,
    name: "Santorini, Greece",
    image: "https://encrypted-tbn3.gstatic.com/licensed-image?q=tbn:ANd9GcTjvwOHltY1-CyWJoN0pvhzEyXU2fQQowcdZBKM95diJ9O9bTVyMmgChLE7DbTor2DFfNsijFWeO39MQeY3bF-l0wm8qlDs1lpudBlTIQ",
    price: "$1299",
    duration: "5 días",
    description: "Disfruta de las mejores vistas del Mar Egeo y la arquitectura única",
    includes: [
      "Vuelos ida y vuelta",
      "Hotel con vista al mar",
      "Desayuno y cena",
      "Tour en barco",
      "Traslados aeropuerto-hotel",
    ],
    dates: ["10 Jun - 15 Jun", "20 Jun - 25 Jun", "5 Jul - 10 Jul"],
  },
  {
    id: 3,
    name: "Machu Picchu, Peru",
    image: "https://images.unsplash.com/photo-1587595431973-160d0d94add1",
    price: "$1499",
    duration: "8 días",
    description: "Descubre una de las maravillas del mundo y la cultura Inca",
    includes: [
      "Vuelos ida y vuelta",
      "Hotel boutique",
      "Todas las comidas",
      "Guía especializado",
      "Entradas a sitios arqueológicos",
    ],
    dates: ["1 Jun - 8 Jun", "15 Jun - 22 Jun", "1 Jul - 8 Jul"],
  },
];

const FeaturedDestinations = () => {
  const [selectedDestination, setSelectedDestination] = useState(null);

  const handleCloseDetails = () => {
    setSelectedDestination(null); // Cerrar el modal
  };

  return (
    <section className="py-16 bg-gray-50">
      <div className="container mx-auto px-4">
        <h2 className="text-3xl font-bold text-center mb-12">Destinos Destacados</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-8">
          {destinations.map((destination) => (
            <div
              key={destination.id}
              className="overflow-hidden bg-white shadow-lg rounded-lg hover:shadow-xl transition-all duration-300 ease-in-out"
            >
              <img
                src={destination.image}
                alt={destination.name}
                className="w-full h-48 sm:h-60 object-cover rounded-t-lg"
              />
              <div className="p-6">
                <h3 className="text-xl font-bold text-gray-800 mb-2">{destination.name}</h3>
                <p className="text-gray-600 mb-2">{destination.description}</p>
                <div className="flex justify-between items-center text-gray-600">
                  <span>{destination.duration}</span>
                  <span className="font-semibold text-primary">{destination.price}</span>
                </div>
                <div className="mt-2 text-yellow-500">
                  {"★".repeat(Math.floor(4.8))} {/* Simplificación del rating */}
                  <span className="text-gray-400 ml-1">(4.8)</span>
                </div>
              </div>
              <div className="p-4 pt-0">
                <button
                  onClick={() => setSelectedDestination(destination)}
                  className="w-full py-2 bg-[rgb(26,54,93)] text-white rounded-md hover:bg-[rgb(26,54,93,0.8)]"
                >
                  Ver detalles
                </button>
              </div>
            </div>
          ))}
        </div>

        {/* Modal de detalles */}
        {selectedDestination && (
          <DestinationDetailsModal
            destination={selectedDestination}
            onClose={handleCloseDetails}
          />
        )}
      </div>
    </section>
  );
};

export default FeaturedDestinations;
