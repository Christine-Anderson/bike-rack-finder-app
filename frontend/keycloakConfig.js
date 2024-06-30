import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
    url: "http://localhost:8181",
    realm: "bike-rack-finder-realm",
    clientId: "react-app-client",
});

export default keycloak;