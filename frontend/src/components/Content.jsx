import React, { useState } from 'react';
import { Flex, Container, VStack, Divider, Text, Button, useToast } from "@chakra-ui/react";
import BikeRackCard from "./BikeRackCard";
import BikeRackMap from "./BikeRackMap";
import ReportModal from "./ReportModal";
import SearchBar from './SearchBar';

const mockBikeRacks = [
    { poi: {key: 1, location: { lat: 49.2827, lng: -123.1207 }}, address: "Vancouver Art Gallery", numThefts: 5, rating: 3.5},
    { poi: {key: 2, location: { lat: 49.3043, lng: -123.1443 }}, address: "Stanley Park", numThefts: 5, rating: 3.5},
    { poi: {key: 3, location: { lat: 49.2713, lng: -123.1340 }}, address: "Granville Island", numThefts: 5, rating: 3.5},
    { poi: {key: 4, location: { lat: 49.2606, lng: -123.2460 }}, address: "UBC", numThefts: 5, rating: 3.5},
    { poi: {key: 5, location: { lat: 49.2886, lng: -123.1112 }}, address: "Canada Place", numThefts: 5, rating: 3.5},
];

const defaultCoordinates = {
    lat: 49.2827,
    lng: -123.1207
};

const Content = () => {
    const [visibleMarkers, setVisibleMarkers] = useState([]);
    const [clickedMarkerCoordinates, setClickedMarkerCoordinates] = useState(null);
    const [searchValue, setSearchValue] = useState('');
    const [center, setCenter] = useState(defaultCoordinates);

    const toast = useToast();

    const handleMapBoundsChange = (visibleMarkers) => {
        setVisibleMarkers(visibleMarkers);
    };

    const handleSearch = () => {
        const geocoder = new window.google.maps.Geocoder();
        geocoder.geocode({ address: searchValue }, (results, status) => {
            if (status === 'OK' && results && results[0]) {
                const location = results[0].geometry.location;
                setCenter({ lat: location.lat(), lng: location.lng() });
            } else {
                toast({
                    title: "Invalid Address",
                    description: "Please enter a valid address.",
                    status: "error",
                    duration: 5000,
                    isClosable: true,
                });
            }
        });
    };

    const onMapClick = (e) => {
        setClickedMarkerCoordinates({ lat: e.detail.latLng.lat, lng: e.detail.latLng.lng });
        setCenter(null);
    };
    
    return (
        <Container height="calc(100vh - 8rem)" maxW="80vw" padding="4" mt={5} mb={5}>
            <Flex direction="column" height="100%">
                <Flex flex="1" direction={{ base: "column", md: "row" }} padding="4" overflowY="auto">
                    <Flex direction="column">
                        <Text textAlign="center" fontSize="xl" fontWeight="bold" mb={2}>Bike Racks</Text>
                        <Divider />
                        <VStack spacing={4} p={4} w="20rem" overflowY="auto" borderRight="1px solid #E2E8F0">
                            
                            { mockBikeRacks
                                .filter(({ poi }) => visibleMarkers.some(marker => marker.poi.key === poi.key))
                                .map(({poi, address, numThefts, rating}) => (
                                    <BikeRackCard
                                        key={poi.key}
                                        address={address}
                                        numThefts={numThefts}
                                        rating={rating}
                                    />
                                ))
                            }

                        </VStack>
                    </Flex>

                    <Flex direction="column" flex="1">
                        <SearchBar
                            searchValue={searchValue}
                            setSearchValue={setSearchValue}
                            handleSearch={handleSearch}
                        />

                        <BikeRackMap
                            mockBikeRacks={mockBikeRacks}
                            center={center}
                            onMapBoundsChange={handleMapBoundsChange}
                            clickedMarkerCoordinates={clickedMarkerCoordinates}
                            onMapClick={onMapClick}
                        />

                        <Flex alignItems="center" justifyContent="space-between" mt={2}>
                            <Button colorScheme="blue" left="50%" transform="translateX(-50%)">Find Closest Rack</Button>
                            <ReportModal
                                reportType={"New Rack"}
                                address={"test"}
                                buttonSize={"md"}
                                buttonRight={"0"}
                                // todo figure out how to pass in the one that was clicked
                            />
                        </Flex>
                    </Flex>
                </Flex>
            </Flex>
        </Container>
    );
};

export default Content;