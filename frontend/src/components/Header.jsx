import React from "react";
import { Flex, Heading, Spacer, Link } from "@chakra-ui/react";
import { useKeycloak } from "@react-keycloak/web";

const Header = () => {
    const {keycloak} = useKeycloak();

    const handleLogin = () => {
        keycloak.login();
    };

    const handleLogout = () => {
        keycloak.logout();
    };

    return (
        <Flex p={4} align="center" bg="gray.100" borderBottom="1px solid #E2E8F0" height="4rem">
            <Heading size="md">Bike Rack Finder</Heading>
            <Spacer />
            {keycloak.authenticated ? (
                <Link onClick={handleLogout}>Logout</Link>
            ) : (
                <Link onClick={handleLogin}>Login</Link>
            )}
        </Flex>
    );
};

export default Header;