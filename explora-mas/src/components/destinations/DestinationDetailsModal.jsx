// src/components/DestinationDetailsModal.js
import React from "react";

const DestinationDetailsModal = ({ destination, onClose }) => {
  return (
    <div className="fixed inset-0 bg-gray-900 bg-opacity-50 flex justify-center items-center z-50">
      <div className="bg-white p-6 rounded-lg w-full max-w-md sm:max-w-lg border-4 border-[rgb(26,54,93)]">
        <h3 className="text-2xl font-bold text-gray-800 mb-4">{destination.name}</h3>
        <img
          src={destination.image}
          alt={destination.name}
          className="w-full h-48 object-cover rounded-lg mb-4"
        />
        <p className="text-gray-600 mb-4">{destination.description}</p>

        <div className="mb-4">
          <h4 className="font-bold mb-2">El viaje incluye:</h4>
          <ul className="list-disc list-inside space-y-1">
            {destination.includes.map((item, index) => (
              <li key={index} className="text-gray-600">{item}</li>
            ))}
          </ul>
        </div>

        <div className="mb-4">
          <h4 className="font-bold mb-2">Próximas fechas disponibles:</h4>
          <div className="flex flex-wrap gap-2">
            {destination.dates.map((date, index) => (
              <span
                key={index}
                className="bg-gray-100 px-3 py-1 rounded-full text-sm text-gray-600"
              >
                {date}
              </span>
            ))}
          </div>
        </div>

        <div className="flex justify-between items-center mt-4">
          <div>
            <span className="text-gray-600">Precio por persona</span>
            <p className="text-2xl font-bold text-primary">
              {destination.price}
            </p>
          </div>
          <span className="text-gray-600">{destination.duration}</span>
        </div>

        <div className="flex justify-end mt-6">
          <button
            onClick={onClose}
            className="px-6 py-2 bg-gray-300 text-gray-800 rounded-md hover:bg-gray-400 transition-all duration-300 ease-in-out"
          >
            Cerrar
          </button>
        </div>
      </div>
    </div>
  );
};

export default DestinationDetailsModal;
