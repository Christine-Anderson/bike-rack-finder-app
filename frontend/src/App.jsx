import { createRoot } from "react-dom/client";
import { ChakraProvider } from '@chakra-ui/react'
import Header from "./components/Header";
import Content from "./components/Content";
import { APIProvider } from '@vis.gl/react-google-maps';

const apiKey = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;

const App = () => {
    return (
        <ChakraProvider>
            <APIProvider apiKey={apiKey} onLoad={() => console.log('Maps API has loaded.')}>
                <div>
                    <Header />
                    <Content />
                </div>
            </APIProvider>
        </ChakraProvider>
    );
};

const container = document.getElementById("root");
const root = createRoot(container);
root.render(<App />);