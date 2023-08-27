import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import {ChakraProvider, createStandaloneToast} from '@chakra-ui/react'
import './index.css'

const {ToastContainer} = createStandaloneToast()

ReactDOM
    .createRoot(document.getElementById('root'))
    .render(
        <React.StrictMode>
            <ChakraProvider>
                <App/>
            </ChakraProvider>
        </React.StrictMode>,
    )
