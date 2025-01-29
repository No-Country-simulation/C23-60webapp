import React, { useState } from 'react';
import { Routes, Route } from 'react-router-dom';
import Navbar from './components/common/Navbar';
import HomePage from './pages/HomePage';
import DestinationsPage from './pages/DestinationsPage';
import PackagesPage from './pages/PackagesPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import MyBookings from './pages/MyBookings';
import BookingDetails from './pages/BookingDetails';
import ConfirmationPurchase from './pages/ConfirmationPurchase';
import { AuthProvider } from './components/auth/AuthContext'; // Importar el AuthProvider
import LoginAdmin from './pages/LoginAdmin'; // Importar el nuevo LoginAdmin
import AdminPage from './pages/AdminPage'; // Página de admin que debe estar protegida

const App = () => {
  const [bookings, setBookings] = useState([]);
  const [isAuthenticated, setIsAuthenticated] = useState(false); // Para manejar la autenticación del admin

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
          
          {/* Ruta para el login del admin */}
          <Route path="/login-admin" element={<LoginAdmin setIsAuthenticated={setIsAuthenticated} />} />
          
          {/* Ruta protegida para el admin (solo accesible si está autenticado) */}
          <Route
            path="/admin"
            element={isAuthenticated ? <AdminPage /> : <LoginAdmin setIsAuthenticated={setIsAuthenticated} />}
          />
        </Routes>
      </div>
    </AuthProvider>
  );
};

export default App;
