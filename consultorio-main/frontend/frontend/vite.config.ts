import { defineConfig } from 'vite'
import {resolve} from 'path'
import vue from '@vitejs/plugin-vue'
import Components from 'unplugin-vue-components/vite';
import {PrimeVueResolver} from '@primevue/auto-import-resolver';

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    Components({
      resolvers: [
        PrimeVueResolver()
      ]
    })
  ],
  optimizeDeps: {
    exclude: ['primevue']
  },
  define: {
    __VUE_PROD_DEVTOOLS__: 'true',
  },
  resolve: {
    alias: [{find: "@", replacement: resolve(__dirname, "./src") }]
  },
  server: {
    port:8900,
    proxy: {
      '/api': {
        target: 'http://localhost:8100',
        changeOrigin: true
      },
      '/login': {
        target: 'http://localhost:8100',
        changeOrigin: true
      },
      '/logout': {
        target: 'http://localhost:8100',
        changeOrigin: true
      }
    }
  }
})
