import React from "react";
import { Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton } from "@chakra-ui/react";
import { NumberInput, NumberInputField, NumberInputStepper, NumberIncrementStepper, NumberDecrementStepper } from "@chakra-ui/react";
import { Slider, SliderTrack, SliderFilledTrack, SliderThumb } from "@chakra-ui/react";
import { Button, FormControl, FormLabel, Flex } from "@chakra-ui/react";
import { useDisclosure } from "@chakra-ui/react";

const RatingModal = () => {
    const { isOpen, onOpen, onClose } = useDisclosure();

    const [value, setValue] = React.useState(0);
    const handleChange = (value) => setValue(value);

    return (
        <>
            <Button size="sm" onClick={onOpen}>Rate</Button>

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
                        {/* todo <Button colorScheme="blue" mr={6} onClick={onClick}>Submit</Button> */}
                        <Button onClick={onClose}>Cancel</Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default RatingModal;