/**
 * plugins/vuetify.js
 *
 * Framework documentation: https://vuetifyjs.com`
 */

// Styles
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'

// Composables
import { createVuetify } from 'vuetify'


const customTDBTheme = {
  dark: false,
  colors: {
    background: '#FFFFFF',
    surface: '#FFFFFF',
    'surface-bright': '#FFFFFF',
    'surface-light': '#EEEEEE',
    'surface-variant': '#424242',
    'on-surface-variant': '#EEEEEE',
    primary: '#F44336',
    'primary-darken-1': '#E53935',
    'primary-darken-2': '#D32F2F',
    'primary-darken-3': '#C62828',
    'primary-darken-4': '#C62828',
    'primary-lighten-1': '#EF5350',
    'primary-lighten-2': '#E57373',
    'primary-lighten-3': '#EF9A9A',
    'primary-lighten-4': '#FFCDD2',
    'primary-lighten-5': '#FFEBEE',
    'primary-accent-1': '#FF8A80',
    'primary-accent-2': '#FF5252',
    'primary-accent-3': '#FF1744',
    'primary-accent-4': '#D50000',
    secondary: '#4CAF50',
    'secondary-darken-1': '#43A047',
    'secondary-darken-2': '#388E3C',
    'secondary-darken-3': '#2E7D32',
    'secondary-darken-4': '#1B5E20',
    'secondary-lighten-1': '#66BB6A',
    'secondary-lighten-2': '#81C784',
    'secondary-lighten-3': '#A5D6A7',
    'secondary-lighten-4': '#C8E6C9',
    'secondary-lighten-5': '#E8F5E9',
    'secondary-accent-1': '#B9F6CA',
    'secondary-accent-2': '#69F0AE',
    'secondary-accent-3': '#00E676',
    'secondary-accent-4': '#00C853',
    error: '#D50000',
    info: '#4CAF50',
    success: '#4CAF50',
    warning: '#FB8C00',
  },
  variables: {
    'border-color': '#000000',
    'border-opacity': 0.12,
    'high-emphasis-opacity': 0.87,
    'medium-emphasis-opacity': 0.60,
    'disabled-opacity': 0.38,
    'idle-opacity': 0.04,
    'hover-opacity': 0.04,
    'focus-opacity': 0.12,
    'selected-opacity': 0.08,
    'activated-opacity': 0.12,
    'pressed-opacity': 0.12,
    'dragged-opacity': 0.08,
    'theme-kbd': '#212529',
    'theme-on-kbd': '#FFFFFF',
    'theme-code': '#F5F5F5',
    'theme-on-code': '#000000',
  }
}

// https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
export default createVuetify({
  theme: {
    defaultTheme: 'customTDBTheme',
    themes: {
      customTDBTheme,
    },
  },
})
