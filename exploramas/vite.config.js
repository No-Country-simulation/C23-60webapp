import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";

export default defineConfig(() => {
  return {
    server: {
      proxy: {
        "/api": "https://exploramas.onrender.com",
      },
    },
    build: {
      outDir: "./dist", // Solo pasa una cadena aquí
      manifest: true, // Si necesitas un manifest, colócalo aquí
    },
    plugins: [tailwindcss(), react()],
  };
});
