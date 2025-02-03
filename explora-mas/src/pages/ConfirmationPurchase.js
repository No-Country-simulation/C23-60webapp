import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const ConfirmationPurchase = () => {
  const location = useLocation();
  const { booking, quantity, totalPrice } = location.state || {}; // Obtener los datos de la reserva
  const navigate = useNavigate();

  const [transactionId, setTransactionId] = useState(null); // ID de transacción
  const [purchaseDate] = useState(new Date()); // Fecha de compra

  // Función para formatear la fecha de la compra
  const formatDate = (date) => {
    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
    return date.toLocaleDateString('es-ES', options);
  };

  // Comprobación si el usuario está logueado al inicio
  const isLoggedIn = !!localStorage.getItem('user');
  const user = isLoggedIn ? JSON.parse(localStorage.getItem('user')) : null; // Obtenemos el usuario desde localStorage

  useEffect(() => {
    if (isLoggedIn) {
      setTransactionId(`TRANS-${Date.now()}`); // Generar el ID de transacción único
    }
  }, [isLoggedIn]);

  // Función para eliminar la reserva comprada de localStorage
  const removeBookedItem = () => {
    const savedBookings = JSON.parse(localStorage.getItem('bookings')) || [];
    const updatedBookings = savedBookings.filter(item => item.id !== booking.id); // Filtramos el viaje comprado
    localStorage.setItem('bookings', JSON.stringify(updatedBookings));
  };

  // Copiar ID de transacción al portapapeles
  const handleCopyId = () => {
    if (transactionId) {
      navigator.clipboard.writeText(transactionId);
      alert("ID copiado al portapapeles.");
    }
  };

  // Redirigir a MyBookings después de la compra
  const handleRedirect = () => {
    removeBookedItem(); // Eliminar el viaje de las reservas locales
    navigate('/mis-viajes'); // Redirigir a la página de reservas
  };

  if (!booking) {
    return <p>No se encontraron detalles de la compra.</p>;
  }

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      <div className="container mx-auto px-4 py-8 flex-grow">
        <h1 className="text-3xl sm:text-4xl font-bold text-center mb-8">¡Compra Completada!</h1>
        
        <div className="bg-white shadow-lg rounded-lg overflow-hidden p-6 md:p-10">
          <div className="flex justify-between items-center mb-6">
            <div>
              <h2 className="text-2xl font-bold text-primary">Resumen de tu compra</h2>
            </div>
            <div className="text-right text-gray-600">
              <p className="text-sm">Fecha de compra: {formatDate(purchaseDate)}</p>
            </div>
          </div>

          {isLoggedIn && user ? (
            <p><strong>Usuario:</strong> {user.name || user.email}</p> // Mostrar el nombre o email del usuario
          ) : (
            <p><strong>Usuario:</strong> Invitado</p> // Mostrar "Invitado" si no está logueado
          )}

          <p><strong>Paquete:</strong> {booking.name}</p>
          <p><strong>Cantidad de pasajes:</strong> {quantity}</p>
          <p><strong>Precio total: $</strong> {totalPrice ? totalPrice.toFixed(2) : 'N/A'} {booking.currency}</p>

          {isLoggedIn && transactionId ? (
            <p><strong>ID de transacción:</strong> {transactionId}</p>
          ) : (
            <p><strong>ID de transacción:</strong> (Solo disponible para usuarios registrados)</p>
          )}
          
          <div className="flex justify-center mt-6 gap-4">
            {isLoggedIn ? (
              <button
                onClick={handleCopyId}
                className="py-2 px-4 bg-[rgb(26,54,93)] text-white rounded-md hover:bg-[rgba(100, 128, 165, 0.71)]"
              >
                Copiar ID
              </button>
            ) : (
              <button
                disabled
                className="py-2 px-4 bg-gray-300 text-white rounded-md cursor-not-allowed"
              >
                Copiar ID (Solo disponible si estás logueado)
              </button>
            )}
            <button
              onClick={handleRedirect}
              className="py-2 px-4 bg-green-500 text-white rounded-md hover:bg-green-600"
            >
              Volver a mis viajes
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ConfirmationPurchase;
