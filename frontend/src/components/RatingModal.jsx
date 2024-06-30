import React, { useState, useEffect } from "react";
import { Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton } from "@chakra-ui/react";
import { NumberInput, NumberInputField, NumberInputStepper, NumberIncrementStepper, NumberDecrementStepper } from "@chakra-ui/react";
import { Slider, SliderTrack, SliderFilledTrack, SliderThumb } from "@chakra-ui/react";
import { Button, FormControl, FormLabel, Flex, useDisclosure, useToast } from "@chakra-ui/react";
import { useKeycloak } from "@react-keycloak/web";
import { useMutation } from "@tanstack/react-query";

import submitRating from '../queries/submitBikeRackRating';

const RatingModal = ({rackId}) => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const {keycloak} = useKeycloak();
    const toast = useToast();
    const ratingMutation = useMutation(submitRating);

    const [value, setValue] = React.useState(0);
    const [isLoading, setIsLoading] = useState(false);

    const handleChange = (value) => setValue(value);

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

    const onClickSubmit = () => {
        const accessToken = keycloak.token;
        setIsLoading(true);
        ratingMutation.mutate({
            rackId: rackId,
            newRating: String(value),
            accessToken: accessToken
        });
    }

    useEffect(() => {
        if (ratingMutation.isError) {
            toast({
                title: "Error Submitting Rating",
                status: "error",
                duration: 5000,
                isClosable: true,
            });
            setIsLoading(false);
        } else if (ratingMutation.isSuccess) {
            console.log("submit: " + rackId + ", " + String(value));
            handleChange(0);
            onClose();
            setIsLoading(false);
        }
    }, [ratingMutation.isError, ratingMutation.isSuccess])

    return (
        <>
            <Button size="sm" onClick={handleOpen}>Rate</Button>

            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Rate Bike Rack</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody pb={6}>
                        <FormControl>
                            <FormLabel>Rating</FormLabel>
                            <Flex>
                                <NumberInput 
                                    maxW="100px"
                                    mr="2rem"
                                    value={value}
                                    onChange={handleChange}
                                    min={0}
                                    max={5}
                                    step={0.1}
                                >
                                    <NumberInputField />
                                    <NumberInputStepper>
                                    <NumberIncrementStepper />
                                    <NumberDecrementStepper />
                                    </NumberInputStepper>
                                </NumberInput>
                                <Slider
                                    flex="1"
                                    focusThumbOnChange={false}
                                    value={value}
                                    onChange={handleChange}
                                    min={0}
                                    max={5}
                                    step={0.1}
                                >
                                    <SliderTrack>
                                    <SliderFilledTrack />
                                    </SliderTrack>
                                    <SliderThumb fontSize="sm" boxSize="32px" />
                                </Slider>
                            </Flex>
                        </FormControl>
                </ModalBody>
                    <ModalFooter>
                        <Button
                            isLoading={isLoading}
                            loadingText="Submitting"
                            colorScheme="blue"
                            mr={6}
                            onClick={onClickSubmit}
                        >
                            Submit
                        </Button>
                        <Button onClick={onClose}>Cancel</Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default RatingModal;