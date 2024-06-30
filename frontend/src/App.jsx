import { createRoot } from "react-dom/client";
import { ChakraProvider } from '@chakra-ui/react'
import Header from "./components/Header";
import Content from "./components/Content";
import { APIProvider } from '@vis.gl/react-google-maps';
import { ReactKeycloakProvider } from "@react-keycloak/web";
import keycloak from "../keycloakConfig";

const apiKey = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;

const App = () => {
    return (
        <ReactKeycloakProvider authClient={keycloak}>
                <ChakraProvider>
                    <APIProvider apiKey={apiKey} onLoad={() => console.log('Maps API has loaded.')}>
                        <div>
                            <Header />
                            <Content />
                        </div>
                    </APIProvider>
                </ChakraProvider>
        </ReactKeycloakProvider>
    );
};

const container = document.getElementById("root");
const root = createRoot(container);
root.render(<App />);