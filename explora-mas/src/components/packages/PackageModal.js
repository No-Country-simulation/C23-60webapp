import React from 'react';

const PackageModal = ({ selectedPackage, onClose, onReserve }) => {
  // Asegúrate de que selectedPackage esté definido antes de acceder a sus propiedades
  if (!selectedPackage) {
    return null; // Si no hay paquete seleccionado, no se muestra nada
  }

  // Destructuramos las propiedades de selectedPackage
  const { name, image, description, includes, price, duration } = selectedPackage;

  return (
    <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div className="bg-white p-6 rounded-lg w-[600px]">
        <h2 className="text-2xl font-bold mb-4">{name}</h2>
        <img
          src={image}
          alt={name}
          className="w-full h-48 object-cover rounded-lg mb-4"
        />
        <p className="text-gray-600 mb-4">{description}</p>

        <div className="mb-4">
          <h4 className="font-bold mb-2">El paquete incluye:</h4>
          {/* Verificamos si 'includes' es un arreglo antes de mapearlo */}
          {Array.isArray(includes) && includes.length > 0 ? (
            <ul className="list-disc list-inside space-y-1">
              {includes.map((item, index) => (
                <li key={index} className="text-gray-600">{item}</li>
              ))}
            </ul>
          ) : (
            <p className="text-gray-600">Vuelos, hotelería y transporte.</p>
          )}
        </div>

        <div className="flex justify-between items-center mt-4">
          <div>
            <span className="text-gray-600">Precio por persona</span>
            <p className="text-2xl font-bold text-primary">${price}</p>
          </div>
          <span className="text-gray-600">{duration}</span>
        </div>

        <div className="flex justify-end gap-4 mt-6">
          <button className="px-4 py-2 bg-gray-300 text-gray-700 rounded-md" onClick={onClose}>Cerrar</button>
          <button
            className="w-full py-2 bg-[rgb(26,54,93)] text-white rounded-md hover:bg-[rgb(26,54,93,0.8)]"
            onClick={() => onReserve(selectedPackage)}
          >
            Reservar ahora
          </button>
        </div>
      </div>
    </div>
  );
};

export default PackageModal;
