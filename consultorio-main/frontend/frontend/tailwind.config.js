/** @type {import('tailwindcss').Config} */

const defaultTheme = require('tailwindcss/defaultTheme')
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    screens: {
      'sm': '640px',
      'md': '768px',
      'lg': '1440px',
      'xl': '1920px',
      '2xl': '2560px',
    },
    extend: {
      fontFamily: {
        helv: ['Helvetica', ...defaultTheme.fontFamily.sans]
      },
    },
  },
  plugins: [
    require('tailwind-scrollbar')({ nocompatible: true }),
    require('tailwindcss-primeui')
  ],
}

