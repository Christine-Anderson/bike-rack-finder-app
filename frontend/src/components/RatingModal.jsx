import React from "react";
import { Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton } from "@chakra-ui/react";
import { NumberInput, NumberInputField, NumberInputStepper, NumberIncrementStepper, NumberDecrementStepper } from "@chakra-ui/react";
import { Slider, SliderTrack, SliderFilledTrack, SliderThumb } from "@chakra-ui/react";
import { Button, FormControl, FormLabel, Flex, useDisclosure, useToast } from "@chakra-ui/react";
import { useKeycloak } from "@react-keycloak/web";

const RatingModal = () => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const {keycloak} = useKeycloak();
    const toast = useToast();

    const [value, setValue] = React.useState(0);
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
                        <Button colorScheme="blue" mr={6}>Submit</Button>
                        {/* todo <Button colorScheme="blue" mr={6} onClick={onClick}>Submit</Button>
                        todo figure out how to pass in the correct rack and then call the api */}
                        <Button onClick={onClose}>Cancel</Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default RatingModal;