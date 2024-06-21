import { createRoot } from "react-dom/client";
import { ChakraProvider } from '@chakra-ui/react'
import Header from "./components/Header";
import Content from "./components/Content";

const App = () => {
    return (
        <ChakraProvider>
            <div>
                <Header />
                <Content />
            </div>
        </ChakraProvider>
    );
};

const container = document.getElementById("root");
const root = createRoot(container);
root.render(<App />);