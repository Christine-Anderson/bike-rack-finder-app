import React, { useState, useEffect } from "react";
import { Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton } from "@chakra-ui/react";
import { Button, Text, FormControl, FormLabel, Textarea, useDisclosure, useToast } from "@chakra-ui/react";
import { useKeycloak } from "@react-keycloak/web";
import { useMutation, useQueryClient } from "@tanstack/react-query";

import submitReport from "../queries/submitReport";
import submitNewRackReport from "../queries/submitNewRackReport";

const ReportModal = ({ rackId, address, reportType, clickedMarkerCoordinates, buttonSize, buttonRight }) => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const {keycloak} = useKeycloak();
    const toast = useToast();
    const queryClient = useQueryClient();
    const theftReportMutation = useMutation(submitReport, {
        onSuccess: () => {
            queryClient.invalidateQueries('bikeRacks');
        },
    });
    const removalReportMutation = useMutation(submitReport, {
        onSuccess: () => {
            queryClient.invalidateQueries('bikeRacks');
        },
    });
    const newRackReportMutation = useMutation(submitNewRackReport, {
        onSuccess: () => {
            queryClient.invalidateQueries('bikeRacks');
        },
    });

    let [value, setValue] = React.useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [modalAddress, setModalAddress] = useState("Unknown Address");

    let handleInputChange = (ev) => {
        let inputValue = ev.target.value
        setValue(inputValue)
    }

    const handleOpen = () => {
        if (keycloak.authenticated) {
            onOpen();
        } else {
            toast({
                title: "Login to Access",
                description: "Please login to submit a report.",
                status: "info",
                duration: 5000,
                isClosable: true,
            })
        }
    }

    const handleClose = () => {
        onClose();
        setValue("");
        setIsLoading(false);
    };

    useEffect(() => {
        if (reportType === "New Rack" && clickedMarkerCoordinates) {
            fetchAddress(clickedMarkerCoordinates.lat, clickedMarkerCoordinates.lng);
        }
    }, [clickedMarkerCoordinates]);

    const fetchAddress = (lat, lng) => {
        const geocoder = new window.google.maps.Geocoder();

        const cacheKey = `${lat},${lng}`;
        if (localStorage.getItem(cacheKey)) {
            const cachedResult = JSON.parse(localStorage.getItem(cacheKey));
            setModalAddress(cachedResult);
            return;
        }

        geocoder.geocode({ location: { lat, lng } }, (results, status) => {
            if (status === 'OK' && results && results[0]) {
                const addressResult = results[0].formatted_address;
                setModalAddress(addressResult);

                localStorage.setItem(cacheKey, addressResult);
            } else {
                setModalAddress("Unknown Address");
            }
        });
    };

    const handleSubmit = () => {
        const userId = keycloak.tokenParsed.sub;
        const accessToken = keycloak.token;
        setIsLoading(true);

        const report = {
            details: value,
            userId,
            accessToken
        }

        if (reportType === "Theft") {
            theftReportMutation.mutate({
                rackId,
                reportType: "THEFT",
                ...report,
            });
        } else if (reportType === "Removal") {
            removalReportMutation.mutate({
                rackId, 
                reportType: "REMOVED_RACK",
                ...report,
            });
        } else if (reportType === "New Rack") {
            if (!clickedMarkerCoordinates) {
                setIsLoading(false);
                toast({
                    title: "No Location Selected",
                    description: "Please select a location on the map.",
                    status: "error",
                    duration: 5000,
                    isClosable: true,
                })
            } else {
                newRackReportMutation.mutate({
                    reportType: "NEW_RACK",
                    address: modalAddress,
                    latitude: clickedMarkerCoordinates.lat,
                    longitude: clickedMarkerCoordinates.lng,
                    ...report,
                });
            }
        }
    }

    useEffect(() => {
        if (theftReportMutation.isError || removalReportMutation.isError || newRackReportMutation.isError) {
            const errorMessage = theftReportMutation.isError? theftReportMutation.error 
                : removalReportMutation.isError? removalReportMutation.error
                : newRackReportMutation.isError? newRackReportMutation.error
                : "Internal error.";

            toast({
                title: "Error Submitting Report",
                description: `${errorMessage}`,
                status: "error",
                duration: 5000,
                isClosable: true,
            });
            setIsLoading(false);
        } else if (theftReportMutation.isSuccess || removalReportMutation.isSuccess || newRackReportMutation.isSuccess) {
            handleClose();
            setIsLoading(false);
        }
    }, [
        theftReportMutation.isError, theftReportMutation.isSuccess,
        removalReportMutation.isError, removalReportMutation.isSuccess,
        newRackReportMutation.isError, newRackReportMutation.isSuccess
    ])

    return (
        <>
            <Button size={buttonSize} right={buttonRight} onClick={handleOpen}>
                {`Report ${reportType}`}
            </Button>

            <Modal isOpen={isOpen} onClose={handleClose}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>
                        {`Report ${reportType}`}
                    </ModalHeader>
                    <ModalCloseButton />
                    <ModalBody pb={6}>
                        <FormControl>
                            <Text align="center" mb={6}>
                                {`Are you sure you want to report ${reportType.toLowerCase()} at ${address || modalAddress}?`}
                            </Text>
                            <FormLabel>Details</FormLabel>
                            <Textarea
                                value={value}
                                onChange={handleInputChange}
                                placeholder="Enter additional details here"
                                size="sm"
                                maxLength={255}
                            />
                        </FormControl>
                </ModalBody>
                    <ModalFooter>
                        <Button 
                            isLoading={isLoading}
                            loadingText="Submitting"
                            colorScheme="blue"
                            mr={6}
                            onClick={handleSubmit}
                        >
                            Submit
                        </Button>
                        <Button onClick={handleClose}>Cancel</Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default ReportModal;