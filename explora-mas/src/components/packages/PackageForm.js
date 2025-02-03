// src/components/packages/PackageForm.js
import React, { useState } from 'react';

const PackageForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    name: '',
    image: '',
    price: '',
    duration: '',
    description: '',
    includes: '',
    rating: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Convertir la propiedad "includes" en un array de strings
    const includesArray = formData.includes.split(',').map((item) => item.trim());

    // Llamar la función onSubmit que recibimos como prop, para enviar el nuevo paquete
    onSubmit({
      ...formData,
      includes: includesArray,
      price: parseFloat(formData.price),  // Asegurarse de que el precio sea un número
      rating: parseFloat(formData.rating), // Asegurarse de que la calificación sea un número
    });

    // Limpiar el formulario
    setFormData({
      name: '',
      image: '',
      price: '',
      duration: '',
      description: '',
      includes: '',
      rating: '',
    });
  };

  return (
    <form onSubmit={handleSubmit} className="max-w-lg mx-auto p-6 bg-white shadow-lg rounded-md">
      <h2 className="text-2xl font-bold text-center mb-6">Crear Nuevo Paquete</h2>

      <label htmlFor="name" className="block text-sm font-medium text-gray-700">Nombre</label>
      <input
        type="text"
        id="name"
        name="name"
        value={formData.name}
        onChange={handleChange}
        required
        className="w-full p-2 mb-4 border border-gray-300 rounded-md"
      />

      <label htmlFor="image" className="block text-sm font-medium text-gray-700">Imagen URL</label>
      <input
        type="url"
        id="image"
        name="image"
        value={formData.image}
        onChange={handleChange}
        required
        className="w-full p-2 mb-4 border border-gray-300 rounded-md"
      />

      <label htmlFor="price" className="block text-sm font-medium text-gray-700">Precio</label>
      <input
        type="number"
        id="price"
        name="price"
        value={formData.price}
        onChange={handleChange}
        required
        className="w-full p-2 mb-4 border border-gray-300 rounded-md"
      />

      <label htmlFor="duration" className="block text-sm font-medium text-gray-700">Duración</label>
      <input
        type="text"
        id="duration"
        name="duration"
        value={formData.duration}
        onChange={handleChange}
        required
        className="w-full p-2 mb-4 border border-gray-300 rounded-md"
      />

      <label htmlFor="description" className="block text-sm font-medium text-gray-700">Descripción</label>
      <textarea
        id="description"
        name="description"
        value={formData.description}
        onChange={handleChange}
        required
        className="w-full p-2 mb-4 border border-gray-300 rounded-md"
      />

      <label htmlFor="includes" className="block text-sm font-medium text-gray-700">Incluye (separado por comas)</label>
      <input
        type="text"
        id="includes"
        name="includes"
        value={formData.includes}
        onChange={handleChange}
        required
        className="w-full p-2 mb-4 border border-gray-300 rounded-md"
      />

      <label htmlFor="rating" className="block text-sm font-medium text-gray-700">Calificación</label>
      <input
        type="number"
        id="rating"
        name="rating"
        value={formData.rating}
        onChange={handleChange}
        required
        className="w-full p-2 mb-4 border border-gray-300 rounded-md"
        step="0.1"
        max="5"
      />

      <button type="submit" className="w-full py-3 bg-blue-500 text-white rounded-md hover:bg-blue-400">Crear Paquete</button>
    </form>
  );
};

export default PackageForm;
