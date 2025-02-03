import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const MyBookings = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [bookings, setBookings] = useState([]);
  const [showLoginAlert, setShowLoginAlert] = useState(false); // Estado para mostrar el mensaje

  // Comprobación si el usuario está logueado
  const isLoggedIn = !!localStorage.getItem('user');

  useEffect(() => {
    const savedBookings = JSON.parse(localStorage.getItem('bookings')) || [];
    setBookings(savedBookings);

    // Si se recibe una nueva reserva, agregamos la nueva reserva a la lista
    if (location.state?.newBooking) {
      const newBooking = location.state.newBooking;
      const isBookingExist = savedBookings.some(booking => booking.id === newBooking.id);
      if (!isBookingExist) {
        const updatedBookings = [...savedBookings, newBooking];
        setBookings(updatedBookings);
        localStorage.setItem('bookings', JSON.stringify(updatedBookings));
      }
    }
  }, [location.state?.newBooking]);

  const handleRemoveBooking = (index) => {
    const updatedBookings = bookings.filter((_, i) => i !== index);
    setBookings(updatedBookings);
    localStorage.setItem('bookings', JSON.stringify(updatedBookings));
  };

  const handleSelectBooking = (booking) => {
    if (!isLoggedIn) {
      setShowLoginAlert(true); // Mostrar el cartel si no está logueado
      setTimeout(() => {
        setShowLoginAlert(false); // Ocultar el cartel después de un tiempo
      }, 5000); // El cartel desaparece después de 5 segundos
      return;
    }
    navigate('/seleccion-pasajes', { state: { booking } });
  };

  const handleCreateNewBooking = () => {
    navigate('/packages'); // Ruta para crear una nueva reserva
  };

  if (bookings.length === 0) {
    return (
      <div className="min-h-screen bg-gray-50 flex flex-col justify-center items-center px-4">
        <div className="text-center">
          <h1 className="text-3xl sm:text-4xl font-bold text-gray-800 mb-4">¡Aún no tienes reservas!</h1>
          <p className="text-gray-600 mb-6">Parece que aún no has reservado ningún viaje. ¿Qué tal si eliges uno ahora?</p>
          <button
            onClick={handleCreateNewBooking}
            className="w-full sm:w-auto py-3 bg-[rgb(26,54,93)] text-white rounded-md hover:bg-[rgb(26,54,93,0.8)] min-w-[220px]"
          >
            Realizar una nueva reserva
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col px-4">
      <div className="container mx-auto py-8 flex-grow">
        {/* Banner de alerta si el usuario no está logueado */}
        {showLoginAlert && (
          <div className="bg-red-500 text-white text-center py-3 rounded-md mb-6">
            <p>Debes estar logueado para poder realizar una compra. <strong><a href="/login" className="underline">Iniciar sesión</a></strong></p>
          </div>
        )}

        <h1 className="text-3xl sm:text-4xl font-bold text-center mb-8">Tus Reservas</h1>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {bookings.map((booking, index) => (
            <div key={index} className="bg-white shadow-md rounded-lg overflow-hidden flex flex-col">
              <div className="relative">
                <img
                  src={booking.image || 'https://via.placeholder.com/400x250'}
                  alt={booking.name}
                  className="w-full h-48 sm:h-60 object-cover rounded-t-lg"
                />
                <div className="absolute top-0 left-0 bg-gradient-to-t from-black to-transparent text-white p-4">
                  <h3 className="text-xl font-bold">{booking.name}</h3>
                </div>
              </div>
              <div className="p-6 flex-grow">
                <div className="space-y-2 text-gray-600">
                  <p>Precio: ${booking.price}</p>
                  <p>Duración: {booking.duration}</p>
                  <p>{booking.description}</p>
                </div>
              </div>
              <div className="p-6 flex flex-col gap-4">
                <button
                  className="w-full py-3 bg-[rgb(26,54,93)] text-white rounded-md hover:bg-[rgb(26,54,93,0.8)] min-w-[220px]"
                  onClick={() => handleSelectBooking(booking)} // Verificación antes de proceder a comprar
                >
                  Comprar
                </button>
                <button
                  className="w-full bg-gray-200 text-gray-700 py-2 px-4 rounded-md hover:bg-gray-300 min-w-[220px]"
                  onClick={() => handleRemoveBooking(index)}
                >
                  Cancelar reserva
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default MyBookings;
