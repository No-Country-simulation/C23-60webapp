// src/index.js
import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App'; // Importamos el componente App
import { BrowserRouter } from 'react-router-dom'; // Importamos BrowserRouter
import reportWebVitals from './reportWebVitals';

// Envolvemos la aplicación en el Router para habilitar el enrutamiento
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();
