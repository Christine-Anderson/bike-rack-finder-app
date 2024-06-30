import React, { useState } from 'react';
import { Flex, Container, VStack, Divider, Text, Button, useToast } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";

import BikeRackCard from "./BikeRackCard";
import BikeRackMap from "./BikeRackMap";
import ReportModal from "./ReportModal";
import SearchBar from './SearchBar';
import fetchBikeRacks from '../queries/fetchBikeRacks';

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
    const { isPending, isError, data: bikeRackData, error } = useQuery({ queryKey: ['bikeRacks'], queryFn: fetchBikeRacks });

    const handleMapBoundsChange = (visibleMarkers) => {
        setVisibleMarkers(visibleMarkers);
    };

    const setCenterNull = () => {
        setCenter(null);
    }

    const handleSearch = () => {
        const geocoder = new window.google.maps.Geocoder();

        if (localStorage.getItem(searchValue)) {
            const cachedResult = JSON.parse(localStorage.getItem(searchValue));
            setCenter({ lat: cachedResult.lat, lng: cachedResult.lng });
            return;
        }

        geocoder.geocode({ address: searchValue }, (results, status) => {
            if (status === 'OK' && results && results[0]) {
                const location = results[0].geometry.location;
                setCenter({ lat: location.lat(), lng: location.lng() });

                localStorage.setItem(searchValue, JSON.stringify({ lat: location.lat(), lng: location.lng() }));
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

    const onMapClick = (ev) => {
        setClickedMarkerCoordinates({ lat: ev.detail.latLng.lat, lng: ev.detail.latLng.lng });
    };
    
    return (
        <Container height="calc(100vh - 8rem)" maxW="80vw" padding="4" mt={5} mb={5}>
            <Flex direction="column" height="100%">
                <Flex flex="1" direction={{ base: "column", md: "row" }} padding="4" overflowY="auto">
                    <Flex direction="column">
                        <Text textAlign="center" fontSize="xl" fontWeight="bold" mb={2}>Bike Racks</Text>
                        <Divider />
                        <VStack spacing={4} p={4} w="20rem" overflowY="auto" borderRight="1px solid #E2E8F0">
                            { isPending ? (
                                <Text>Loading bike racks...</Text>
                            ) : isError ? (
                                <Text>Error loading bike racks: {error.message}</Text>
                            ) : (
                                bikeRackData?.length > 0 ? (
                                        bikeRackData
                                            .filter(({ poi }) => visibleMarkers.some(marker => marker.poi.rackId === poi.rackId))
                                            .map(({poi, address, theftsInLastMonth, rating}) => (
                                                <BikeRackCard
                                                    key={poi.rackId}
                                                    address={address}
                                                    numThefts={theftsInLastMonth}
                                                    rating={rating}
                                                />
                                            ))
                                    ) : (
                                        <Text>No bike rack data available.</Text>
                                    )
                            )}
                        </VStack>
                    </Flex>

                    <Flex direction="column" flex="1">
                        <SearchBar
                            searchValue={searchValue}
                            setSearchValue={setSearchValue}
                            handleSearch={handleSearch}
                        />

                        <BikeRackMap
                            bikeRacks={bikeRackData}
                            center={center}
                            setCenterNull={setCenterNull}
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