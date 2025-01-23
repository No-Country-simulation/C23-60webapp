import React, { useState } from 'react';
import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import HomePage from './pages/HomePage';
import DestinationsPage from './pages/DestinationsPage';
import PackagesPage from './pages/PackagesPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import MyBookings from './pages/MyBookings';
import BookingDetails from './pages/BookingDetails';
import ConfirmationPurchase from './pages/ConfirmationPurchase';
import { AuthProvider } from './components/AuthContext'; // Importar el AuthProvider

const App = () => {
  const [bookings, setBookings] = useState([]);

  const handleAddBooking = (newBooking) => {
    setBookings([...bookings, newBooking]); 
  };

  return ( 
    <AuthProvider>
      <div>
        {/* El Navbar estará disponible en todas las páginas */}
        <Navbar />
        
        {/* Rutas de la aplicación */}
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/destinations" element={<DestinationsPage />} />
          <Route path="/packages" element={<PackagesPage onAddBooking={handleAddBooking} />} />
          <Route path="/mis-viajes" element={<MyBookings />} />
          <Route path="/seleccion-pasajes" element={<BookingDetails />} />
          <Route path="/confirmacion" element={<ConfirmationPurchase />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
        </Routes>
      </div>
    </AuthProvider>
  );
};

export default App;
