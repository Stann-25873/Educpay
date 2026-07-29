import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    // Configuration HMR explicite pour éviter les erreurs de WebSocket sur le port 3000
    hmr: {
      protocol: 'ws',
      host: 'localhost',
    },
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false, // Évite les erreurs si le backend utilise du HTTPS en développement
        //rewrite: (path) => path.replace(/^\/api/, ''), // Décommente cette ligne si ton backend Spring Boot n'attend PAS préfixé par /api
      },
    },
  },
});