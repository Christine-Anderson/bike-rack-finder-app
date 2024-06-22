import React, { useState } from "react";
import { Flex, Heading, Spacer, Link } from "@chakra-ui/react";

const Header = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const handleLogin = () => {
        // todo login
        setIsLoggedIn(true);
    };

    const handleLogout = () => {
        // todo logout
        setIsLoggedIn(false);
    };

    return (
        <Flex p={4} align="center" bg="gray.100" borderBottom="1px solid #E2E8F0" height="4rem">
            <Heading size="md">Bike Rack Finder</Heading>
            <Spacer />
            {isLoggedIn ? (
                <Link onClick={handleLogout}>Logout</Link>
            ) : (
                <Link onClick={handleLogin}>Login</Link>
            )}
        </Flex>
    );
};

export default Header;