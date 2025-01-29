import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const BookingDetails = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const { booking } = location.state || {}; // Obtener la reserva desde el estado

  const [quantity, setQuantity] = useState(1); // Cantidad de pasajes seleccionados
  const [totalPrice, setTotalPrice] = useState(0); // Precio total después del descuento
  const [currentDate] = useState(new Date()); // Fecha y hora actuales

  // Efecto para inicializar el totalPrice con el precio de un solo pasaje o 0 si no es válido
  useEffect(() => {
    if (booking && typeof booking.price === 'number' && !isNaN(booking.price)) {
      setTotalPrice(booking.price); // Inicia con el precio de un solo pasaje
    } else {
      setTotalPrice(0); // Si no hay precio válido, inicializa en 0
    }
  }, [booking]);

  // Lógica para manejar el cambio de cantidad y calcular el precio total con descuento
  const handleQuantityChange = (e) => {
    const newQuantity = parseInt(e.target.value, 10);
    setQuantity(newQuantity);

    // Aplicar el descuento según la cantidad
    let discount = 0;
    if (newQuantity === 2) {
      discount = 0.10; // 10% de descuento si son 2 pasajes
    } else if (newQuantity === 3) {
      discount = 0.15; // 15% de descuento si son 3 pasajes
    } else if (newQuantity >= 4) {
      discount = 0.20; // 20% de descuento si son 4 o más pasajes
    }

    // Calculamos el precio total después del descuento
    if (booking && typeof booking.price === 'number' && !isNaN(booking.price)) {
      const priceAfterDiscount = booking.price * newQuantity * (1 - discount);
      setTotalPrice(priceAfterDiscount);
    } else {
      setTotalPrice(0); // Si no hay un precio válido, ponemos el precio total en 0
    }
  };

  // Redirigir a la página de confirmación de compra
  const handleBuyNow = () => {
    if (totalPrice > 0) {
      navigate('/confirmacion', { state: { booking, quantity, totalPrice } });
    } else {
      alert('El precio total no es válido.');
    }
  };

  // Función para volver atrás
  const handleGoBack = () => {
    navigate(-1); // Vuelve a la página anterior
  };

  // Formatear la fecha y hora actual
  const formatDate = (date) => {
    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
    return date.toLocaleDateString('es-ES', options);
  };

  if (!booking) {
    return <p>No se encontraron detalles de la reserva.</p>;
  }

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      <div className="container mx-auto px-4 py-8 flex-grow">
        <div className="flex justify-between items-center mb-8">
          <button
            onClick={handleGoBack}
            className="text-sm text-gray-600 hover:text-gray-800"
          >
            ← Volver
          </button>
          <div className="text-right text-gray-600">
            <p className="text-sm">Fecha y hora: {formatDate(currentDate)}</p>
          </div>
        </div>

        <h1 className="text-3xl sm:text-4xl font-bold text-center mb-8">Detalles de tu Reserva</h1>

        <div className="bg-white shadow-lg rounded-lg overflow-hidden p-6 md:p-10">
          <h2 className="text-2xl font-bold text-primary mb-4">Resumen del Paquete</h2>
          <p className="text-gray-700"><strong>Paquete:</strong> {booking.name}</p>
          <p className="text-gray-700"><strong>Precio por pasaje:</strong> ${booking.price} {booking.currency}</p>

          <div className="mt-6">
            <label className="block text-gray-600 font-medium">Cantidad de Pasajes:</label>
            <input
              type="number"
              value={quantity}
              onChange={handleQuantityChange}
              min="1"
              className="w-full p-3 border border-gray-300 rounded-md mt-2 focus:ring-primary focus:border-primary"
            />
          </div>

          <p className="mt-4 text-gray-700"><strong>Precio Total: $</strong> {totalPrice.toFixed(2)} {booking.currency}</p>

          <button
            onClick={handleBuyNow}
            className="w-full py-3 bg-[rgb(26,54,93)] text-white rounded-md hover:bg-[rgb(26,54,93,0.8)]"
          >
            Confirmar compra
          </button>
        </div>
      </div>
    </div>
  );
};

export default BookingDetails;
